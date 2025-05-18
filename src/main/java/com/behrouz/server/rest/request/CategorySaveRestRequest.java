package com.behrouz.server.rest.request;

import com.behrouz.server.model.global.CategoryEntity;

public class CategorySaveRestRequest {
    private long id;
    private long parent;
    private long imageId;
    private double showOrder;
    private String name;
    private String description;

    public CategorySaveRestRequest() {
    }

    public CategorySaveRestRequest(long id, long imageId,  long parent, String name, String description) {
        this.id = id;
        this.parent = parent;
        this.imageId = imageId;
        this.name = name;
        this.description = description;
    }

    public CategorySaveRestRequest(CategoryEntity c) {
        this.id = c.getId();
        this.parent = c.getParent() == null ? 0 : c.getParent().getId();
        this.imageId = c.getImage() == null ? 0 : c.getImage().getId();
        this.name = c.getName();
        this.description = c.getDescription();
        this.showOrder = c.getShowOrder();
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public long getParent() {
        return parent;
    }
    public void setParent(long parent) {
        this.parent = parent;
    }

    public long getImageId() {
        return imageId;
    }
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public double getShowOrder() {
        return showOrder;
    }
    public void setShowOrder(double showOrder) {
        this.showOrder = showOrder;
    }
}
