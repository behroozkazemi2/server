package com.behrouz.server.api.customer.response;

public class ApiVerifyResponse {

    private String token;

    //age bayad register beshe == true vagarna register shode ghablan
    private boolean registration;


    public ApiVerifyResponse() {
    }

    public ApiVerifyResponse(String token, boolean registration) {
        this.token = token;
        this.registration = registration;
    }


    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }


    public boolean isRegistration() {
        return registration;
    }
    public void setRegistration(boolean registration) {
        this.registration = registration;
    }
}
