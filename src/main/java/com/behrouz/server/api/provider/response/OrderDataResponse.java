package com.behrouz.server.api.provider.response;

import java.util.List;

public class OrderDataResponse {

    private long allOrderCount;
    private double allOrderAmount;


    private long allConfirmOrderCount;
    private double allConfirmOrderAmount;


    private long allWaitingOrderCount;
    private double allWaitingOrderAmount;

    public OrderDataResponse() {
    }

    public OrderDataResponse(long allOrderCount, double allOrderAmount, long allConfirmOrderCount, double allConfirmOrderAmount, long allWaitingOrderCount, double allWaitingOrderAmount) {
        this.allOrderCount = allOrderCount;
        this.allOrderAmount = allOrderAmount;
        this.allConfirmOrderCount = allConfirmOrderCount;
        this.allConfirmOrderAmount = allConfirmOrderAmount;
        this.allWaitingOrderCount = allWaitingOrderCount;
        this.allWaitingOrderAmount = allWaitingOrderAmount;
    }

    public long getAllOrderCount() {
        return allOrderCount;
    }
    public void setAllOrderCount(long allOrderCount) {
        this.allOrderCount = allOrderCount;
    }

    public double getAllOrderAmount() {
        return allOrderAmount;
    }
    public void setAllOrderAmount(double allOrderAmount) {
        this.allOrderAmount = allOrderAmount;
    }

    public long getAllConfirmOrderCount() {
        return allConfirmOrderCount;
    }
    public void setAllConfirmOrderCount(long allConfirmOrderCount) {
        this.allConfirmOrderCount = allConfirmOrderCount;
    }

    public double getAllConfirmOrderAmount() {
        return allConfirmOrderAmount;
    }
    public void setAllConfirmOrderAmount(double allConfirmOrderAmount) {
        this.allConfirmOrderAmount = allConfirmOrderAmount;
    }

    public long getAllWaitingOrderCount() {
        return allWaitingOrderCount;
    }

    public void setAllWaitingOrderCount(long allWaitingOrderCount) {
        this.allWaitingOrderCount = allWaitingOrderCount;
    }

    public double getAllWaitingOrderAmount() {
        return allWaitingOrderAmount;
    }

    public void setAllWaitingOrderAmount(double allWaitingOrderAmount) {
        this.allWaitingOrderAmount = allWaitingOrderAmount;
    }
}
