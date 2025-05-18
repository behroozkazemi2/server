package com.behrouz.server.rest.response;

public class ProviderCategoryResponse {

    private int providerId;

    private int categoryId;

    private String categoryName;


    public ProviderCategoryResponse() {
    }


    public ProviderCategoryResponse(int providerId, int categoryId, String categoryName) {
        this.providerId = providerId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public int getProviderId() {
        return providerId;
    }
    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }



    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }




    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
