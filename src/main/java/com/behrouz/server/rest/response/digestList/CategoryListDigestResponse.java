package com.behrouz.server.rest.response.digestList;

import com.behrouz.server.rest.constant.RequestDetailResponse;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.response.digestList
 * Project Koala Server
 * 10 September 2018 11:10
 **/
public class CategoryListDigestResponse {

    private long id;

    private String name;

    private List<RequestDetailResponse> categories;




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



    public List<RequestDetailResponse> getCategories() {
        return categories;
    }
    public void setCategories(List<RequestDetailResponse> categories) {
        this.categories = categories;
    }

}
