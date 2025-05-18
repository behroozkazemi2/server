package com.behrouz.server.api.customer.response;

//import com.behrouz.server.model.special.ProviderSpecialProductSuggestionEntity;
//import com.behrouz.server.model.special.SpecialProductProviderEntity;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.response
 * Project server
 * 08 October 2018 10:15
 **/
public class ProductDetailResponse extends ProductResponse {


    protected String userDescription;

    protected float inCartCount;



    public ProductDetailResponse() {
    }


    public ProductDetailResponse(ProductResponse response, String userDescription, float inCartCount ) {
        super(
                response.getId(),
                response.getProductId(),
                response.getName(),
                response.getRate(),
                response.isExistence(),
                response.getShortDescription(),
                response.getFullDescription(),
                response.getProvider(),
                response.getProviderRegion(),
                response.getUnit(),
                response.getCategory(),
                response.getParentCategory(),
                response.getMinAllow(),
                response.getMaxAllow(),
                response.getUnitStep(),
                response.getPrepareHour(),
                response.getPrimitiveAmount(),
                response.getOffPercent(),
                response.getOffPrice(),
                response.getFinalAmount(),
                response.getImages(),
                response.getTags(),
                response.getBrands(),
                response.getProductCount()
        );
        this.inCartCount = inCartCount;
        this.userDescription = userDescription;
    }

    public ProductDetailResponse( List<Long> images) {

//        super(
////                (int)special.getId(),
////                "سفارش ویژه",
//                0,
//                0,
//                true,
////                special.getDescription(),
////                suggestion.getDescription(),
////                special.getAcceptedProvider() == null ? null : new IdName(special.getAcceptedProvider().getId(), special.getAcceptedProvider().getName()),
////                special.getAcceptedProvider() == null ? null : new IdName(special.getAcceptedProvider().getRegion().getId(), special.getAcceptedProvider().getRegion().getName()),
////                special.getUnit() == null ? null : new IdName(special.getUnit().getId(), special.getUnit().getName()),
////                special.getCategory() == null ? null : new IdName(special.getCategory().getId(), special.getCategory().getName()),
//                0,
//                0,
//                0,
////                suggestion.getHourToCreation(),
////                suggestion.getFinalAmount(),
//                0,
//                0,
////                suggestion.getFinalAmount(),
//                images,
//                null,
//                null
//        );
//        this.inCartCount = (float) suggestion.getCountValue();
//        this.userDescription = special.getDescription();
    }


    @Override
    public ProductDetailResponse toToman() {
        super.toToman();
        return this;
    }



    public String getUserDescription() {
        return userDescription;
    }
    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }




    public float getInCartCount() {
        return inCartCount;
    }
    public void setInCartCount(float inCartCount) {
        this.inCartCount = inCartCount;
    }


}
