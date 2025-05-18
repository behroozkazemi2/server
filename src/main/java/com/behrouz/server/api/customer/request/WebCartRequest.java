package com.behrouz.server.api.customer.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Abolfazl
 * Package com.behrouz.server.rest.request.cart
 * Project newxima
 * 30 January 2019 12:15 PM
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebCartRequest {

    private int productProviderId;

    private float count;

    private String userDescription;


    public WebCartRequest() {
    }


    public WebCartRequest(int productProviderId, float count, String userDescription) {
        this.productProviderId = productProviderId;
        this.count = count;
        this.userDescription = userDescription;
    }


    public int getProductProviderId () {
        return productProviderId;
    }
    public void setProductProviderId ( int productProviderId ) {
        this.productProviderId = productProviderId;
    }


    public float getCount () {
        return count;
    }
    public void setCount ( float count ) {
        this.count = count;
    }


    public String getUserDescription() {
        return userDescription;
    }
    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }
}
