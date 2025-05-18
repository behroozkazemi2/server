package com.behrouz.server.rest.response.digestList;


import com.behrouz.server.model.product.ProductImageEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.model.product.ProductProviderPriceEntity;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.response.digestList
 * Project Koala Server
 * 09 September 2018 15:52
 **/
public class ProductProviderListDigestResponse {

    private long id;

    private String name;

    private float price;

    private String unit;

    private String shortDescription;

    private long imageId;// آیدی تصویر اصلی

    private boolean availableForSale;


    public ProductProviderListDigestResponse( ProductProviderEntity productProvider,
                                              ProductProviderPriceEntity productPrice,
                                              List<ProductImageEntity> images ) {

        this.id = productProvider.getId();
        this.name = productProvider.getProduct().getName();
        if ( productPrice != null ) {

            this.price = productPrice.getFinalAmount();
        }
        this.unit = productProvider.getProduct().getProductUnit().getName();
        this.shortDescription = productProvider.getProduct().getShortDescription();

        if ( images != null && !images.isEmpty() ){

            this.imageId = images.get( 0 ).getImage().getId();

        }

        this.availableForSale = productProvider.isProductProviderExistence();

    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }



    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }



    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }



    public long getImageId() {
        return imageId;
    }
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }



    public boolean isAvailableForSale() {
        return availableForSale;
    }
    public void setAvailableForSale( boolean availableForSale ) {
        this.availableForSale = availableForSale;
    }
}
