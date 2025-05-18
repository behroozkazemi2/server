package com.behrouz.server.api.customer.response;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.response
 * Project server
 * 30 September 2018 10:48
 **/
public class ProductProviderListResponse {

    private int id;

    private String name;

    private int imageId;

    private float rate; //for provider



    public int getId() {
        return id;
    }
    public void setId( int id ) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName( String name ) {
        this.name = name;
    }


    public int getImageId() {
        return imageId;
    }
    public void setImageId( int imageId ) {
        this.imageId = imageId;
    }


    public float getRate() {
        return rate;
    }
    public void setRate( float rate ) {
        this.rate = rate;
    }
}
