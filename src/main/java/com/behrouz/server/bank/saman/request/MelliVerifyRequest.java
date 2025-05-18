package com.behrouz.server.bank.saman.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by thunderbolt on 9/16/17.
 */
public class MelliVerifyRequest {

    @JsonProperty("Token")
    private String token;

    @JsonProperty("SignData")
    private String signData;



    public MelliVerifyRequest(String token) {
        this.token = token;
    }

    public MelliVerifyRequest(String token, String signData) {
        this.token = token;
        this.signData = signData;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }
}
