package com.behrouz.server.rest.response;

import com.behrouz.server.rest.request.IdName;

/**
 * created by Hapi.
 */

public class CategoryRestResponse {

    protected long id;

    protected String name;
    protected IdName parent;

    public CategoryRestResponse() {
    }


    public CategoryRestResponse(long id, String name , IdName parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }


    public CategoryRestResponse(long id) {
        this.id = id;
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

    public IdName getParent() {
        return parent;
    }
    public void setParent(IdName parent) {
        this.parent = parent;
    }
}
