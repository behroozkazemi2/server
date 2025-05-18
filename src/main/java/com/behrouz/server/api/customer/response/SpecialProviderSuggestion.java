package com.behrouz.server.api.customer.response;

import com.behrouz.server.rest.request.IdName;

import java.util.Date;

public class SpecialProviderSuggestion {

    private long id;

    private IdName provider;

    private float rate;

    private int providerImage;

    private IdName unit;

    private double countValue;

    private long unitAmount;

    private long extraAmount;

    private long finalAmount;

    private String description;

    private int creationHour;

    private Date date;


    public SpecialProviderSuggestion() {
    }

    public SpecialProviderSuggestion(long id, IdName provider, float rate, int providerImage, IdName unit, double countValue, long unitAmount, long extraAmount, long finalAmount, String description, int creationHour, Date date) {
        this.id = id;
        this.provider = provider;
        this.rate = rate;
        this.providerImage = providerImage;
        this.unit = unit;
        this.countValue = countValue;
        this.unitAmount = unitAmount;
        this.extraAmount = extraAmount;
        this.finalAmount = finalAmount;
        this.description = description;
        this.creationHour = creationHour;
        this.date = date;
    }


    public SpecialProviderSuggestion toToman(){

        this.unitAmount /= 10;
        this.extraAmount /= 10;
        this.finalAmount /= 10;

        return this;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }



    public IdName getProvider() {
        return provider;
    }
    public void setProvider(IdName provider) {
        this.provider = provider;
    }


    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }


    public int getProviderImage() {
        return providerImage;
    }
    public void setProviderImage(int providerImage) {
        this.providerImage = providerImage;
    }



    public IdName getUnit() {
        return unit;
    }
    public void setUnit(IdName unit) {
        this.unit = unit;
    }


    public double getCountValue() {
        return countValue;
    }
    public void setCountValue(double countValue) {
        this.countValue = countValue;
    }



    public long getUnitAmount() {
        return unitAmount;
    }
    public void setUnitAmount(long unitAmount) {
        this.unitAmount = unitAmount;
    }



    public long getExtraAmount() {
        return extraAmount;
    }
    public void setExtraAmount(long extraAmount) {
        this.extraAmount = extraAmount;
    }



    public long getFinalAmount() {
        return finalAmount;
    }
    public void setFinalAmount(long finalAmount) {
        this.finalAmount = finalAmount;
    }



    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }



    public int getCreationHour() {
        return creationHour;
    }
    public void setCreationHour(int creationHour) {
        this.creationHour = creationHour;
    }



    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
