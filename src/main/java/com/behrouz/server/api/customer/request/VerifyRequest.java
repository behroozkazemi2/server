package com.behrouz.server.api.customer.request;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.request
 * Project server
 * 18 September 2018 10:55
 **/
public class VerifyRequest {

    private String mobile;

    private String smsCode;

    private String imei;

    private String notificationId;

    private boolean android;



    public String getMobile() {
        return mobile;
    }
    public void setMobile( String mobile ) {
        this.mobile = mobile;
    }


    public String getSmsCode() {
        return smsCode;
    }
    public void setSmsCode( String smsCode ) {
        this.smsCode = smsCode;
    }


    public String getImei() {
        return imei;
    }
    public void setImei( String imei ) {
        this.imei = imei;
    }


    public String getNotificationId() {
        return notificationId;
    }
    public void setNotificationId( String notificationId ) {
        this.notificationId = notificationId;
    }

    
    public boolean isAndroid() {
        return android;
    }
    public void setAndroid( boolean android ) {
        this.android = android;
    }
}
