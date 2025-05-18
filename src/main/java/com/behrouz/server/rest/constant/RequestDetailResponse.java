package com.behrouz.server.rest.constant;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.constant
 * Project Koala Server
 * 09 September 2018 15:53
 **/
public class RequestDetailResponse {

    private Long id;

    private String name;
    private String description;
    private String icon;
    private long imageId;

    public RequestDetailResponse() {
    }
    public RequestDetailResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public RequestDetailResponse(Long id, String name, long imageId, String description, String icon) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
        this.description = description;
        this.icon = icon;
    }



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getImageId() {
        return imageId;
    }
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }
}
