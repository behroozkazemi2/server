package com.behrouz.server.rest.request;

import java.io.Serializable;

/**
 * created by Hapi.
 */

public class DoubleDouble implements Serializable {

    protected double id;

    protected double value;

    public DoubleDouble() {
    }


    public DoubleDouble(int id, double value) {
        this.id = id;
        this.value = value;
    }


    public DoubleDouble(double id) {
        this.id = id;
    }

    public double getId() {
        return id;
    }
    public void setId(double id) {
        this.id = id;
    }


    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
}
