package com.behrouz.server.api.provider.request;


import com.behrouz.server.rest.request.IdName;

import java.util.List;

public class ProviderProductRequest {

    private int id;

    private float showOrder;

    private boolean productProviderExistence;

    private String shortDescription;

    private String fullDescription;

    private IdName provider; // if user is super admin , should be fill

    private IdName productProviderUnit;

    private String name;

    private IdName category;

    private double minAllow;

    private double maxAllow;

    private int prepareHour;

    private long amount;

    private long discountPercent;

    private List<Integer> images;

    private List<IdName> tags;



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }



    public float getShowOrder() {
        return showOrder;
    }
    public void setShowOrder(float showOrder) {
        this.showOrder = showOrder;
    }



    public boolean isProductProviderExistence() {
        return productProviderExistence;
    }
    public void setProductProviderExistence(boolean productProviderExistence) {
        this.productProviderExistence = productProviderExistence;
    }



    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }



    public String getFullDescription() {
        return fullDescription;
    }
    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }



    public IdName getProvider() {
        return provider;
    }
    public void setProvider(IdName provider) {
        this.provider = provider;
    }



    public IdName getProductProviderUnit() {
        return productProviderUnit;
    }
    public void setProductProviderUnit(IdName productProviderUnit) {
        this.productProviderUnit = productProviderUnit;
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public IdName getCategory() {
        return category;
    }
    public void setCategory(IdName category) {
        this.category = category;
    }



    public double getMinAllow() {
        return minAllow;
    }
    public void setMinAllow(double minAllow) {
        this.minAllow = minAllow;
    }



    public double getMaxAllow() {
        return maxAllow;
    }
    public void setMaxAllow(double maxAllow) {
        this.maxAllow = maxAllow;
    }



    public int getPrepareHour() {
        return prepareHour;
    }
    public void setPrepareHour(int prepareHour) {
        this.prepareHour = prepareHour;
    }






    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }



    public long getDiscountPercent() {
        return discountPercent;
    }
    public void setDiscountPercent(long discountPercent) {
        this.discountPercent = discountPercent;
    }



    public List<Integer> getImages() {
        return images;
    }
    public void setImages(List<Integer> images) {
        this.images = images;
    }



    public List<IdName> getTags() {
        return tags;
    }
    public void setTags(List<IdName> tags) {
        this.tags = tags;
    }

}
