package com.behrouz.server.model.base;


import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

/**
 * Created by: Hapi
 * 26 September 2020
 **/

@MappedSuperclass
public class BaseIdNameEntity extends BaseEntity {

    protected String name;


    @Basic
    public String getName () {
        return name;
    }
    public void setName ( String name ) {
        this.name = name;
    }
}
