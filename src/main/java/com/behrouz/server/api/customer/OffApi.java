package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.response.OffCodeResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionNotFoundException;
import com.behrouz.server.base.exception.ApiActionWrongDataException;
import com.behrouz.server.component.PriceComponent;
import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.model.product.ProductProviderPriceEntity;
import com.behrouz.server.repository.CustomerOrderRepository;
import com.behrouz.server.repository.OffCodeRepository;
import com.behrouz.server.repository.ProductPriceRepository;
import com.behrouz.server.repository.bill.BillRepository;
import com.behrouz.server.rest.constant.RequestDetailResponse;
import com.behrouz.server.specification.OffCodeSpecification;
import com.behrouz.server.utils.DateUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.utils.UnitUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer
 * Project Name: behta-server
 * 20 January 2019
 **/
public class OffApi extends BaseApi {


    @Autowired
    private OffCodeRepository offCodeRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private PriceComponent priceComponent;

    @Autowired
    private BillRepository billRepository;


    @ApiAction( value = "app.customer.off.list" )
    public ApiResponseBody list (long customerId ) throws ApiActionException {

        CustomerEntity customer = getCustomerById( customerId );


        List < OffCodeEntity > offCodes =
                offCodeRepository.findAllByCustomer_IdOrForAllIsTrueAndDeletedIsFalse( customerId );


        List<OffCodeEntity> target = new ArrayList <>(  );

        Set<String> uniqCode = new HashSet <>(  );

        for ( OffCodeEntity offCode : offCodes ) {
            if ( !uniqCode.contains( offCode.getCode() ) ){
                uniqCode.add( offCode.getCode() );
                target.add( offCode );
            }
        }

        return new ApiResponseBody <>().ok(
                target
                        .stream()
                        .map( e ->
                                new OffCodeResponse(
                                        e,
                                        e.getUsageCount(),
                                        billRepository.countAllByCustomer_IdAndOffCode_CodeAndDeletedIsFalse( customerId, e.getCode() ),
//                                        billRepository.countAllByCustomer_IdAndOffCode_IdAndDeletedIsFalse( customerId, e.getId() ),
                                        DateUtil.greaterThan( e.getExpireDate(), new Date() )
                                )
                        )
                        .sorted( Comparator.comparingLong( e -> -e.getInsertDate().getTime() ) )
                        .collect( Collectors.toList() )
        );
    }


    @ApiAction( value = "app.customer.off.check" )
    public ApiResponseBody checkOffCode (RequestDetailResponse request, long customerId ) throws ApiActionException {

        CustomerEntity customer = getCustomerById( customerId );


        List <CustomerOrderEntity> selectedProducts = customerOrderRepository.findAllByCustomer_IdAndDeletedIsFalse( customerId );

        if ( selectedProducts == null || selectedProducts.isEmpty() ){
            throw new ApiActionNotFoundException( "محصولات مورد نظر یافت نشد. لطفا با پشتیبانی تماس بگیرید." );
        }


        List < Long > productProviderIds =
                selectedProducts.stream().map( e -> e.getProductProvider().getId() ).collect( Collectors.toList() );

        List < Long > providerIds =
                selectedProducts.stream().map( e -> e.getProductProvider().getProvider().getId() ).collect( Collectors.toList() );

        // TODO jdbcTemplate

        List<OffCodeEntity> offCodes =
                offCodeRepository
                        .findAll(
                                OffCodeSpecification.findFirstByCodeForCustomer(
                                        productProviderIds, providerIds, customerId, request.getName()
                                )
                        );


        if ( StringUtil.isNullOrEmpty( request.getName() ) || offCodes == null || offCodes.isEmpty() ){
            throw new ApiActionNotFoundException( "کد تخفیف مورد نظر یافت نشد." );
        }

        OffCodeEntity offCode = offCodes.get( 0 );


        /*
        Some validation
         */
        if ( DateUtil.greaterThan( new Date(), offCode.getStartDate() ) ){
            throw new ApiActionWrongDataException(
                    "کد تخفیف مورد نظر فعال نشده."
            );
        }

        if ( DateUtil.greaterThan( offCode.getExpireDate(), new Date() ) ){
            throw new ApiActionWrongDataException(
                    "کد تخفیف مورد نظر منقضی شده."
            );
        }


        List <BillEntity> paidBills =
                new ArrayList<>(); // TODO: 9/30/20
//                billRepository.findAllByCustomer_IdAndStatus_IdNotAndOffCode_Code(
//                        customerId,
//                        BillStatusOption.CREATED.getId(),
//                        request.getName()
//                );

        if ( paidBills != null && paidBills.size() >= offCode.getUsageCount() ) {
            throw new ApiActionNotFoundException(
                    "کد تخفیف مورد نظر منقضی شده."
            );
        }


        /*
        Now, OffCode is valid
         */
        float offCodeAmount = priceComponent.offCodeForCustomerOrders( offCode, selectedProducts );


        return new ApiResponseBody<>().ok( offCodeAmount/10 );
    }


    private float getOffCodeMaxAmount ( OffCodeEntity offCode ) {
        return offCode.getMaxAmount() * ( offCode.getPercent() / 100.0f );
    }


    private float calculatePricesBeforeOffCode ( List<CustomerOrderEntity> selectedProducts ) {

        return (float) selectedProducts
                .stream()
                .mapToDouble( e ->
                        priceComponent.calculateTheTotalPrice(
                                UnitUtil.getSelectedCountBasedOnUnit( e ),
                                getProductProviderPrice( e.getProductProvider().getId() )
                        )
                )
                .sum();

    }


    private long getProductProviderPrice(long productProviderId){

        ProductProviderPriceEntity lastPrice =
                productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc( productProviderId );

        return lastPrice != null ? lastPrice.getFinalAmount() : 0;

    }


}