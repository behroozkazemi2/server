package com.behrouz.server.api.customer.request;

import java.util.Date;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.request
 * Project server
 * 16 September 2018 15:35
 **/
public class RegisterRequest {

    private String mobile;
    private String nationalCode;

    private String firstName;

    private String lastName;

    private boolean genderMen;

    private String inviteCode;

    private String imei;

    private Date birthDate;



    public String getMobile() {
        return mobile;
    }
    public void setMobile( String mobile ) {
        this.mobile = mobile;
    }


    public String getFirstName() {
        return firstName;
    }
    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }
    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }


    public boolean isGenderMen() {
        return genderMen;
    }
    public void setGenderMen( boolean genderMen ) {
        this.genderMen = genderMen;
    }


    public String getInviteCode() {
        return inviteCode;
    }
    public void setInviteCode( String inviteCode ) {
        this.inviteCode = inviteCode;
    }


    public String getImei() {
        return imei;
    }
    public void setImei( String imei ) {
        this.imei = imei;
    }


    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationalCode() {
        return nationalCode;
    }
    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }
}
