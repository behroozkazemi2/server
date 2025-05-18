package com.behrouz.server.api.customer.request;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.request
 * Project server
 * 06 October 2018 13:53
 **/

/**
 *  Add new order
 *  or
 *  Change count of that order
 */
public class CartAddProductRequest {

    private int id;

    private float count;

    private String userDescription;
    private long addressId;



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public float getCount() {
        return count;
    }
    public void setCount(float count) {
        this.count = count;
    }


    public String getUserDescription() {
        return userDescription;
    }
    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }


    public long getAddressId() {
        return addressId;
    }
    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
}
