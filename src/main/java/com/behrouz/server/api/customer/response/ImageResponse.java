package com.behrouz.server.api.customer.response;

import com.behrouz.server.model.product.ProductImageEntity;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.response
 * Project server
 * 30 September 2018 13:13
 **/
public class ImageResponse {

    private long id;

    private byte[] image;

    private boolean deleted;

    public ImageResponse( byte[] image ) {
        this.image = image;
    }

    public ImageResponse(ProductImageEntity e) {
        id = e.getImage().getId();
//        image = e.getImage().getImage();
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }
    public void setImage( byte[] image ) {
        this.image = image;
    }
}
