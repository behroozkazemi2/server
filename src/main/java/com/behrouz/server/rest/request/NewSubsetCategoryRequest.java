package com.behrouz.server.rest.request;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.request
 * Project Koala Server
 * 25 September 2018 15:55
 **/
public class NewSubsetCategoryRequest {

    private long id;

    private String name;

    private long parentId;




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



    public long getParentId() {
        return parentId;
    }
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }


}
