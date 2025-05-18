package com.behrouz.server.base;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.base
 * project name:  ximaServer
 * 13 July 2018
 **/


public class ApiActionRequest {


    private String token;

    private String action;

    private int version;


    public ApiActionRequest() {
    }

    public ApiActionRequest(String token, String action, int version) {
        this.token = token;
        this.action = action;
        this.version = version;
    }

    public ApiActionRequest(ApiRequestBody requestBody) {
        this(requestBody.getToken() , requestBody.getAction() , requestBody.getVersion());
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }


}
