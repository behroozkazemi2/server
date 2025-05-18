package com.behrouz.server.rest.constant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.constant
 * Project Koala Server
 * 09 September 2018 16:48
 **/
public class AjaxResponse {

    private boolean result;
    private String payload;


    public AjaxResponse() {
    }

    public AjaxResponse(boolean result, String payload) {
        this.result = result;
        this.payload = payload;
    }

    public AjaxResponse(boolean result, Object payload) {
        this.result = result;
        try {
            this.payload = new ObjectMapper().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

}
