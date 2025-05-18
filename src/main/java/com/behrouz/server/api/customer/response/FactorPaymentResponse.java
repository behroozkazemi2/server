package com.behrouz.server.api.customer.response;

/**
 * Created by: Hapi
 * Company: reza
 * Package: com.behrouz.server.api.customer.response
 * Project Name: behta-server
 * 28 Sep 2020
 **/
public class FactorPaymentResponse {

    private long primitiveAmount;

    private long offPriceAmount;

    private long offCodeAmount;

    private long deliveryAmount;

    private long taxAmount;

    private long finalAmount;

    private long availableInBalance;

    private String offCodeMessage;

    private long count;

    private boolean offCodeMessageState;



    public FactorPaymentResponse() {
    }

    public FactorPaymentResponse(long  count,long primitiveAmount, long offPriceAmount, long offCodeAmount, long deliveryAmount, long taxAmount, long finalAmount, long availableInBalance, String offCodeMessage, boolean offCodeMessageState) {
        this.primitiveAmount = primitiveAmount;
        this.offPriceAmount = offPriceAmount;
        this.offCodeAmount = offCodeAmount;
        this.deliveryAmount = deliveryAmount;
        this.taxAmount = taxAmount;
        this.finalAmount = finalAmount;
        this.availableInBalance = availableInBalance;
        this.offCodeMessage = offCodeMessage;
        this.count = count;
        this.offCodeMessageState = offCodeMessageState;
    }



    public FactorPaymentResponse toToman(){
        this.primitiveAmount /= 10;
        this.offPriceAmount /= 10;
        this.offCodeAmount /= 10;
        this.deliveryAmount /= 10;
        this.taxAmount /= 10;
        this.finalAmount /= 10;
        this.availableInBalance /= 10;

        return this;
    }

    public long getPrimitiveAmount() {
        return primitiveAmount;
    }
    public void setPrimitiveAmount(long primitiveAmount) {
        this.primitiveAmount = primitiveAmount;
    }



    public long getOffPriceAmount() {
        return offPriceAmount;
    }
    public void setOffPriceAmount(long offPriceAmount) {
        this.offPriceAmount = offPriceAmount;
    }



    public long getOffCodeAmount() {
        return offCodeAmount;
    }
    public void setOffCodeAmount(long offCodeAmount) {
        this.offCodeAmount = offCodeAmount;
    }


    public long getDeliveryAmount() {
        return deliveryAmount;
    }
    public void setDeliveryAmount(long deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }



    public long getTaxAmount() {
        return taxAmount;
    }
    public void setTaxAmount(long taxAmount) {
        this.taxAmount = taxAmount;
    }



    public long getFinalAmount() {
        return finalAmount;
    }
    public void setFinalAmount(long finalAmount) {
        this.finalAmount = finalAmount;
    }



    public long getAvailableInBalance() {
        return availableInBalance;
    }
    public void setAvailableInBalance(long availableInBalance) {
        this.availableInBalance = availableInBalance;
    }



    public String getOffCodeMessage() {
        return offCodeMessage;
    }
    public void setOffCodeMessage(String offCodeMessage) {
        this.offCodeMessage = offCodeMessage;
    }


    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }

    public boolean isOffCodeMessageState() {
        return offCodeMessageState;
    }
    public void setOffCodeMessageState(boolean offCodeMessageState) {
        this.offCodeMessageState = offCodeMessageState;
    }
}
