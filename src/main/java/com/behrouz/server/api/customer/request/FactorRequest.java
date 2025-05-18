package com.behrouz.server.api.customer.request;

import java.util.Date;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 09 July 2020
 **/
public class FactorRequest {


    private int addressId;
    private int paymentMethod;

    private String offCode;
    private String userDescription;

    private Date selectedDate;

    private int timeId;

    private boolean app ;

    public FactorRequest() {
    }

    public FactorRequest(int addressId, String offCode, Date selectedDate, int timeId) {
        this.addressId = addressId;
        this.offCode = offCode;
        this.selectedDate = selectedDate;
        this.timeId = timeId;
    }

    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }


    public String getOffCode() {
        return offCode;
    }
    public void setOffCode(String offCode) {
        this.offCode = offCode;
    }


    public Date getSelectedDate() {
        return selectedDate;
    }
    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }


    public int getTimeId() {
        return timeId;
    }
    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }


    public boolean isApp() {
        return app;
    }
    public void setApp(boolean app) {
        this.app = app;
    }


    public String getUserDescription() {
        return userDescription;
    }
    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}