package com.behrouz.server.rest.response.digestList;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.utils.LocalDateTimeUtil;

import java.util.Date;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.response.digestList
 * Project server
 * 08 October 2018 14:45
 **/
public class BillDigestResponse {

    private long id;
    private String trackingCode;
    private Date insertDate;
    private String status;


    private String customerFirstName;
    private String customerLastName;
    private String customerMobile;
    private String customerAddress;

    private double sumAllPrice;


    public BillDigestResponse(long id, String trackingCode, Date insertDate, String customerFirstName, String customerLastName, String customerMobile, String customerAddress) {
        this.id = id;
        this.trackingCode = trackingCode;
        this.insertDate = insertDate;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerMobile = customerMobile;
        this.customerAddress = customerAddress;
    }


    public BillDigestResponse ( BillEntity bill,
                                CustomerEntity customer,
                                AddressEntity customerAddress,
                                String status,
                                double sumAllPrice ) {

        if ( bill != null ) {

            this.id = bill.getId();
            this.trackingCode = bill.getTrackingCode();
            this.insertDate = LocalDateTimeUtil.localDateTimeToDate(bill.getInsertDate());

        }

        if ( customer != null ) {

            this.customerFirstName = customer.getFirstName();
            this.customerLastName = customer.getLastName();
            this.customerMobile = customer.getMobile();

        }

        if ( customerAddress != null ) {

            this.customerAddress = customerAddress.getAddress();

        }

        this.status = status;

        this.sumAllPrice = sumAllPrice;

    }


    public long getId() {
        return id;
    }
    public void setId( long id ) {
        this.id = id;
    }


    public String getTrackingCode() {
        return trackingCode;
    }
    public void setTrackingCode( String trackingCode ) {
        this.trackingCode = trackingCode;
    }


    public Date getInsertDate() {
        return insertDate;
    }
    public void setInsertDate( Date insertDate ) {
        this.insertDate = insertDate;
    }


    public String getStatus () {
        return status;
    }
    public void setStatus ( String status ) {
        this.status = status;
    }


    public String getCustomerFirstName() {
        return customerFirstName;
    }
    public void setCustomerFirstName( String customerFirstName ) {
        this.customerFirstName = customerFirstName;
    }


    public String getCustomerLastName() {
        return customerLastName;
    }
    public void setCustomerLastName( String customerLastName ) {
        this.customerLastName = customerLastName;
    }


    public String getCustomerMobile() {
        return customerMobile;
    }
    public void setCustomerMobile( String customerMobile ) {
        this.customerMobile = customerMobile;
    }


    public String getCustomerAddress() {
        return customerAddress;
    }
    public void setCustomerAddress( String customerAddress ) {
        this.customerAddress = customerAddress;
    }


    public double getSumAllPrice () {
        return sumAllPrice;
    }
    public void setSumAllPrice ( double sumAllPrice ) {
        this.sumAllPrice = sumAllPrice;
    }
}