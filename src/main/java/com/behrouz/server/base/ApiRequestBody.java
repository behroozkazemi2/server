package com.behrouz.server.base;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Hapi KZM
 */
public class ApiRequestBody<T> {

    private String token;

    private String action;

    private int version;

    private T params;


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


    public T getParams() {
        return params;
    }
    public void setParams(T params) {
        this.params = params;
    }
    public <T> T getParams(Class<T> request) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(mapper.writeValueAsString(params), request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
