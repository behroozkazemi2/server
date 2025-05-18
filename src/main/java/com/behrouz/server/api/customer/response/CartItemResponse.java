package com.behrouz.server.api.customer.response;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.response
 * Project server
 * 08 October 2018 13:49
 **/
public class CartItemResponse extends ProductDetailResponse{

    private long orderId;

    private long totalAmount;



    public CartItemResponse() {
    }

    public CartItemResponse(ProductResponse response, String userDescription, float inCartCount, long orderId, long totalAmount) {
        super(response, userDescription, inCartCount);
        this.orderId = orderId;
        this.totalAmount = totalAmount;
    }



    @Override
    public CartItemResponse toToman(){
        super.toToman();
        this.totalAmount /= 10;
        return this;
    }

    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }



    public long getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

}
