package com.behrouz.server.rest.request;

import com.behrouz.server.api.customer.data.LatLngData;
import com.behrouz.server.utils.thymeleaf.ThymeleafBase64Model;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.request
 * Project Koala Server
 * 10 September 2018 12:20
 **/
public class ProviderRequest implements ThymeleafBase64Model {

    private long id; // 0 -> new , ow -> edit

    private String name;

    private String shortDescription;

    private String fullDescription;

    private long logoId;

    private List< Integer > providerImage;

    private long minDay;

    private String address;

    private String phone;

    private String instagramId;

    private String telegramId;

    private List<LatLngData> location;
     private String locationString;




    public ProviderRequest() {
    }





    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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



    public long getLogoId() {
        return logoId;
    }
    public void setLogoId(long logoId) {
        this.logoId = logoId;
    }



    public List<Integer> getProviderImage() {
        return providerImage;
    }
    public void setProviderImage(List<Integer> providerImage) {
        this.providerImage = providerImage;
    }





    public long getMinDay() {
        return minDay;
    }
    public void setMinDay(long minDay) {
        this.minDay = minDay;
    }



    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


    public String getInstagramId() {
        return instagramId;
    }
    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }


    public String getTelegramId() {
        return telegramId;
    }
    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public List<LatLngData> getLocation() {
        return location;
    }
    public void setLocation(List<LatLngData> location) {
        this.location = location;
    }

    public String getLocationString() {
        return locationString;
    }
    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }
}
