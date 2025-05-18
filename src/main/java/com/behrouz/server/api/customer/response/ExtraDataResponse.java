package com.behrouz.server.api.customer.response;

public class ExtraDataResponse {


    private String name;

    private String value;


    public ExtraDataResponse() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}