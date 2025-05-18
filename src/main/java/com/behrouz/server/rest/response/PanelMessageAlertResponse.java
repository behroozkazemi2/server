package com.behrouz.server.rest.response;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.response
 * Project Koala Server
 * 08 October 2018 10:31
 **/
public class PanelMessageAlertResponse {

    private String type;

    private String message;

    public PanelMessageAlertResponse() {
    }

    public PanelMessageAlertResponse(String type, String description) {
        this.type = type;
        this.message = description;
    }


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }



    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
