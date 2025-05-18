package com.behrouz.server.rest.request;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.request
 * Project server
 * 16 September 2018 11:36
 **/
public class AddProductRequest {

    private String productName; // esme kala

    private List<Integer> productUnitIds; // che vahedai dare

    private List<Integer> productProviderIds; // kia daranesh

    private List<Byte> images; // axash



    public String getProductName() {
        return productName;
    }
    public void setProductName( String productName ) {
        this.productName = productName;
    }


    public List< Integer > getProductUnitIds() {
        return productUnitIds;
    }
    public void setProductUnitIds( List< Integer > productUnitIds ) {
        this.productUnitIds = productUnitIds;
    }


    public List< Integer > getProductProviderIds() {
        return productProviderIds;
    }
    public void setProductProviderIds( List< Integer > productProviderIds ) {
        this.productProviderIds = productProviderIds;
    }


    public List< Byte > getImages() {
        return images;
    }
    public void setImages( List< Byte > images ) {
        this.images = images;
    }
}
