package com.behrouz.server.rest.response.digestList;


import com.behrouz.server.model.global.UnitEntity;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.response.digestList
 * Project Koala Server
 * 10 September 2018 11:10
 **/
public class UnitListDigestResponse {

    private long id;

    private String name;

    private boolean dividable;

    private float ratio;

    private float tolerance;

    public UnitListDigestResponse() {
    }

    public UnitListDigestResponse(UnitEntity e) {
        if (e != null) {
            this.id = e.getId();
            this.name = e.getName();
            this.dividable = e.isDividable();
            this.ratio = e.getRatio();
            this.tolerance = e.getTolerance();
        }
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public boolean isDividable() {
        return dividable;
    }
    public void setDividable(boolean dividable) {
        this.dividable = dividable;
    }



    public float getRatio() {
        return ratio;
    }
    public void setRatio(float ratio) {
        this.ratio = ratio;
    }



    public float getTolerance() {
        return tolerance;
    }
    public void setTolerance(float tolerance) {
        this.tolerance = tolerance;
    }


}
