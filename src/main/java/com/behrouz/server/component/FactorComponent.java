package com.behrouz.server.component;

import com.behrouz.server.bot.BotProviderRetrofitFactory;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.model.product.ProductProviderPriceEntity;
import com.behrouz.server.repository.CandidateTimeRepository;
import com.behrouz.server.repository.OffCodeRepository;
import com.behrouz.server.repository.ProductPriceRepository;
import com.behrouz.server.repository.balance.BalanceHistoryRepository;
import com.behrouz.server.repository.balance.BalanceRepository;
import com.behrouz.server.repository.bill.BillProductProviderRepository;
import com.behrouz.server.repository.bill.BillRepository;
import com.behrouz.server.repository.bill.BillStatusRepository;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.component
 * Project Name: behta-server
 * 02 July 2020
 **/

@Component
public class FactorComponent {

    private static final Logger logger = Logger.getLogger( PaymentComponent.class.getSimpleName() );

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private BillStatusRepository billStatusRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillProductProviderRepository billProductProviderRepository;

    @Autowired
    private PriceComponent priceComponent;
//
//    @Autowired
//    private AddressRepository addressRepository;

    @Autowired
    private CandidateTimeRepository candidateTimeRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BalanceHistoryRepository balanceHistoryRepository;

    @Autowired
    private OffCodeRepository offCodeRepository;


//
//    public BillEntity makePrimitiveFactor ( CustomerEntity customer,
//                                                List <CustomerOrderEntity> customerOrders,
//                                                String OffCodeText,
//                                                int addressId,
//                                                Date orderDate,
//                                                int orderTimeId) {
//
//
//        OffCodeEntity offCode = offCodeRepository.findFirstByCodeAndDeletedIsFalse( OffCodeText );
//
//        AddressEntity address = addressRepository.findFirstById( addressId );
//
//        SettingEntity setting = settingRepository.findFirstById(1);
//
//        long realAmount = priceComponent.realPriceForCustomerOrders( customerOrders );
//
//        long discountAmount = priceComponent.discountForCustomerOrders( customerOrders );
//
//        long offAmount = priceComponent.offCodeForCustomerOrders( offCode, customerOrders );
//
//        long distanceAmount = priceComponent.calculateDistanceAmount( address );
//
//        long taxAmount =
//                setting.getTaxPercent() == 0
//                        ? 0
//                        : priceComponent.calculateTaxAmount(setting);
//
//
//        BillEntity bill = new BillEntity(
//                billStatusRepository.findFirstById(BillStatusOption.CREATED.getId()),
//                customer,
//                offCode,
//                realAmount, discountAmount, offAmount, distanceAmount, taxAmount,
//                address
//        );
//
//        CandidateTimeEntity candidateTime = candidateTimeRepository.findAllById(orderTimeId);
//        if(candidateTime != null){
//            bill.setOrderTime(candidateTime);
//        }
//
//        if(orderDate != null){
//            bill.setOrderDate(orderDate);
//        }
//
//        return bill;
//    }


    public void sendBillProductToTelegramBotProvider(BillEntity bill) {

        logger.info( "sendBillProductToTelegramBotProvider" );

        final List<BillProductProviderEntity> billOrder =
                billProductProviderRepository.findAllByBill_IdAndDeletedIsFalse(bill.getId());


        new Thread(() -> {

            for (BillProductProviderEntity bp : billOrder) {
                System.err.println("!!TEL!! before BotProviderRetrofitFactory == null! ");
                Call<Void> response =
                        BotProviderRetrofitFactory.getInstance().getBotProviderEndPoint().newBillReceived(
                                bp.getProductProvider().getProvider().getId(),
                                bp.getId(),
                                bp.getBill().getId()
                        );

                try {
                    Response<Void> respobseHttp = response.execute();

                    if (respobseHttp == null) {
                        System.err.println("Response Http From Bot is null");
                    }else {
                        if(respobseHttp.isSuccessful()){
                            System.out.println("Request Send Successful");
                        }else{
                            System.err.println("Response failed From Server By Code: " + respobseHttp.code());
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }).start();


    }


//
//    public List < BillProductEntity > makeBillProducts ( final BillEntity bill,
//                                                         final List < CustomerOrderEntity > customerOrders,
//                                                         String OffCodeText ) throws ApiActionException {
//
//        List< BillProductEntity > billProductList = new ArrayList<>(  );
//
//        OffCodeEntity offCode = offCodeRepository.findFirstByCodeAndDeletedIsFalse( OffCodeText );
//
//        long offCodeAll = priceComponent.offCodeForCustomerOrders(
//                offCode,
//                customerOrders
//        );
//
//
//        customerOrders
//                .stream()
//                .collect( Collectors.groupingBy( e -> e.getProductProvider().getProvider().getId() ) )
//                .forEach( (providerId, orders) -> {
//
//
//
//                    orders.forEach( order -> {
//
//                        ProductPriceEntity productPrice = getProductPrice( order );
//
//                        long price = priceComponent.calculateTheTotalPrice(
//                                UnitUtil.getSelectedCountBasedOnUnit( order ),
//                                productPrice
//                        );
//
//                        long currentDiscountPrice = priceComponent.oneProductWithDiscount(order);
//
//                        long currentOffCodePrice = 0; //todo badan bayad handle she
//
//
//                        billProductList.add(
//                                new BillProductEntity(
//                                        bill,
//                                        order,
//                                        productPrice,
//                                        price,
//                                        currentDiscountPrice,
//                                        currentOffCodePrice
//                                )
//                        );
//
//                    } );
//
//
//
//                } );
//
//
//        return billProductList;
//    }


    private ProductProviderPriceEntity getProductPrice(CustomerOrderEntity order) {

        ProductProviderPriceEntity productPrice =
                productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(
                        order.getProductProvider().getId()
                );

//        if ( productPrice == null ) {
//            throw new ApiActionException(
//                    HttpCode.REQUEST_REJECT,
//                    String.format(
//                            "کالا %s یافت نشده.",
//                            order.getProductProvider().getName()
//                    )
//            );
//        }


        return productPrice;
    }




    public void setBillAccepted(BillEntity newBill) throws BehtaShopException {
        //set bill status to accepted
        //change user balance

//        BillStatusEntity billStatus = billStatusRepository.findFirstByIdAnd_DeletedIsFalse(BillStatusOption.ACCEPTED.getId());
//        if(billStatus == null){
//            throw new HashtagMarketException("خطا در محاسبه موجودی، با پشتیبانی تماس بگیرید.");
//        }
//
//
//        BalanceEntity balance = balanceRepository.findFirstByCustomer_IdOrderByIdDesc( newBill.getCustomer().getId() );
//        if(balance == null){
//            throw new HashtagMarketException("خطا در محاسبه موجودی، با پشتیبانی تماس بگیرید.");
//        }
//
//
//
//        if ( balance.getAmount() < newBill.getPayableAmount() ){
//            throw new HashtagMarketException("موجودی کافی‌نمی‌باشد.");
//        }

//        long currentBalence = balance.getAmount() - newBill.getPayableAmount();




//        //save history balance
//        BalanceHistoryEntity balanceHistoryEntity = new BalanceHistoryEntity(newBill);
//        balanceHistoryRepository.save( balanceHistoryEntity );
//
//        //update balance
//        balance.setAmount(currentBalence);
//        balanceRepository.save(balance);



    }
}
