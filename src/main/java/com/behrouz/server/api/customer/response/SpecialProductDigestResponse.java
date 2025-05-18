package com.behrouz.server.api.customer.response;

import com.behrouz.server.rest.request.IdName;

import java.util.Date;
import java.util.List;

public class SpecialProductDigestResponse {

    protected int id;

    protected List<Integer> images;

    protected IdName region;

    protected IdName category;

    protected IdName provider;

    protected IdName status;

    protected IdName customer;

    protected Date date;

    protected String description;


    public SpecialProductDigestResponse() {
    }



    public SpecialProductDigestResponse(int id, List<Integer> images, IdName region, IdName category, IdName provider, IdName status, IdName customer, Date date, String description) {
        this.id = id;
        this.images = images;
        this.region = region;
        this.category = category;
        this.provider = provider;
        this.status = status;
        this.customer = customer;
        this.date = date;
        this.description = description;
    }



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public List<Integer> getImages() {
        return images;
    }
    public void setImages(List<Integer> images) {
        this.images = images;
    }



    public IdName getRegion() {
        return region;
    }
    public void setRegion(IdName region) {
        this.region = region;
    }



    public IdName getCategory() {
        return category;
    }
    public void setCategory(IdName category) {
        this.category = category;
    }



    public IdName getProvider() {
        return provider;
    }
    public void setProvider(IdName provider) {
        this.provider = provider;
    }



    public IdName getStatus() {
        return status;
    }
    public void setStatus(IdName status) {
        this.status = status;
    }



    public IdName getCustomer() {
        return customer;
    }
    public void setCustomer(IdName customer) {
        this.customer = customer;
    }



    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }



    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
