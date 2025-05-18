package com.behrouz.server.rest.request;

import com.behrouz.server.rest.constant.DataTableRequest;

import java.util.Date;

public class BillSearchRequest extends DataTableRequest {

    private String trackingCode;

    private Date fromDate;
    private Date toDate;

    private String firstName;
    private String lastName;
    private String mobile;

    public BillSearchRequest ( String trackingCode, String firstName, String lastName, String mobile, String from, String until, int page, int length, int status ) {

        super(page, length, status);

        if (!trackingCode.isEmpty())
            this.trackingCode = trackingCode;

        if (!firstName.isEmpty())
            this.firstName = firstName;

        if (!lastName.isEmpty())
            this.lastName = lastName;

        if (!mobile.isEmpty())
            this.mobile = mobile;

        if (!from.isEmpty())
            this.fromDate = new Date(Long.parseLong(from));

        if (!until.isEmpty())
            this.toDate = new Date(Long.parseLong(until));

    }


    public String getTrackingCode () {
        return trackingCode;
    }
    public void setTrackingCode ( String trackingCode ) {
        this.trackingCode = trackingCode;
    }


    public Date getFromDate () {
        return fromDate;
    }
    public void setFromDate ( Date fromDate ) {
        this.fromDate = fromDate;
    }


    public Date getToDate () {
        return toDate;
    }
    public void setToDate ( Date toDate ) {
        this.toDate = toDate;
    }


    public String getFirstName () {
        return firstName;
    }
    public void setFirstName ( String firstName ) {
        this.firstName = firstName;
    }


    public String getLastName () {
        return lastName;
    }
    public void setLastName ( String lastName ) {
        this.lastName = lastName;
    }


    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
