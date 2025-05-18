package com.behrouz.server.api.customer;//package com.behrouz.server.api.customer;
//
//import BaseApi;
//import CustomProductRequest;
//import CustomProductResponse;
//import ApiResponseBody;
//import ApiAction;
//import ApiActionException;
//import com.behrouz.server.component.CustomProductComponent;
//import HashtagMarketException;
//import CustomerEntity;
//import ProductProviderEntity;
//import IdName;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Created by: Reza
// * Company: reza
// * Package: com.behrouz.server.api.customer
// * Project Name: behta-server
// * 27 May 2020
// **/
//public class CustomProductApi extends BaseApi {
//
//    @Autowired
//    private CustomProductComponent customProductComponent;
//
//
//    @ApiAction( value = "app.customer.custom.new" )
//    @Transactional(rollbackFor = {Exception.class})
//    public ApiResponseBody add ( CustomProductRequest request, int customerId ) throws ApiActionException, HashtagMarketException {
//
//        CustomerEntity customer = getCustomerById( customerId );
//
//        ProductProviderEntity customProduct = customProductComponent.save( request, customer );
//
//        return new ApiResponseBody().ok( new IdName( customProduct.getId() ) );
//    }
//
//    @ApiAction( value = "app.customer.custom.edit" )
//    @Transactional(rollbackFor = {Exception.class})
//    public ApiResponseBody edit ( CustomProductRequest request, int customerId ) throws ApiActionException, HashtagMarketException {
//
//        CustomerEntity customer = getCustomerById( customerId );
//
//        ProductProviderEntity customProduct = customProductComponent.edit( request, customer );
//
//        return new ApiResponseBody().ok( new IdName( customProduct.getId() ) );
//    }
//
//    @ApiAction( value = "app.customer.custom.get" )
//    @Transactional(rollbackFor = {Exception.class})
//    public ApiResponseBody get ( IdName request, int customerId ) throws ApiActionException, HashtagMarketException {
//
//        CustomerEntity customer = getCustomerById( customerId );
//
//        CustomProductResponse product = customProductComponent.get(request.getId(), customer);
//
//        return new ApiResponseBody().ok( product );
//    }
//
//
//    @ApiAction( value = "app.customer.customized.status.change" )
//    public ApiResponseBody statusChange ( int customerId ) throws ApiActionException {
//
//
//
//        return new ApiResponseBody().ok();
//    }
//
//
//}