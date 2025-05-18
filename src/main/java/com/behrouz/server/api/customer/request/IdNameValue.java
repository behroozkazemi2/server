package com.behrouz.server.api.customer.request;

import com.behrouz.server.rest.request.IdName;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer.request
 * Project Name: behta-server
 * 27 May 2020
 **/
public class IdNameValue {

    private long id;

    private String name;

    private double value;


    public IdNameValue(IdName idName, double value ) {
        this.id = idName.getId();
        this.name = idName.getName();
        this.value = value;
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


    public double getValue () {
        return value;
    }
    public void setValue ( double value ) {
        this.value = value;
    }

    public IdNameValue() {
    }
}
