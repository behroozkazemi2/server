package com.behrouz.server.api.customer.response;

public class PriceRestResponse {

    private long ppId;
    private long priceId;
    private long primitiveAmount;
    private long discountAmount;
    private long finalAmount;

    public PriceRestResponse() {
    }

    public PriceRestResponse(long ppId, long priceId, long primitiveAmount, long discountAmount, long finalAmount) {
        this.ppId = ppId;
        this.priceId = priceId;
        this.primitiveAmount = primitiveAmount;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
    }

    public long getPpId() {
        return ppId;
    }
    public void setPpId(long ppId) {
        this.ppId = ppId;
    }


    public long getPriceId() {
        return priceId;
    }
    public void setPriceId(long priceId) {
        this.priceId = priceId;
    }


    public long getPrimitiveAmount() {
        return primitiveAmount;
    }
    public void setPrimitiveAmount(long primitiveAmount) {
        this.primitiveAmount = primitiveAmount;
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
}
