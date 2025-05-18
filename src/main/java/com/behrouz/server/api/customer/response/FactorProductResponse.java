package com.behrouz.server.api.customer.response;


/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer.response
 * Project Name: behta-server
 * 04 December 2018
 **/
public class FactorProductResponse {

    private String productName;

    private String providerName;

    private long productProviderId;

    private float count;

    private String unit;

    private long price;
    private long finalPrice;

    private long imageId;

    private long discount;

    private boolean customized;


    public FactorProductResponse() {
    }

    public FactorProductResponse(String productName, String providerName, float count, String unit, long price, long imageId, long discount, boolean customized) {
        this.productName = productName;
        this.providerName = providerName;
        this.count = count;
        this.unit = unit;
        this.price = price;
        this.imageId = imageId;
        this.discount = discount;
        this.customized = customized;
    }
    public FactorProductResponse(String productName, String providerName, float count, String unit, long price, long imageId, long discount, boolean customized, long finalPrice, long productProviderId) {
        this.productName = productName;
        this.providerName = providerName;
        this.count = count;
        this.unit = unit;
        this.price = price;
        this.imageId = imageId;
        this.discount = discount;
        this.customized = customized;
        this.finalPrice = finalPrice;
        this.productProviderId = productProviderId;
    }

    public long getDiscount () {
        return discount;
    }
    public void setDiscount ( long discount ) {
        this.discount = discount;
    }


    public String getProductName () {
        return productName;
    }
    public void setProductName ( String productName ) {
        this.productName = productName;
    }


    public String getProviderName () {
        return providerName;
    }
    public void setProviderName ( String providerName ) {
        this.providerName = providerName;
    }


    public float getCount () {
        return count;
    }
    public void setCount ( float count ) {
        this.count = count;
    }


    public String getUnit () {
        return unit;
    }
    public void setUnit ( String unit ) {
        this.unit = unit;
    }


    public long getPrice () {
        return price;
    }
    public void setPrice ( long price ) {
        this.price = price;
    }


    public long getImageId () {
        return imageId;
    }
    public void setImageId ( long imageId ) {
        this.imageId = imageId;
    }


    public boolean isCustomized() {
        return customized;
    }
    public void setCustomized(boolean customized) {
        this.customized = customized;
    }

    public long getFinalPrice() {
        return finalPrice;
    }
    public void setFinalPrice(long finalPrice) {
        this.finalPrice = finalPrice;
    }


    public long getProductProviderId() {
        return productProviderId;
    }
    public void setProductProviderId(long productProviderId) {
        this.productProviderId = productProviderId;
    }
}
