package com.behrouz.server.component;

import com.behrouz.server.api.customer.request.CustomerOrderListRequest;
import com.behrouz.server.api.customer.response.ProductOrderBotResponse;
import com.behrouz.server.api.provider.response.OrderDataResponse;
import com.behrouz.server.api.provider.response.ProviderOrderDigestResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.HttpCode;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.repository.*;
import com.behrouz.server.repository.balance.BalanceHistoryRepository;
import com.behrouz.server.repository.bill.BillBillStatusRepository;
import com.behrouz.server.repository.bill.BillProductProviderRepository;
import com.behrouz.server.repository.bill.BillRepository;
import com.behrouz.server.repository.bill.BillStatusRepository;
import com.behrouz.server.rest.constant.RequestResponseList;
import com.behrouz.server.rest.request.ProviderSearchRequest;
import com.behrouz.server.rest.response.CustomerOrderDetail;
import com.behrouz.server.rest.response.OrderChartRestResponse;
import com.behrouz.server.rest.response.digestList.BillDashboardRestResponse;
import com.behrouz.server.rest.response.digestList.BillDigestResponse;
import com.behrouz.server.utils.LocalDateTimeUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.utils.date.PersianDate;
import com.behrouz.server.utils.date.PersianDateUtil;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.modelOption.BillStatusOption;
import com.behrouz.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.component
 * Project server
 * 08 October 2018 14:38
 **/
@Component
public class OrderComponent {

//
//    @Autowired
//    private AddressRepository addressRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private BillRepository billRepository;


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BillStatusRepository billStatusRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private PriceComponent priceComponent;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private BalanceHistoryRepository balanceHistoryRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private BillProductProviderRepository billProductProviderRepository;

    @Autowired
    private BillBillStatusRepository billBillStatusRepository;


    public RequestResponseList getList(Page<BillEntity> billList) throws BehtaShopException {

        return new RequestResponseList<>(
                billList.getContent()
                        .stream()
                        .map(e ->
                                new BillDigestResponse(
                                        e,
                                        e.getCustomer(),
                                        e.getAddress(),
                                        billBillStatusRepository.findFirstByBill_IdAndDeletedIsFalseOrderByIdDesc(e.getId()).getStatus().getName(),
                                        sumAllProductBillPrices(e.getId())

                                )
                        )
                        .collect(Collectors.toList()),
                billList.getTotalElements()
        );
    }


    public List<CustomerOrderDetail> getBillProducts(long billId) throws BehtaShopException {

        List<BillProductProviderEntity> billProduct =
                billProductProviderRepository.findAllByBill_IdAndDeletedIsFalse(billId);

        if (billProduct == null) {
            throw new BehtaShopException("سفارش مورد نظر یافت نشد");
        }


        return billProduct
                .stream()
                .map(e ->
                        new CustomerOrderDetail(
                                e,
                                productImageRepository
                                        .findAllByProduct_IdAndProduct_DeletedIsFalseAndDeletedIsFalse(
                                                e.getProductProvider().getProduct().getId()
                                        )
                        )
                )
                .collect(Collectors.toList());
    }


    public double sumAllProductBillPrices(long billId) {

        try {

            return getBillProducts(billId).stream().mapToDouble(CustomerOrderDetail::getPrice).sum();

        } catch (BehtaShopException e) {

            return 0;

        }

    }


    public ApiResponseBody getProductBillDetailList(CustomerOrderListRequest request) {

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLength());

        String queryPart =

                "from bill_product bp " +
                        "left join product_provider pp on bp.product_provider_id = pp.id " +
                        "left join bill b on bp.bill_id = b.id " +
                        "where true = true " +
                        "and pp.provider_id = :providerId " +
                        "and bp.deleted = false " +
                        "and bp.accepted = 0 " +
                        "group by ( pp.id ,pp.name ,bpId,bId) ";


        String query =
                "select " +
                        "    pp.id pId, " +
                        "    pp.name pName, " +
                        "    bp.id bpId, " +
                        "    b.id bId " +
                        " " +
                        queryPart +
                        "order by b.insert_date desc  " +
                        "LIMIT :pageLimited " +
                        "OFFSET :pageOffset ";

//        String queryCount = "SELECT count(*) FROM (SELECT 1 " + queryPart + ") mmg ";

        HashMap<String, Object> params = new HashMap<>();
        params.put("providerId", request.getProviderId());
        params.put("pageLimited", pageRequest.getPageSize());
        params.put("pageOffset", pageRequest.getOffset());


        List<ProductOrderBotResponse> result =
                jdbcTemplate.query(
                        query,
                        params,
                        (res, rowId) -> {


                            ProductOrderBotResponse rp = new ProductOrderBotResponse(
                                    res.getLong("pId"),
                                    res.getString("pName"),
                                    res.getLong("bId"),
                                    res.getLong("bpId")
                            );

                            return rp;

                        }
                );


//
//        List<Long> totals =
//                jdbcTemplate.query(
//                        queryCount,
//                        params,
//                        (res, rowId) -> res.getLong(1)
//                );
//
//        long total = ArraysUtil.isNullOrEmpty(totals) ? 0 : totals.get(0);

        return new ApiResponseBody().ok(result);
    }


//    public ApiResponseBody changeProductBillStatus(IdNameBit request) {
//
//        BillProductEntity billProduct = billProductRepository.findFirstByIdAndDeletedIsFalse(request.getId());
//
//        if(billProduct == null){
//            return new ApiResponseBody().error("سفارش مورد نظر یافت نشد!\n با پشتیبانی تماس بگیرید");
//        }
//
//        billProduct.setAccepted(request.isBit() ? OrderStatusOption.ACCEPTED.getId() : OrderStatusOption.REJECTED.getId());
//
//        billProductRepository.save(billProduct);
//
//        if(billProduct.getAccepted() == OrderStatusOption.ACCEPTED.getId()){
//            checkStatusOfBill(billProduct.getBill());
//        }else{
//            // TODO: 7/14/20 *(Hapi) status is rejected (What will happend for the rest of product in this Bill????)
//        }
//
//        return new ApiResponseBody().ok();
//    }

//    private void checkStatusOfBill(BillEntity bill) {
//
//        List<BillProductEntity> billProducts = billProductRepository.findAllByBill_IdAndDeletedIsFalse(bill.getId());
//        long billAcceptedNumber = 0;
//        for(BillProductEntity b : billProducts){
//           if(b.getAccepted() == OrderStatusOption.ACCEPTED.getId() ){
//               billAcceptedNumber+=1;
//           }
//        }
//
//
//        if(billAcceptedNumber == billProducts.size()){
//            //change Bill Status to GettingReady
//            BillStatusEntity billStatus = billStatusRepository.findFirstById(BillStatusOption.GETTING_READY.getId());
//            if(billStatus == null){
//                //billStatus == null
//                return;
//            }
//
//            bill.setStatus(billStatus);
//            billRepository.save(bill);
//
//        }
//    }


//    public ApiResponseBody getProductBillDetail(BillProductRequest request) {
//
//        BillProductEntity billProductEntity = billProductRepository.findFirstByIdAndDeletedIsFalse(request.getBillProductId());
//
//
//        if( billProductEntity == null || billProductEntity.getBill() == null){
//            return new ApiResponseBody().error("سفارش مورد نظر یافت نشد!\n با پشتیبانی تماس بگیرید");
//        }
//
//        List <ProductProviderImageEntity> images =
//                productProviderImageRepository.findAllByProductProvider_IdAndProductProvider_DeletedIsFalseAndDeletedIsFalse( billProductEntity.getProductProvider().getId() );
//
//        List<Integer> imgList = images.stream().map(e -> e.getImage().getId()).collect(Collectors.toList());
//
//        ProductOrderDetailBotResponse productOrderDetailBotResponse = new ProductOrderDetailBotResponse(billProductEntity, imgList);
//
//
//        return new ApiResponseBody().ok(productOrderDetailBotResponse);
//    }


    public void clearCustomerOrder(long customerId) throws ApiActionException {

        List<CustomerOrderEntity> customerOrders = getAllOrders(customerId);

//        customerOrders
//                .forEach( e ->
//                        saveToCustomerOrderHistory(
//                                e,
//                                CustomerOrderHistoryOption.FACTOR_CREATED.getId()
//                        )
//                );

        customerOrders = customerOrders.stream().peek(p -> p.setDeleted(true)).collect(Collectors.toList());
        customerOrderRepository.saveAll(customerOrders);
    }


    private List<CustomerOrderEntity> getAllOrders(long customerId) throws ApiActionException {

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

    public List<ProviderOrderDigestResponse> getPayedBills(ProviderSearchRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLength());
        Date orderDate = request.getOrderDate() == 0 ? null : new Date(request.getOrderDate());
        Date deliverDate =  request.getDeliverDate() == 0 ? null :  new Date(request.getDeliverDate());

        String queryPart = "SELECT" +
                "  b.id id, " +
                "  b.payment_method payment_method, " +
                "  b.tracking_code tracking_code, " +
                "  b.insert_date insert_date, " +
                "  b.order_date dt, " +
                "  b.order_date order_date, " +
                "  ct.name ct_time, " +
                "  b.payable_amount final_price, " +
                "  concat(acc.first_name , ' ' , acc.last_name ) customer_name, " +
                "  array_agg(DISTINCT p.name) products, " +
                "  acc.mobile mobile, " +
                "  a.address addr, " +
                "  bl.name bill_status_name, " +
                "  apr.name provider_name, " +
                "  apr.image_id provider_imageId, " +
                "  bl.id bill_status_id " +
                "FROM bill b " +
                "INNER JOIN bill_product_provider bpp ON b.id = bpp.bill_id AND bpp.deleted = FALSE " +
                "INNER JOIN product_provider pp ON pp.id = bpp.product_provider_id  " +
                "INNER JOIN product p ON p.id = pp.product_id  " +
                "INNER JOIN account apr ON apr.id = pp.provider_id  " +
                "INNER JOIN account acc ON b.customer_id = acc.id " +
                "INNER JOIN address a ON b.address_id = a.id " +
                "INNER JOIN bill_bill_status_last bll ON bll.bill_id = b.id " +
                "INNER JOIN bill_status bl ON bl.id = bll.bill_status_id " +
                "INNER JOIN candidate_time ct ON ct.id = b.order_time_id " +
                "WHERE b.deleted = FALSE " +
                (StringUtil.isNullOrEmpty(request.getSearch()) ? "" :
                        " AND ( apr.name LIKE :searchStr ) "
                )+
                (StringUtil.isNullOrEmpty(request.getTrackingCode()) ? "" :
                        " AND ( b.tracking_code LIKE :trackingCode ) "
                )+
                (request.getStatus() == 0 ? "" :
                        " AND bl.id = :status  "
                )+
                (request.getOrderDate() == 0 ? "" : "AND  Date(b.insert_date) = Date(:orderDate)  ") +
                (request.getDeliverDate() == 0 ? "" : "AND  Date(b.order_date) = Date(:deliverDate)  ") +

                (StringUtil.isNullOrEmpty(request.getCustomerName()) ? "" :
                        " AND ( acc.first_name LIKE :customerName OR acc.last_name LIKE :customerName ) "
                )+
                // TODO فقط استیت های پرداخت شده بیاد
                "GROUP BY b.id, b.tracking_code, b.insert_date, b.payment_method, b.order_date, b.order_date, ct.name, b.payable_amount, apr.image_id, acc.mobile, concat(acc.first_name , ' ' , acc.last_name ), concat(acc.first_name , ' ' , acc.last_name ), a.address, bl.name, apr.name, bl.id";


        String query = queryPart +
                " order by b.insert_date desc  ";
//                "LIMIT :pageLimited " +
//                "OFFSET :pageOffset ";

//        String queryCount = "SELECT count(*) FROM (SELECT 1 " + queryPart + ") mmg ";

        HashMap<String, Object> params = new HashMap<>();
        params.put("providerId", request.getProviderId());
        params.put("pageLimited", pageRequest.getPageSize());
        params.put("pageOffset", pageRequest.getOffset());
        params.put("searchStr", request.getSearch());
        params.put("customerName", request.getCustomerName());
        params.put("customerMobile", request.getCustomerMobile());
        params.put("status", request.getStatus());
        params.put("trackingCode", request.getTrackingCode());
        params.put("orderDate", orderDate);
        params.put("deliverDate", deliverDate);


        return jdbcTemplate.query(
                query,
                params,
                (res, rowId) -> {
                    String[] prs = (res.getObject("products") != null ? (String[]) res.getArray("products").getArray() : null);

                    List<String> products = new ArrayList<>();
                    for (int i = 0; i < prs.length; i++) {
                        products.add(prs[i]);
                    }
                    ProviderOrderDigestResponse rp = new ProviderOrderDigestResponse(
                            res.getLong("id"),
                            res.getString("tracking_code"),
                            res.getLong("bill_status_id"),
                            res.getLong("bill_status_id") == BillStatusOption.WAIT_FOR_PAY.getId() ? "پرداخت‌ ناموفق" : res.getString("bill_status_name"),
                            PersianDateUtil.getPersianDate(res.getTimestamp("insert_date")) + "",
                            products,
                            res.getDouble("final_price"),
                            res.getString("provider_name"),
                            res.getString("ct_time") + " : " + PersianDateUtil.getPersianDate(res.getTimestamp("order_date")),
                            res.getString("customer_name"),
                            res.getString("mobile"),
                            res.getLong("provider_imageId"),
                            res.getLong("payment_method")
                            );
                    return rp;
                }
        );

    }


    public OrderDataResponse getAllOrderForMainPageTopCart(ProviderSearchRequest request) throws BehtaShopException {

        List<BillDashboardRestResponse> result =
                getBillDashboardRestResponseList(request);

        OrderDataResponse order = new OrderDataResponse();

        long totalCount = 0;
        double totalPrice = 0;

        long verifiedCount = 0;
        double VerifiedPrice = 0;

        long inProgressCount = 0;
        double inProgressPrice = 0;

        for (BillDashboardRestResponse bi : result){
            totalCount += bi.getCount();
            totalPrice += bi.getAmount();
            switch (BillStatusOption.getById(bi.getId())) {
                case SENDING:
                case DELIVERED: {
                    verifiedCount += bi.getCount();
                    VerifiedPrice += bi.getAmount();
                    break;
                }
                case PAYED: {
                    inProgressCount += bi.getCount();
                    inProgressPrice += bi.getAmount();
                    break;
                }
            }


        }

        order.setAllOrderAmount(totalPrice);
        order.setAllOrderCount(totalCount);

        order.setAllConfirmOrderAmount(VerifiedPrice);
        order.setAllConfirmOrderCount(verifiedCount);

        order.setAllWaitingOrderAmount(inProgressPrice);
        order.setAllWaitingOrderCount(inProgressCount);

        return order;
    }

    private List<BillDashboardRestResponse> getBillDashboardRestResponseList(ProviderSearchRequest request) {
        String queryPart = "SELECT" +
                "  bl.id status," +
                "  count(*) cnt," +
                "  sum(b.payable_amount) priceAmount " +
                "FROM bill b " +
                "INNER JOIN bill_bill_status_last bll ON bll.bill_id = b.id " +
                "INNER JOIN bill_status bl ON bl.id = bll.bill_status_id " +
                "WHERE b.deleted = FALSE AND bl.id != 5 " +
                "GROUP BY bl.id ";

        String query = queryPart +
                "   ";
//                "LIMIT :pageLimited " +
//                "OFFSET :pageOffset ";

//        String queryCount = "SELECT count(*) FROM (SELECT 1 " + queryPart + ") mmg ";

        HashMap<String, Object> params = new HashMap<>();
        params.put("providerId", request.getProviderId());
        params.put("status", request.getStatus());


        List<BillDashboardRestResponse> result = jdbcTemplate.query(
                query,
                params,
                (res, rowId) ->
                        new BillDashboardRestResponse(
                                res.getLong("status"),
                                res.getLong("cnt"),
                                res.getDouble("priceAmount")
                        )
        );
        return result;
    }


    public List<OrderChartRestResponse> getOrderChartData(int year, int month, boolean groupByMonth) {

        Date fromDate = year == 0 ? null :
                PersianDateUtil.getDateFromPersianDate(new PersianDate(year, (month == 0 ? 1 : month), 1));

        Date toDate = year == 0 ? null :
                ( month == 0
                        ? LocalDateTimeUtil.localDateTimeToDate(LocalDateTimeUtil.dateToLocalDateDate(fromDate).plusYears(1).plusDays(-1))
                        : LocalDateTimeUtil.localDateTimeToDate(LocalDateTimeUtil.dateToLocalDateDate(fromDate).plusMonths(1).plusDays(-1))
                );

        HashMap<String, Object> params = new HashMap<>();

        params.put("start", fromDate);
        params.put("end", toDate);

        String query =
                " SELECT " +
                        "  b.insert_date, " +
                        "  SUM(bpp.payable_amount) payableAmount , " +
                        "  count(DISTINCT b.id) cnt, " +
                        "  count(DISTINCT bpp.id) ppcnt " +
                        "FROM bill b " +
                        "INNER JOIN bill_product_provider bpp ON bpp.bill_id = b.id AND bpp.deleted = FALSE  " +
                        "INNER JOIN bill_bill_status_last bll ON bll.bill_id = b.id  " +
                        "INNER JOIN bill_status bl ON bl.id = bll.bill_status_id " +
                        (fromDate == null ? " " : " AND DATE(b.insert_date) >= DATE(:start) ") +
                        (toDate == null ? " " : " AND DATE(b.insert_date) <= DATE(:end) ") +
                        "WHERE b.deleted = FALSE " +
                        "AND bl.id IN ( " +
                        BillStatusOption.SENDING.getId() + ", " +
                        BillStatusOption.DELIVERED.getId() + " " +
                        ") " +
                        "GROUP BY b.insert_date " +
                        "ORDER BY b.insert_date asc ";


        List<OrderChartRestResponse> result =
                jdbcTemplate.query(
                        query,
                        params,
                        (res, rowId) ->
                                new OrderChartRestResponse(
                                        res.getDate("insert_date"),
                                        res.getDouble("payableAmount"),
                                        res.getLong("cnt"),
                                        res.getLong("ppcnt")
                                )
                ).stream().collect(
                        Collectors.groupingBy(
                                OrderChartRestResponse::getInsertDate
                        )
                ).entrySet().stream().map(
                        m -> new OrderChartRestResponse(
                                m.getKey(),
                                m.getValue().stream().mapToDouble(OrderChartRestResponse::getPrice).sum(),
                                m.getValue().stream().mapToLong(OrderChartRestResponse::getCount).sum(),
                                m.getValue().stream().mapToLong(OrderChartRestResponse::getProductCount).sum()
                        )
                ).collect(Collectors.toList());
        if (groupByMonth) {

            result = result.stream().collect(
                    Collectors.groupingBy(
                            g -> PersianDateUtil.getPersianDateWithoutDay(g.getInsertDate().getTime())
                    )
            ).values().stream().map(
                    orderChartRestResponses -> {
                        double price = orderChartRestResponses.stream().mapToDouble(OrderChartRestResponse::getPrice).sum();
                        long count = orderChartRestResponses.stream().mapToLong(OrderChartRestResponse::getCount).sum();
                        long productCount = orderChartRestResponses.stream().mapToLong(OrderChartRestResponse::getProductCount).sum();

                        OrderChartRestResponse sameDetail =
                                orderChartRestResponses.get(0);

                        return new OrderChartRestResponse(
                                sameDetail.getInsertDate(),
                                price,
                                count,
                                productCount,
                                PersianDateUtil.getPersianDateWithoutDay(sameDetail.getInsertDate().getTime())
                        );
                    }
            ).collect(Collectors.toList());

        }
        return result
                .stream().sorted(
                        Comparator.comparingLong(cm -> cm.getInsertDate().getTime())
                ).collect(Collectors.toList());
    }
    public List<OrderChartRestResponse> getCategoryOrderChartData(int year, int month) {

        Date fromDate = year == 0 ? null :
                PersianDateUtil.getDateFromPersianDate(new PersianDate(year, (month == 0 ? 1 : month), 1));

        Date toDate = year == 0 ? null :
                ( month == 0
                        ? LocalDateTimeUtil.localDateTimeToDate(LocalDateTimeUtil.dateToLocalDateDate(fromDate).plusYears(1).plusDays(-1))
                        : LocalDateTimeUtil.localDateTimeToDate(LocalDateTimeUtil.dateToLocalDateDate(fromDate).plusMonths(1).plusDays(-1))
                );

        HashMap<String, Object> params = new HashMap<>();

        params.put("start", fromDate);
        params.put("end", toDate);

        String query =
                " SELECT " +
                        "c.name categoryName, " +
                        "c.id categoryId, " +
                        "p.name productName, " +
                        "p.id productId, " +
                        "bpp.payable_amount " +
                        "FROM bill b " +
                        "INNER JOIN bill_product_provider bpp ON bpp.bill_id = b.id AND bpp.deleted = FALSE  " +
                        "INNER JOIN product_provider pp ON pp.id = bpp.product_provider_id " +
                        "INNER JOIN product p ON p.id = pp.product_id " +
                        "INNER JOIN category c ON c.id = p.category_id AND c.deleted = FALSE  " +
                        "INNER JOIN bill_bill_status_last bll ON bll.bill_id = b.id  " +
                        "INNER JOIN bill_status bl ON bl.id = bll.bill_status_id " +
                        (fromDate == null ? " " : " AND DATE(b.insert_date) >= DATE(:start) ") +
                        (toDate == null ? " " : " AND DATE(b.insert_date) <= DATE(:end) ") +
                        "WHERE b.deleted = FALSE " +
                        "AND bl.id IN ( " +
                        BillStatusOption.SENDING.getId() + ", " +
                        BillStatusOption.DELIVERED.getId() + " " +
                        ") " +
                        " ";

        List<OrderChartRestResponse> result =
                jdbcTemplate.query(
                        query,
                        params,
                        (res, rowId) ->
                                new OrderChartRestResponse(
                                        res.getDouble("payable_amount"),
                                        res.getLong("categoryId"),
                                        res.getString("categoryName"),
                                        res.getLong("productId"),
                                        res.getString("productName")
                                )
                );

        Map<Long, Map<Long, List<OrderChartRestResponse>>> categoryMap =
                result.stream().collect(
                        Collectors.groupingBy(
                                OrderChartRestResponse::getCategoryId,
                                Collectors.groupingBy(
                                        OrderChartRestResponse::getProductId
                                )
                        )
                );

        List<OrderChartRestResponse> responseData = new ArrayList<>();
        for (Map.Entry<Long, Map<Long, List<OrderChartRestResponse>>> category: categoryMap.entrySet()){
            OrderChartRestResponse cc = new OrderChartRestResponse();

            List<OrderChartRestResponse> subs = new ArrayList<>();
            int count = 0;
            double price = 0;
            for (Map.Entry<Long , List<OrderChartRestResponse>> product : category.getValue().entrySet()){

                OrderChartRestResponse sameDetail =
                        product.getValue().get(0);

                int subCount = product.getValue().size();
                double subPrice = product.getValue().stream().mapToDouble(OrderChartRestResponse::getPrice).sum();
                price += subPrice;
                count += subCount;

                subs.add(
                        new OrderChartRestResponse(
                                subPrice,
                                sameDetail.getCategoryId(),
                                sameDetail.getCategoryName(),
                                sameDetail.getProductId(),
                                sameDetail.getProductName(),
                                subCount
                        )
                );
            }

            cc.setSubDetail(subs);

            OrderChartRestResponse subProducts =
                    cc.getSubDetail().get(0);

            cc.setCategoryName(subProducts.getCategoryName());
            cc.setCategoryId(subProducts.getCategoryId());
            cc.setCount(count);
            cc.setPrice(price);

            responseData.add(cc);
        }
        return responseData;
    }


//    protected void saveToCustomerOrderHistory( CustomerOrderEntity customerOrder, long statusId ) {
//
//        CustomerOrderHistoryEntity customerOrderHistory = new CustomerOrderHistoryEntity(
//                customerOrder,
//                orderStatusRepository.findFirstById( statusId )
//        );
//
//        customerOrderHistoryRepository.save( customerOrderHistory );
//
//    }


}