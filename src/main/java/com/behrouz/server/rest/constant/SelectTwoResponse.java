package com.behrouz.server.rest.constant;

/**
 * Created by Hapi KZM
 **/
public class SelectTwoResponse {

    private long id;

    private String text;


    public SelectTwoResponse() {
    }


    public SelectTwoResponse(long id, String text) {
        this.id = id;
        this.text = text;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }



    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }


}
