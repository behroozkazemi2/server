package com.behrouz.server.api.customer.response;

import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.rest.request.IdName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer.request
 * Project Name: behta-server
 * 27 May 2020
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomProductResponse {

    private long id;

    private long providerId;

    private IdName status;

    private String description;

    private double price;

    private List<ImageResponse> images;

    public CustomProductResponse(ProductProviderEntity pp) {
        providerId = pp.getProvider().getId();
        description = pp.getProduct().getFullDescription();
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public long getProviderId () {
        return providerId;
    }
    public void setProviderId ( long providerId ) {
        this.providerId = providerId;
    }


    public IdName getStatus () {
        return status;
    }
    public void setStatus ( IdName status ) {
        this.status = status;
    }


    public String getDescription () {
        return description;
    }
    public void setDescription ( String description ) {
        this.description = description;
    }


    public double getPrice () {
        return price;
    }
    public void setPrice ( double price ) {
        this.price = price;
    }


    public List < ImageResponse > getImages () {
        return images;
    }
    public void setImages ( List < ImageResponse > images ) {
        this.images = images;
    }


    public CustomProductResponse() {
    }
}