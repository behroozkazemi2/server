package com.behrouz.server.api.provider.response;

public class ProductDigestResponse {

    private long id;

    private long image;

    private String name;

    private String shortDescription;

    private String category;

    private String  brand;

    private String unit;
    private long cnt;


    public ProductDigestResponse() {
    }



    public ProductDigestResponse(long id, long image, String name, String shortDescription, String category, String brand, String unit) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.shortDescription = shortDescription;
        this.category = category;
        this.unit = unit;
        this.brand = brand;
    }

    public ProductDigestResponse(long id, long image, String name, long cnt) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.cnt = cnt;

    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }



    public long getImage() {
        return image;
    }
    public void setImage(long image) {
        this.image = image;
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



    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }


    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }


    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }


    public long getCnt() {
        return cnt;
    }
    public void setCnt(long cnt) {
        this.cnt = cnt;
    }
}
