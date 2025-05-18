package com.behrouz.server.api.customer.request;

/**
 * Created by: HapiJ
 * Package: com.behrouz.server.api.customer.request
 * Project Name: behta-server
 * 15 June 2020
 **/
public class ImageTypeRequest {

    private int id;

    private int type;


    public ImageTypeRequest() {
    }

    public ImageTypeRequest(int id, int type) {
        this.id = id;
        this.type = type;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
}