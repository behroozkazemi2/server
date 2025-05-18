package com.behrouz.server.rest.response;

import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.model.product.ProductImageEntity;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.response
 * Project server
 * 10 October 2018 13:46
 **/

public class CustomerOrderDetail {

    private long billProductId; // ino sikh nazan

    private String unitName;
    private String productName;

    private String providerName;
    private String providerPhone;
    private long providerLogoId;

    private String productProviderShortDescription;
    private String productProviderFullDescription;

    private long productProviderImageId;

    private float count;
    private float price;



    public CustomerOrderDetail( BillProductProviderEntity billProduct,
                                List<ProductImageEntity> productProviderImageList ){

        if ( billProduct != null ) {

            this.billProductId = billProduct.getId();
            this.count = billProduct.getOrderCount();
            this.price = 0;

            this.unitName = billProduct.getProductProvider().getProduct().getProductUnit().getName();

            this.productName = billProduct.getProductProvider().getProduct().getName();
            this.providerName = billProduct.getProductProvider().getProvider().getName();
//                this.providerPhone = billProduct.getProductProvider().getProviderId().getPhone();

            if ( billProduct.getProductProvider().getProvider().getLogo() != null ) {

                this.providerLogoId = billProduct.getProductProvider().getProvider().getLogo().getId();

            }

            this.productProviderShortDescription = billProduct.getProductProvider().getProduct().getShortDescription();
            this.productProviderFullDescription = billProduct.getProductProvider().getProduct().getFullDescription();

        }

        if ( productProviderImageList != null && !productProviderImageList.isEmpty() ){

            this.productProviderImageId = productProviderImageList.get( 0 ).getId();

        }


    }


    public long getBillProductId () {
        return billProductId;
    }
    public void setBillProductId ( long billProductId ) {
        this.billProductId = billProductId;
    }


    public String getUnitName() {
        return unitName;
    }
    public void setUnitName( String unitName ) {
        this.unitName = unitName;
    }


    public String getProductName() {
        return productName;
    }
    public void setProductName( String productName ) {
        this.productName = productName;
    }


    public String getProviderName() {
        return providerName;
    }
    public void setProviderName( String providerName ) {
        this.providerName = providerName;
    }


    public String getProviderPhone() {
        return providerPhone;
    }
    public void setProviderPhone( String providerPhone ) {
        this.providerPhone = providerPhone;
    }


    public long getProviderLogoId() {
        return providerLogoId;
    }
    public void setProviderLogoId( long providerLogoId ) {
        this.providerLogoId = providerLogoId;
    }


    public String getProductProviderShortDescription() {
        return productProviderShortDescription;
    }
    public void setProductProviderShortDescription( String productProviderShortDescription ) {
        this.productProviderShortDescription = productProviderShortDescription;
    }


    public String getProductProviderFullDescription() {
        return productProviderFullDescription;
    }
    public void setProductProviderFullDescription( String productProviderFullDescription ) {
        this.productProviderFullDescription = productProviderFullDescription;
    }


    public long getProductProviderImageId() {
        return productProviderImageId;
    }
    public void setProductProviderImageId( long productProviderImageId ) {
        this.productProviderImageId = productProviderImageId;
    }


    public float getCount () {
        return count;
    }
    public void setCount ( float count ) {
        this.count = count;
    }


    public float getPrice () {
        return price;
    }
    public void setPrice ( float price ) {
        this.price = price;
    }
}
