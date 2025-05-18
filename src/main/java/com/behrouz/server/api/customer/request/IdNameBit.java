package com.behrouz.server.api.customer.request;

import com.behrouz.server.rest.request.IdName;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 07 July 2020
 **/
public class IdNameBit {

    private long id;

    private String name;

    private boolean bit;


    public IdNameBit(IdName idName, boolean bit ) {
        this.id = idName.getId();
        this.name = idName.getName();
        this.bit = bit;
    }

    public long getId () {
        return id;
    }
    public void setId ( long id ) {
        this.id = id;
    }


    public String getName () {
        return name;
    }
    public void setName ( String name ) {
        this.name = name;
    }


    public boolean isBit() {
        return bit;
    }
    public void setBit(boolean bit) {
        this.bit = bit;
    }


    public IdNameBit() {
    }
}
