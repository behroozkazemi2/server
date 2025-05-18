package com.behrouz.server.api.customer.request;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.request
 * Project server
 * 16 September 2018 12:41
 **/
public class LoginRequest {

    private String mobile;
    private String imei;


    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getImei() {
        return imei;
    }
    public void setImei( String imei ) {
        this.imei = imei;
    }
}
