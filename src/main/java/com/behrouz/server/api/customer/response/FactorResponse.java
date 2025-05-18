package com.behrouz.server.api.customer.response;

import com.behrouz.server.api.customer.request.IdNameLong;
import com.behrouz.server.rest.constant.RequestDetailResponse;
import com.behrouz.server.utils.date.PersianDateUtil;
import com.behrouz.server.modelOption.PaymentMethodOption;

import java.util.Date;
import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer.response
 * Project Name: behta-server
 * 04 December 2018
 **/
public class FactorResponse {

    private long billId;

    private String trackingCode;

    private PaymentMethodOption paymentMethod;


    private Date insertDate;
    private String  insertDateString;


    private RequestDetailResponse status;

    private long payableAmount;

    private Date supposedToDeliverDate;

    private RequestDetailResponse supposedToDeliverTime;

    private Date deliveredDate;

    private List<IdNameLong> productsName;

    private String offCodeString;

    private float discountAmount;
    private float shipAmount;
    private long taxAmount;


    public FactorResponse() {
    }

    public FactorResponse(
            long billId,
            String trackingCode,
            Date insertDate,
            RequestDetailResponse status,
            long payableAmount,
            Date supposedToDeliverDate,
            RequestDetailResponse supposedToDeliverTime,
            Date deliveredDate,
            String offCodeString,
            float discountAmount,
            List<IdNameLong> productsName,
            int payment_method
    ) {
        this.billId = billId;
        this.trackingCode = trackingCode;
        this.insertDate = insertDate;
        this.insertDateString = PersianDateUtil.getPersianDateString(insertDate);
        this.status = status;
        this.payableAmount = payableAmount;
        this.supposedToDeliverDate = supposedToDeliverDate;
        this.supposedToDeliverTime = supposedToDeliverTime;
        this.deliveredDate = deliveredDate;
        this.offCodeString = offCodeString;
        this.discountAmount = discountAmount;
        this.productsName = productsName;
        this.paymentMethod = payment_method == 0 ? PaymentMethodOption.INTERNETI : PaymentMethodOption.getById(payment_method);
    }

    public FactorResponse toToman(){
        this.payableAmount /= 10;
        return this;
    }


    public long getBillId () {
        return billId;
    }
    public void setBillId ( long billId ) {
        this.billId = billId;
    }


    public String getTrackingCode () {
        return trackingCode;
    }
    public void setTrackingCode ( String trackingCode ) {
        this.trackingCode = trackingCode;
    }


    public Date getInsertDate () {
        return insertDate;
    }
    public void setInsertDate ( Date insertDate ) {
        this.insertDate = insertDate;
    }


    public RequestDetailResponse getStatus () {
        return status;
    }
    public void setStatus ( RequestDetailResponse status ) {
        this.status = status;
    }


    public long getPayableAmount () {
        return payableAmount;
    }
    public void setPayableAmount ( long payableAmount ) {
        this.payableAmount = payableAmount;
    }

    public Date getSupposedToDeliverDate() {
        return supposedToDeliverDate;
    }

    public void setSupposedToDeliverDate(Date supposedToDeliverDate) {
        this.supposedToDeliverDate = supposedToDeliverDate;
    }

    public RequestDetailResponse getSupposedToDeliverTime() {
        return supposedToDeliverTime;
    }

    public void setSupposedToDeliverTime(RequestDetailResponse supposedToDeliverTime) {
        this.supposedToDeliverTime = supposedToDeliverTime;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getInsertDateString() {
        return insertDateString;
    }
    public void setInsertDateString(String insertDateString) {
        this.insertDateString = insertDateString;
    }

    public List<IdNameLong> getProductsName() {
        return productsName;
    }
    public void setProductsName(List<IdNameLong> productsName) {
        this.productsName = productsName;
    }



    public String getOffCodeString() {
        return offCodeString;
    }
    public void setOffCodeString(String offCodeString) {
        this.offCodeString = offCodeString;
    }

    public float getDiscountAmount() {
        return discountAmount;
    }
    public void setDiscountAmount(float discountAmount) {
        this.discountAmount = discountAmount;
    }


    public float getShipAmount() {
        return shipAmount;
    }
    public void setShipAmount(float shipAmount) {
        this.shipAmount = shipAmount;
    }

    public PaymentMethodOption getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethodOption paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long getTaxAmount() {
        return taxAmount;
    }
    public void setTaxAmount(long taxAmount) {
        this.taxAmount = taxAmount;
    }


}
