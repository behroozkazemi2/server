package com.behrouz.server.api.customer.request;

import com.behrouz.server.rest.request.ListRequest;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.request
 * Project server
 * 30 September 2018 10:43
 **/
public class ProductProviderCategoryRequest extends ListRequest {

    private List<Long> productIds;

    private List<Long> productCategoryId;

    private long providerId; // for next step, when clicked on some provider

    private String search;

    private List<Long> tag;

    private List<Long> brands;

    private long orderId;

    private float upPrice;

    private float downPrice;

    private long region;

    private boolean existence;


    private long addressId;


    public List<Long> getProductIds() {
        return productIds;
    }
    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }




    public List<Long> getProductCategoryId() {
        return productCategoryId;
    }
    public void setProductCategoryId( List<Long> productCategoryId ) {
        this.productCategoryId = productCategoryId;
    }


    public long getProviderId() {
        return providerId;
    }
    public void setProviderId( long providerId ) {
        this.providerId = providerId;
    }


    public String getSearch () {
        return search;
    }
    public void setSearch ( String search ) {
        this.search = search;
    }

    public List<Long> getTag() {
        return tag;
    }
    public void setTag(List<Long> tag) {
        this.tag = tag;
    }


    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }


    public float getUpPrice() {
        return upPrice;
    }
    public void setUpPrice(float upPrice) {
        this.upPrice = upPrice;
    }


    public float getDownPrice() {
        return downPrice;
    }
    public void setDownPrice(float downPrice) {
        this.downPrice = downPrice;
    }


    public long getRegion() {
        return region;
    }
    public void setRegion(long region) {
        this.region = region;
    }


    public List<Long> getBrands() {
        return brands;
    }
    public void setBrands(List<Long> brands) {
        this.brands = brands;
    }

    public boolean isExistence() {
        return existence;
    }

    public void setExistence(boolean existence) {
        this.existence = existence;
    }

    public long getAddressId() {
        return addressId;
    }
    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "ProductProviderCategoryRequest{" +
                "productIds=" + productIds +
                ", productCategoryId=" + productCategoryId +
                ", providerId=" + providerId +
                ", search='" + search + '\'' +
                ", tag=" + tag +
                ", brands=" + brands +
                ", orderId=" + orderId +
                ", upPrice=" + upPrice +
                ", downPrice=" + downPrice +
                ", region=" + region +
                ", existence=" + existence +
                ", addressId=" + addressId +
                ", page=" + page +
                ", length=" + length +
                '}';
    }
}
