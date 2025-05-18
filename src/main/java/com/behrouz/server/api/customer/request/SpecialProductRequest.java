package com.behrouz.server.api.customer.request;

import java.util.List;

/*
 * app.customer.special.add
 */
public class SpecialProductRequest {

    private long id;

    private String description;

    private int category;

    private int provider;//provider e k karbar entekhab mikone

    private int region;

    private List<Integer> imageIds;




    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }



    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }



    public int getCategory() {
        return category;
    }
    public void setCategory(int category) {
        this.category = category;
    }



    public int getProvider() {
        return provider;
    }
    public void setProvider(int provider) {
        this.provider = provider;
    }



    public int getRegion() {
        return region;
    }
    public void setRegion(int region) {
        this.region = region;
    }




    public List<Integer> getImageIds() {
        return imageIds;
    }
    public void setImageIds(List<Integer> imageIds) {
        this.imageIds = imageIds;
    }
}
