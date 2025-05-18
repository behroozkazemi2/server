package com.behrouz.server.bank.saman.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by thunderbolt on 9/16/17.

 */
public class MelliPaymentResponse implements Serializable{


    @JsonProperty("ResCode")
    private long resCode;

    @JsonProperty("Token")
    private String token;

    @JsonProperty("Description")
    private String description;


    public long getResCode() {
        return resCode;
    }

    public void setResCode(long resCode) {
        this.resCode = resCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}