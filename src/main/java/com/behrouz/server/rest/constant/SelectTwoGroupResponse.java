package com.behrouz.server.rest.constant;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package ir.moblongabaran.mehan.server.rest.constant
 * Project MehanServer
 * 16 July 2018 14:50
 **/
public class SelectTwoGroupResponse {

    private long id;

    private String text;

    private List<SelectTwoResponse> children;


    public SelectTwoGroupResponse() {
    }

    public SelectTwoGroupResponse(String text, List<SelectTwoResponse> children) {
        this.text = text;
        this.children = children;
    }

    public SelectTwoGroupResponse(long id, String text, List<SelectTwoResponse> children) {
        this.id = id;
        this.text = text;
        this.children = children;
    }


    public SelectTwoGroupResponse(long id, String text) {
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



    public List<SelectTwoResponse> getChildren() {
        return children;
    }
    public void setChildren(List<SelectTwoResponse> children) {
        this.children = children;
    }


}
