package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.request.MoneyRequestResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.HttpCode;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionNotFoundException;
import com.behrouz.server.base.exception.ApiActionWrongDataException;
import com.behrouz.server.component.FactorComponent;
import com.behrouz.server.component.OrderComponent;
import com.behrouz.server.component.PaymentComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.balance.BalanceEntity;
import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.bill.BillBillStatusEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.modelOption.BillStatusOption;
import com.behrouz.server.repository.CustomerOrderRepository;
import com.behrouz.server.repository.ProductProviderRepository;
import com.behrouz.server.repository.balance.BalanceRepository;
import com.behrouz.server.repository.bill.BillBillStatusRepository;
import com.behrouz.server.repository.bill.BillProductProviderRepository;
import com.behrouz.server.repository.bill.BillRepository;
import com.behrouz.server.repository.bill.BillStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer
 * Project Name: behta-server
 * 02 February 2019
 **/
public class BalanceApi extends BaseApi {


    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BillStatusRepository billStatusRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private PaymentComponent paymentComponent;

    @Autowired
    private OrderComponent orderComponent;

    @Autowired
    private FactorComponent factorComponent;

    @Autowired
    private BillBillStatusRepository billBillStatusRepository;

    @Autowired
    private ProductProviderRepository productProviderRepository;

    @Autowired
    private BillProductProviderRepository billProductProviderRepository;


    @ApiAction( value = "app.customer.balance.check" )
    public ApiResponseBody check( int customerId ) throws ApiActionException {

        CustomerEntity customer = getCustomerById( customerId );

        return new ApiResponseBody<>().ok(
                new MoneyRequestResponse(
                getCustomerBalance( customerId )
                ).toToman()
        );
    }


    @Transactional(rollbackFor = {ApiActionException.class , Exception.class})
    @ApiAction( value = "app.customer.balance.charge" )
    public ApiResponseBody charge( MoneyRequestResponse chargeRequest, int customerId ) throws ApiActionException {


        try {
            String link = paymentComponent.charge(chargeRequest , customerId);
            return new ApiResponseBody<>().ok( link );

        } catch (BehtaShopException e) {
            return new ApiResponseBody().error(e.getDescription());
        }


    }


    @Transactional(rollbackFor = {ApiActionException.class , Exception.class})
    @ApiAction( value = "app.customer.balance.pay" )
    public ApiResponseBody pay(int customerId ) throws ApiActionException {

        CustomerEntity customer = getCustomerById( customerId );

        long balance =
                getCustomerBalance( customerId ) / 10;


        BillEntity bill =
                billRepository.findFirstByCustomer_IdOrderByIdDesc(customer.getId());

        if (bill == null) {
            throw new ApiActionNotFoundException("فاکتوری برای پرداخت پیدا نشد");
        }


        if ( !hasEnoughMoney(balance, bill) ){
            throw new ApiActionException(
                    HttpCode.NOT_ALLOW,
                    "موجودی شما کافی نیست. لطفا موجودی کیف پول خود را بررسی کنید."
            );
        }


        saveNewBalanceState(
                customer,
                (balance - bill.getPayableAmount()) * 10
        );


        changeBillStatus(
                bill,
                (int) BillStatusOption.PAYED.getId(),
                customer
        );

        reduceProductCount(bill);

        orderComponent.clearCustomerOrder( customerId );

        String redirectLink =
                "https://shop.behrouz.com/pay/success/" + bill.getTrackingCode() + '/' + bill.getId();

        return new ApiResponseBody<>().ok(redirectLink);
    }

    private void reduceProductCount(BillEntity bill) throws ApiActionWrongDataException {
        List<BillProductProviderEntity> billCart =
                billProductProviderRepository.findAllByBill_IdAndDeletedIsFalse(bill.getId());

        List<ProductProviderEntity> productProvideList =
                billCart
                        .stream().map(
                                BillProductProviderEntity::getProductProvider
                        ).collect(Collectors.toList());
        Map<Long, ProductProviderEntity> ppMap =
                productProvideList.stream().collect(
                        Collectors.toMap(
                                BaseEntity::getId,
                                v -> v
                        )
                );

        List<ProductProviderEntity> productProviders = new ArrayList<>();
        for ( BillProductProviderEntity item : billCart ) {
            productProviders.add(reduceProductCount(ppMap.get(item.getId()), (long) item.getOrderCount()));
        }
        productProviderRepository.saveAll(productProviders);
    }


    public ProductProviderEntity reduceProductCount(ProductProviderEntity productProvider, long inCart) throws ApiActionWrongDataException{
        long count = productProvider.getProductCount() - inCart;
        count = count < 0 ? 0 : count;
        productProvider.setProductCount(count);
        return productProvider;
    }


    private List<CustomerOrderEntity> getAllOrders(int customerId) throws ApiActionException {

        List<CustomerOrderEntity> orders =
                customerOrderRepository.findAllByCustomer_IdAndDeletedIsFalse(customerId);

        if ( orders == null || orders.isEmpty() ){
            throw new ApiActionException(
                    HttpCode.REQUEST_REJECT,
                    "هیچ کالایی ثبت نشده"
            );
        }


        return orders;
    }

    private void changeBillStatus (final BillEntity bill, int statusId, AccountEntity account ) {

        BillBillStatusEntity billBillStatus =
                new BillBillStatusEntity(
                        bill,
                        billStatusRepository.findFirstById(statusId),
                        account
                );
        billBillStatusRepository.save(billBillStatus);

    }

    private void saveNewBalanceState ( CustomerEntity customer, long amount ) {

        BalanceEntity newBalance = new BalanceEntity( customer, amount );

        balanceRepository.save( newBalance );

    }

    private boolean hasEnoughMoney ( float balance, BillEntity bill ) {

        float payableAmount = bill.getPayableAmount();

        float inWallet =  balance;

        System.out.println("\n\n\n hasEnoughMoney payableAmount " + payableAmount + " inWallet" + inWallet + "\n\n\n" );

        return inWallet >= payableAmount;

    }


}