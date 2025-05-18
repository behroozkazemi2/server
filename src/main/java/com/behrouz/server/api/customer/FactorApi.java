package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.request.IdNameLong;
import com.behrouz.server.api.customer.response.FactorDetailResponse;
import com.behrouz.server.api.customer.response.FactorProductResponse;
import com.behrouz.server.api.customer.response.FactorResponse;
import com.behrouz.server.api.customer.response.ProviderResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.HttpCode;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionNotFoundException;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.bill.BillBillStatusEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.model.product.ProductImageEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.modelOption.BillStatusOption;
import com.behrouz.server.repository.AddressRepository;
import com.behrouz.server.repository.CustomerOrderRepository;
import com.behrouz.server.repository.ProductImageRepository;
import com.behrouz.server.repository.bill.BillBillStatusRepository;
import com.behrouz.server.repository.bill.BillProductProviderRepository;
import com.behrouz.server.repository.bill.BillRepository;
import com.behrouz.server.repository.bill.BillStatusRepository;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.constant.RequestDetailResponse;
import com.behrouz.server.rest.request.FactorListRequest;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.utils.ArraysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class FactorApi extends BaseApi {


    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private BillStatusRepository billStatusRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillProductProviderRepository billProductProviderRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private AddressRepository addressRepository;

//    @Autowired
//    private SpecialProductProviderImageRepository specialProductProviderImageRepository;
//

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private BillBillStatusRepository billBillStatusRepository;


    @ApiAction(value = "app.customer.factor.list")
    public ApiResponseBody list(FactorListRequest request, int customerId) throws ApiActionException {

        System.out.println("customerId " + customerId);

        CustomerEntity customer = getCustomerById(customerId);

        String query =
                "SELECT " +
                        "b.id bid, " +
                        "b.tracking_code tCode, " +
                        "b.insert_date iDate, " +
                        "bs.id sid, " +
                        "bs.name snm, " +
                        "b.payable_amount pAmount, " +
                        "b.discount_amount + b.off_code_amount dAmount, " +
                        "b.order_date dlvDate, " +
                        "b.payment_method, " +
                        "ct.id tid, " +
                        "oc.code oc_code," +
                        "array_agg(Distinct concat(p.name, '_', p.id)) pName, " +
                        "ct.name tnm " +
                        "FROM bill_bill_status_last bbl " +
                        "INNER JOIN bill b ON bbl.bill_id = b.id AND b.customer_id = :customerId " +
                        "INNER JOIN bill_product_provider bpp ON bpp.bill_id = b.id " +
                        "INNER JOIN product_provider pp ON bpp.product_provider_id = pp.id  " +
                        "INNER JOIN product p ON pp.product_id = p.id  " +
                        "LEFT JOIN off_code oc ON b.off_code_id = oc.id AND oc.deleted = FALSE " +
                        "INNER JOIN bill_status bs on bbl.bill_status_id = bs.id " +
                        "INNER JOIN candidate_time ct on ct.id = b.order_time_id " +
                        "WHERE bbl.deleted = FALSE  " +
                        (request == null || request.getFactorType() == 0 ? "" : " AND  bs.id IN (:status)") +
                        "GROUP BY b.payment_method, b.id, b.tracking_code, b.insert_date, bs.id, bs.name, b.payable_amount, b.discount_amount + b.off_code_amount, b.order_date, ct.id, oc.code, ct.name " +
                        "ORDER BY b.id desc ";

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("status", request == null ? 0 : request.getFactorType());

        List<FactorResponse> result = jdbcTemplate.query(
                query,
                params,
                (res, rowId) -> {
                    List<IdNameLong> products = new ArrayList();
                    String[] pts = (res.getObject("pName") != null ? (String[]) res.getArray("pName").getArray() : null);
                    for (String p : pts) {
                        List<String> items = new ArrayList<>();
                        items = Arrays.asList(p.split("_"));
                        List<ProductImageEntity> images =
                                productImageRepository.findAllByProduct_IdAndProduct_DeletedIsFalseAndDeletedIsFalse(new Long(items.get(1)));
                        if (!items.get(0).equals("")) {
                            products.add(new IdNameLong(
                                    new IdName(
                                            items.get(0),
                                            new Long(items.get(1))
                                    ),
                                    ArraysUtil.isNullOrEmpty(images) ? 0 : images.get(0).getImage().getId()
                            ));
                        }
                    }
                    products = products.stream().distinct().collect(Collectors.toList());
                    return new FactorResponse(
                            res.getLong("bid"),
                            res.getString("tCode"),
                            res.getTimestamp("iDate"),
                            new RequestDetailResponse(
                                    res.getLong("sid"),
                                    (res.getLong("sid") == 1 ? "پرداخت ناموفق" : res.getString("snm"))
                            ),
                            res.getLong("pAmount"),
                            res.getTimestamp("dlvDate"),
                            new RequestDetailResponse(
                                    res.getLong("tid"),
                                    res.getString("tnm")
                            ),
                            null,
                            res.getObject("oc_code") == null ? "-" : res.getString("oc_code"),
                            res.getFloat("dAmount"),
                            products,
                            res.getInt("payment_method")
                    );
                }
        );

        result = result.stream().distinct().collect(Collectors.toList());
        return new ApiResponseBody<>().ok(result, result.size());

    }


    @ApiAction(value = "app.customer.factor.detail")
    public ApiResponseBody detail(@ApiActionParam(nullable = false) IdRequest factorIdRequest, int customerId) throws ApiActionException {

        CustomerEntity customer = getCustomerById(customerId);

        List<BillBillStatusEntity> allStatus = factorIdRequest.getId() == 0 ? null :
                billBillStatusRepository.findAllByBill_IdAndDeletedIsFalse(factorIdRequest.getId());

        if (ArraysUtil.isNullOrEmpty(allStatus)) {
            throw new ApiActionNotFoundException("فاکتور مورد نظر یافت نشد!");
        }

        BillBillStatusEntity lastStatus =
                allStatus.stream().max(Comparator.comparingLong(BillBillStatusEntity::getId)).orElseGet(null);

        if (lastStatus == null) {
            throw new ApiActionNotFoundException();
        }


        List<BillProductProviderEntity> result =
                billProductProviderRepository.findAllByBill_IdAndDeletedIsFalseOrderById(factorIdRequest.getId());

        List<FactorProductResponse> products = new ArrayList<>();

        for (BillProductProviderEntity bco : result) {
            if (bco.getBill().getCustomer().getId() != customer.getId()) {
                throw new ApiActionNotFoundException("عدم دسترسی به فاکتور.");
            }
            BillEntity bl = bco.getBill();
            ProductProviderEntity prd = bco.getProductProvider();
//            SpecialProductProviderEntity spc = ord.getSpecialProduct();

//            boolean isSpecial = spc != null;


//            if(isSpecial){
//                SpecialProductProviderImageEntity spi =
//                        specialProductProviderImageRepository.findFirstBySpecialProduct_Id(spc.getId());
//                img = spi == null ? 0 : spi.getImage().getId();
//            }else{
            ProductImageEntity ppi =
                    productImageRepository.findFirstByProduct_Id(prd.getProduct().getId());
            long img = ppi == null ? 0 : ppi.getImage().getId();
//            }
            FactorProductResponse cur =
                    new FactorProductResponse(
                            prd.getProduct().getName(),
                            prd.getProvider().getName(),
                            bco.getOrderCount(),
                            prd.getProduct().getProductUnit() == null ? "-" : prd.getProduct().getProductUnit().getName(),
                            bco.getRealAmount(),
                            img,
                            bco.getDiscountAmount(),
                            false,
                            bco.getPayableAmount(),
                            bco.getProductProvider().getId()
                    );

            products.add(cur);
        }


        FactorDetailResponse response = new FactorDetailResponse(
                billStatusRepository.findAllByIdInAndDeletedIsFalseOrderById(
                        Arrays.asList(
                                BillStatusOption.INITIALIZE.getId(),
                                BillStatusOption.WAIT_FOR_PAY.getId(),
                                BillStatusOption.PAYED.getId(),
                                BillStatusOption.SENDING.getId(),
                                BillStatusOption.DELIVERED.getId(),
                                BillStatusOption.CANCELED.getId()
                        )
                ),
                lastStatus.getBill(),
                lastStatus.getStatus().getId(),
                products
        );


        return new ApiResponseBody<>().ok(response);
    }

    @ApiAction(value = "app.customer.factor.provider.list")
    public ApiResponseBody providerList(IdRequest factorIdRequest, int customerId) throws ApiActionException {

        CustomerEntity customer = getCustomerById(customerId);
        BillEntity bill = billRepository.findFirstById(factorIdRequest.getId());

        if (bill == null) {
            throw new ApiActionException(
                    HttpCode.REQUEST_REJECT,
                    "فاکتور مورد نظر یافت نشد!"
            );
        }


        List<BillProductProviderEntity> billProducts =
                billProductProviderRepository.findAllByBill_IdAndDeletedIsFalse(factorIdRequest.getId());

        if (billProducts == null) {
            throw new ApiActionException(
                    HttpCode.REQUEST_REJECT,
                    "فاکتور مورد نظر یافت نشد!"
            );
        }

        List<ProviderEntity> providers =
                billProducts.stream().collect(Collectors.groupingBy(g -> g.getProductProvider().getProvider())).entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        return new ApiResponseBody<>().ok(
                providers
                        .stream()
                        .map(ProviderResponse::new
                        )
                        .collect(Collectors.toList()
                        )
        );

    }


//
//    @ApiAction( value = "app.customer.factor.accepted" , tokenRequired = false)
//    public ApiResponseBody accepted ( IdName request, int customerId ) throws ApiActionException {
//
//        // TODO: 7/4/20 update billCharge
//
//        BillEntity bill = billRepository.findFirstById( request.getId() );
//
//        bill.setStatus( billStatusRepository.findFirstById( BillStatusOption.ACCEPTED.getId() ) );
//
//        billRepository.save( bill );
//
//        customerOrderRepository.deleteAll(
//                customerOrderRepository.findAllByCustomer_IdAndDeletedIsFalse( customerId )
//        );
//
//        factorComponent.sendBillProductToTelegramBotProvider( bill );
//
//        return new ApiResponseBody <>().ok();
//    }


    private void changeCustomerPrimaryAddress(int customerId, int addressId) {

        AddressEntity address = addressRepository.findFirstById(addressId);

        if (address == null) {
            return;
//            throw new ApiActionNotFoundException(
//                    "آدرس انتخابی مورد نظر یافت نشد. لطفا با پشتیبانی تماس بگیرید."
//            );
        }


        List<AddressEntity> ownAddresses =
                addressRepository.findAllByAccount_IdAndDeletedIsFalse(customerId);


        ownAddresses.forEach(e -> {

            if (e.getId() == addressId) {

                e.setAddressOrder(1);

            } else {

                e.setAddressOrder(0);

            }

        });


        addressRepository.saveAll(ownAddresses);
    }


    private void productExist(CustomerOrderEntity order) throws ApiActionException {

        if (!order.getProductProvider().isProductProviderExistence()) {

            throw new ApiActionException(
                    HttpCode.REQUEST_REJECT,
                    String.format(
                            "کالا %s تمام شده",
                            order.getProductProvider().getProduct().getName()
                    )
            );

        }

    }


    private List<CustomerOrderEntity> getAllOrders(int customerId) throws ApiActionException {

        List<CustomerOrderEntity> orders =
                customerOrderRepository.findAllByCustomer_IdAndDeletedIsFalse(customerId);

        if (orders == null || orders.isEmpty()) {
            throw new ApiActionException(
                    HttpCode.REQUEST_REJECT,
                    "هیچ کالایی ثبت نشده"
            );
        }


        return orders;
    }


}
