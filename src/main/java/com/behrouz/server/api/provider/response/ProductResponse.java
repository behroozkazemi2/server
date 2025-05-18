package com.behrouz.server.api.provider.response;

import com.behrouz.server.model.product.ProductEntity;
import com.behrouz.server.rest.request.IdName;

import java.util.List;

public class ProductResponse {

    private long id;

    private String shortDescription;

    private String fullDescription;

    private IdName unit;

    private int unitId; // JUST FOR PANEL

    private String name;

    private IdName category;

    private int categoryId; // JUST FOR PANEL

    private double unitStep;

    private List<Long> images;

    private List<IdName> tags;

    private IdName brand;

    private int brandId; // JUST FOR PANEL

    private List<Long> tagsId; // JUST FOR PANEL

    public ProductResponse() {
    }
    public ProductResponse(ProductEntity p) {
        this.id = p.getId();
        this.shortDescription = p.getShortDescription();
        this.fullDescription = p.getFullDescription();
        this.unit = new IdName(p.getProductUnit().getId(), p.getProductUnit().getName());
        this.unitId = (int) p.getProductUnit().getId();
        this.name = p.getName();
        this.category =  new IdName(p.getCategory().getId(),p.getCategory().getName());
        this.categoryId = (int) p.getCategory().getId();
        this.unitStep = p.getUnitStep();
        this.brand = new IdName(p.getBrand().getId(),p.getBrand().getName());
        this.brandId = (int) p.getBrand().getId();
    }



    public ProductResponse(long id, String shortDescription, String fullDescription, IdName unit, int unitId, String name, IdName category, int categoryId, double unitStep,IdName brand, int brandId) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.unit = unit;
        this.unitId = unitId;
        this.name = name;
        this.category = category;
        this.categoryId = categoryId;
        this.unitStep = unitStep;
        this.brand = brand;
        this.brandId = brandId;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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


    public IdName getUnit() {
        return unit;
    }
    public void setUnit(IdName unit) {
        this.unit = unit;
    }


    public int getUnitId() {
        return unitId;
    }
    public void setUnitId(int unitId) {
        this.unitId = unitId;
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


    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    public double getUnitStep() {
        return unitStep;
    }
    public void setUnitStep(double unitStep) {
        this.unitStep = unitStep;
    }


    public List<Long> getImages() {
        return images;
    }
    public void setImages(List<Long> images) {
        this.images = images;
    }


    public List<IdName> getTags() {
        return tags;
    }
    public void setTags(List<IdName> tags) {
        this.tags = tags;
    }


    public IdName getBrand() {
        return brand;
    }
    public void setBrand(IdName brand) {
        this.brand = brand;
    }


    public int getBrandId() {
        return brandId;
    }
    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }


    public List<Long> getTagsId() {
        return tagsId;
    }
    public void setTagsId(List<Long> tagsId) {
        this.tagsId = tagsId;
    }
}
