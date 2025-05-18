package com.behrouz.server.api.customer.request;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 15 September 2020
 **/
public class CommentRequest {

    private int productId;
    private int providerId;

    private String text;
    private String commenter;
    private String email;
    private boolean logedIn;

    private float rate;

    public CommentRequest() {
    }

    public CommentRequest(int productId, int providerId, String text, float rate) {
        this.productId = productId;
        this.providerId = providerId;
        this.text = text;
        this.rate = rate;
    }

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }


    public int getProviderId() {
        return providerId;
    }
    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }


    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }


    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getCommenter() {
        return commenter;
    }
    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLogedIn() {
        return logedIn;
    }
    public void setLogedIn(boolean logedIn) {
        this.logedIn = logedIn;
    }
}
