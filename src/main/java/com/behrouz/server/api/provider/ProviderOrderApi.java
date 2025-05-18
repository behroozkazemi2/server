package com.behrouz.server.api.provider;

import com.behrouz.server.api.customer.response.FactorDetailResponse;
import com.behrouz.server.api.customer.response.FactorProductResponse;
import com.behrouz.server.api.provider.response.ListResponse;
import com.behrouz.server.api.provider.response.OrderDataResponse;
import com.behrouz.server.api.provider.response.ProductDigestResponse;
import com.behrouz.server.api.provider.response.ProviderOrderDigestResponse;
import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionNotFoundException;
import com.behrouz.server.component.OrderComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.bill.BillBillStatusEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.model.bill.BillStatusEntity;
import com.behrouz.server.model.product.ProductImageEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.modelOption.BillStatusOption;
import com.behrouz.server.repository.ProductImageRepository;
import com.behrouz.server.repository.bill.BillBillStatusRepository;
import com.behrouz.server.repository.bill.BillProductProviderRepository;
import com.behrouz.server.repository.bill.BillRepository;
import com.behrouz.server.repository.bill.BillStatusRepository;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.ChangeStatusRequest;
import com.behrouz.server.rest.request.ProviderSearchRequest;
import com.behrouz.server.rest.request.YearMonthFilterRequest;
import com.behrouz.server.rest.response.OrderChartRestResponse;
import com.behrouz.server.utils.ArraysUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ProviderOrderApi extends ProviderBaseApi {

    @Autowired
    private OrderComponent orderComponent;

    @Autowired
    private BillBillStatusRepository billBillStatusRepository;

    @Autowired
    private BillProductProviderRepository billProductProviderRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private BillStatusRepository billStatusRepository;

    @Autowired
    private BillRepository billRepository;

    @ApiAction(value = "app.provider.order.list")
    public ApiResponseBody<List<ProviderOrderDigestResponse>> list(
            @ApiActionParam(nullable = false) ProviderSearchRequest request,
            ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest);


        List<ProviderOrderDigestResponse> result =
                new ArrayList<>();

        result =
                orderComponent.getPayedBills(request);
        return new ApiResponseBody<>().ok(result, result.size());
    }



    @ApiAction(value = "app.provider.order.detail")
    public ApiResponseBody detail(@ApiActionParam(nullable = false) IdRequest factorIdRequest, ApiActionRequest apiActionRequest) throws ApiActionException {


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

    @ApiAction(value = "app.provider.order.status")
    public ApiResponseBody<Void> status(@ApiActionParam(nullable = false) ChangeStatusRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest);


        BillEntity bill =
                billRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        if (bill == null) {
            throw new ApiActionNotFoundException("فاکتور یافت نشود.");
        }
        long nextState = 0;


        if (BillStatusOption.PAYED.getId() == request.getStatus()){
            nextState = BillStatusOption.SENDING.getId();

        }else if (BillStatusOption.SENDING.getId() == request.getStatus()){
            nextState = BillStatusOption.DELIVERED.getId();

        }
        BillStatusEntity billStatus =
                billStatusRepository.findFirstByIdAndDeletedIsFalse(nextState);

        if (billStatus == null) {
            throw new ApiActionNotFoundException("وضعیت فاکتور یافت نشود.");
        }

        billBillStatusRepository.save(new BillBillStatusEntity(
                        bill,
                        billStatus,
                        bill.getCustomer()
                )
        );
        return new ApiResponseBody<>().ok();
    }


    //
    @ApiAction(value = "app.provider.order.data")
    public ApiResponseBody<OrderDataResponse> orderDataForMainPageTop(
            @ApiActionParam(nullable = false) ProviderSearchRequest request,
            ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest);


        OrderDataResponse result = new OrderDataResponse();

        try {
            result =
                    orderComponent.getAllOrderForMainPageTopCart(request);
        } catch (BehtaShopException e) {
            throw new RuntimeException(e);
        }

        return new ApiResponseBody<>().ok(result);
    }

    @ApiAction(value = "app.provider.order.chart")
    public ApiResponseBody<ListResponse<ProductDigestResponse>> getOrderChart(YearMonthFilterRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        List<OrderChartRestResponse> response =
                orderComponent.getOrderChartData(
                        (int) request.getYear(),
                        (int) request.getMonth(),
                        request.isGroupByMonth()
                );

        return new ApiResponseBody<>().ok(response);

    }

    @ApiAction(value = "app.provider.order.category.chart")
    public ApiResponseBody<ListResponse<ProductDigestResponse>> getCategoryOrderChartData(YearMonthFilterRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        List<OrderChartRestResponse> response =
                orderComponent.getCategoryOrderChartData(
                        (int) request.getYear(),
                        (int) request.getMonth()
                );

        return new ApiResponseBody<>().ok(response);

    }


}
