package com.behrouz.server.api.customer.response;

import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.model.*;
import com.behrouz.server.model.product.*;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.response
 * Project server
 * 08 October 2018 10:15
 **/
public class ProductResponse {

    protected long id; // providerProductId
    protected long productId; // productId
    protected long productCount; // productCount

    protected String name;

    protected float rate;


    protected boolean existence;

    protected String shortDescription;

    protected String fullDescription;

    protected IdName provider;

    protected IdName providerRegion;

    protected IdName unit;

    protected IdName parentCategory;
    protected IdName category;

    protected double unitStep;

    protected double minAllow;

    protected double maxAllow;

    protected long prepareHour;

    protected long primitiveAmount;

    protected float offPercent;

    protected long offPrice;

    protected long finalAmount;

    protected List< Long > images;

    protected List< IdName > tags;

    protected IdName  brands;



    public ProductResponse () {
    }


    public ProductResponse(long id, long productId, String name, float rate, boolean productProviderExistence, String shortDescription, String fullDescription, IdName provider, IdName providerRegion, IdName productProviderUnit, IdName category, IdName parentCategory, double minAllow, double maxAllow, double unitStep, long prepareHour, long primitiveAmount, float offPercent, long offPrice, long finalAmount, List<Long> images, List<IdName> tags, IdName brands, long productCount) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.rate = rate;
        this.existence = productProviderExistence;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.provider = provider;
        this.providerRegion = providerRegion;
        this.unit = productProviderUnit;
        this.category = category;
        this.parentCategory = parentCategory;
        this.minAllow = minAllow;
        this.maxAllow = maxAllow;
        this.unitStep = unitStep;
        this.prepareHour = prepareHour;
        this.primitiveAmount = primitiveAmount;
        this.offPercent = offPercent;
        this.offPrice = offPrice;
        this.finalAmount = finalAmount;
        this.images = images;
        this.tags = tags;
        this.brands = brands;
        this.productCount = productCount;
    }


    public ProductResponse toToman(){
        this.primitiveAmount /= 10;
        this.offPrice /= 10;
        this.finalAmount /= 10;
        return this;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }




    public boolean isExistence() {
        return existence;
    }
    public void setExistence(boolean existence) {
        this.existence = existence;
    }



    public IdName getUnit() {
        return unit;
    }
    public void setUnit(IdName unit) {
        this.unit = unit;
    }



    public double getUnitStep() {
        return unitStep;
    }
    public void setUnitStep(double unitStep) {
        this.unitStep = unitStep;
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


    public IdName getProviderRegion() {
        return providerRegion;
    }
    public void setProviderRegion(IdName providerRegion) {
        this.providerRegion = providerRegion;
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



    public long getPrepareHour() {
        return prepareHour;
    }
    public void setPrepareHour(long prepareHour) {
        this.prepareHour = prepareHour;
    }



    public long getPrimitiveAmount() {
        return primitiveAmount;
    }
    public void setPrimitiveAmount(long primitiveAmount) {
        this.primitiveAmount = primitiveAmount;
    }



    public float getOffPercent() {
        return offPercent;
    }
    public void setOffPercent(float offPercent) {
        this.offPercent = offPercent;
    }



    public long getOffPrice() {
        return offPrice;
    }
    public void setOffPrice(long offPrice) {
        this.offPrice = offPrice;
    }



    public long getFinalAmount() {
        return finalAmount;
    }
    public void setFinalAmount(long finalAmount) {
        this.finalAmount = finalAmount;
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

    public IdName getBrands() {
        return brands;
    }
    public void setBrands(IdName brands) {
        this.brands = brands;
    }

    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }

    public IdName getParentCategory() {
        return parentCategory;
    }
    public void setParentCategory(IdName parentCategory) {
        this.parentCategory = parentCategory;
    }

    public long getProductCount() {
        return productCount;
    }
    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }
}
