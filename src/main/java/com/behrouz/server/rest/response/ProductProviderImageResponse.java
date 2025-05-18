package com.behrouz.server.rest.response;

public class ProductProviderImageResponse {

    private long product;

    private long image;

    private long order;


    public ProductProviderImageResponse() {
    }

    public ProductProviderImageResponse(long product, long image, long order) {
        this.product = product;
        this.image = image;
        this.order = order;
    }

    public long getProduct() {
        return product;
    }
    public void setProduct(long product) {
        this.product = product;
    }




    public long getImage() {
        return image;
    }
    public void setImage(long image) {
        this.image = image;
    }



    public long getOrder() {
        return order;
    }
    public void setOrder(long order) {
        this.order = order;
    }
}
