package com.behrouz.server.rest.request;

import java.util.List;

/**
 * Created by Hapi KZM
 * 10 September 2018 12:20
 **/
public class ProductRequest {

    private long id; // new : 0, edit : productId

    private long providerId; // age super admin bood por she vagarna nemikhadesh

    private String name;

    private String shortDescription;

    private String fullDescription;

    private long categoryId;

    private long unitId;

    private List<Integer> tagIds;

    private List<Integer> images;

    private float showOrder; // tartib namayesh dar panel

    private double minAllow;

    private double maxAllow;

    private long amountUnit;

    private float offPercent;

    private long prepareMinHour;

    private long prepareMaxHour;



    public ProductRequest() {
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getProviderId() {
        return providerId;
    }
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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

    public long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public long getUnitId() {
        return unitId;
    }
    public void setUnitId(long unitId) {
        this.unitId = unitId;
    }

    public List<Integer> getTagIds() {
        return tagIds;
    }
    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }

    public List<Integer> getImages() {
        return images;
    }
    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public float getShowOrder() {
        return showOrder;
    }
    public void setShowOrder(float showOrder) {
        this.showOrder = showOrder;
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

    public long getAmountUnit() {
        return amountUnit;
    }

    public void setAmountUnit(long amountUnit) {
        this.amountUnit = amountUnit;
    }

    public float getOffPercent() {
        return offPercent;
    }

    public void setOffPercent(float offPercent) {
        this.offPercent = offPercent;
    }

    public long getPrepareMinHour() {
        return prepareMinHour;
    }

    public void setPrepareMinHour(long prepareMinHour) {
        this.prepareMinHour = prepareMinHour;
    }

    public long getPrepareMaxHour() {
        return prepareMaxHour;
    }

    public void setPrepareMaxHour(long prepareMaxHour) {
        this.prepareMaxHour = prepareMaxHour;
    }
}
