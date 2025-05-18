package com.behrouz.server.api.customer.request;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 13 July 2020
 **/
public class BankStatusCallBack {

    public static final int STATUS_CODE_OK = 3;
    public static final int STATUS_CODE_FAILED = 2;

    private String token;

    private int status;

    public BankStatusCallBack() {
    }

    public BankStatusCallBack(String token, int status) {
        this.token = token;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
