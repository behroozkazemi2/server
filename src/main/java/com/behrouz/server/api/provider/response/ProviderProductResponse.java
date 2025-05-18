package com.behrouz.server.api.provider.response;

import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.model.product.ProductProviderPriceEntity;

public class ProviderProductResponse {



    private long productProviderId;

    private long providerId;

    private long productId;

    private long minAllow;

    private long maxAllow;

    private long prepareHour;

    private long primitiveAmount;
    private long productCount;
    private long storageCount;
    private float discountPercent;

    private long discountAmount;

    private long finalAmount;

    private float rate;

    private float showOrder;

    public ProviderProductResponse() {
    }


    public ProviderProductResponse(long productProviderId, long providerId, long productId, long minAllow, long maxAllow, long prepare_hour, long primitiveAmount, float discountPercent, long discountAmount, long finalAmount, float rate, float showOrder, long productCount) {
        this.productProviderId = productProviderId;
        this.providerId = providerId;
        this.productId = productId;
        this.minAllow = minAllow;
        this.maxAllow = maxAllow;
        this.prepareHour = prepare_hour;
        this.primitiveAmount = primitiveAmount;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.rate = rate;
        this.productCount = productCount;
    }

    public ProviderProductResponse(ProductProviderEntity productProvider, ProductProviderPriceEntity price) {
        this.productProviderId = productProvider.getId();
        this.providerId = productProvider.getProvider().getId();
        this.productId = productProvider.getProduct().getId();
        this.minAllow = (long) productProvider.getMinAllow();
        this.maxAllow = (long) productProvider.getMaxAllow();
        this.prepareHour = productProvider.getPrepareHour();
        this.primitiveAmount = price.getPrimitiveAmount();
        this.discountPercent = price.getOffPercent();
        this.discountAmount = price.getOffPrice();
        this.finalAmount = price.getFinalAmount();
        this.rate = productProvider.getRate();
        this.showOrder =  productProvider.getProductProviderOrder();
        this.productCount =  productProvider.getProductCount();
        this.storageCount =  productProvider.getStorageCount();
    }


    public long getProductProviderId() {
        return productProviderId;
    }
    public void setProductProviderId(long productProviderId) {
        this.productProviderId = productProviderId;
    }


    public long getProviderId() {
        return providerId;
    }
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }


    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }


    public long getMinAllow() {
        return minAllow;
    }
    public void setMinAllow(long minAllow) {
        this.minAllow = minAllow;
    }


    public long getMaxAllow() {
        return maxAllow;
    }
    public void setMaxAllow(long maxAllow) {
        this.maxAllow = maxAllow;
    }


    public long getPrepareHour() {
        return prepareHour;
    }
    public void setPrepareHour(long prepare_hour) {
        this.prepareHour = prepare_hour;
    }


    public long getPrimitiveAmount() {
        return primitiveAmount;
    }
    public void setPrimitiveAmount(long primitiveAmount) {
        this.primitiveAmount = primitiveAmount;
    }


    public float getDiscountPercent() {
        return discountPercent;
    }
    public void setDiscountPercent(float discountPercent) {
        this.discountPercent = discountPercent;
    }


    public long getDiscountAmount() {
        return discountAmount;
    }
    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }


    public long getFinalAmount() {
        return finalAmount;
    }
    public void setFinalAmount(long finalAmount) {
        this.finalAmount = finalAmount;
    }


    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }


    public float getShowOrder() {
        return showOrder;
    }
    public void setShowOrder(float showOrder) {
        this.showOrder = showOrder;
    }

    public long getProductCount() {
        return productCount;
    }
    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }

    public long getStorageCount() {
        return storageCount;
    }
    public void setStorageCount(long storageCount) {
        this.storageCount = storageCount;
    }
}

