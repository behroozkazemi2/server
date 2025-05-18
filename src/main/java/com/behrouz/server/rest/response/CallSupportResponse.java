package com.behrouz.server.rest.response;

import com.behrouz.server.model.CallSupportEntity;
import com.behrouz.server.utils.LocalDateTimeUtil;

import java.util.Date;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.response
 * Project Name: behta-server
 * 18 December 2018
 **/
public class CallSupportResponse {

    private long id;

    private String subject;

    private String description;

    private Date insertDate;

    private String trackingCode;

    private String customerFirstName;

    private String customerLastName;

    private String customerMobile;

    public CallSupportResponse ( CallSupportEntity e ) {
        this.id = e.getId();
        this.subject = e.getSubject();
        this.description = e.getDescription();
        this.insertDate = LocalDateTimeUtil.localDateTimeToDate(e.getInsertDate());
        this.trackingCode = e.getTrackingCode();
        this.customerFirstName = e.getCustomer().getFirstName();
        this.customerLastName = e.getCustomer().getLastName();
        this.customerMobile = e.getCustomer().getMobile();
    }


    public long getId () {
        return id;
    }
    public void setId ( long id ) {
        this.id = id;
    }


    public String getSubject () {
        return subject;
    }
    public void setSubject ( String subject ) {
        this.subject = subject;
    }


    public String getDescription () {
        return description;
    }
    public void setDescription ( String description ) {
        this.description = description;
    }


    public Date getInsertDate () {
        return insertDate;
    }
    public void setInsertDate ( Date insertDate ) {
        this.insertDate = insertDate;
    }


    public String getTrackingCode () {
        return trackingCode;
    }
    public void setTrackingCode ( String trackingCode ) {
        this.trackingCode = trackingCode;
    }


    public String getCustomerFirstName () {
        return customerFirstName;
    }
    public void setCustomerFirstName ( String customerFirstName ) {
        this.customerFirstName = customerFirstName;
    }


    public String getCustomerLastName () {
        return customerLastName;
    }
    public void setCustomerLastName ( String customerLastName ) {
        this.customerLastName = customerLastName;
    }


    public String getCustomerMobile () {
        return customerMobile;
    }
    public void setCustomerMobile ( String customerMobile ) {
        this.customerMobile = customerMobile;
    }

}
