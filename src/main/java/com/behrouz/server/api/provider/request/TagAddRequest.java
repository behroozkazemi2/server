package com.behrouz.server.api.provider.request;

import java.util.Collections;
import java.util.List;

public class TagAddRequest {

    protected long id;

    protected String name;

    protected List<Long> categories;


    public TagAddRequest() {
    }

    public TagAddRequest(long id, String name, List<Long> categories) {
        this.id = id;
        this.name = name;
        this.categories = categories;
    }


    public TagAddRequest(long id, String name, long category) {
        this.id = id;
        this.name = name;
        this.categories = Collections.singletonList(category);
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





    public List<Long> getCategories() {
        return categories;
    }
    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }
}
