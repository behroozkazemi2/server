package com.behrouz.server.rest.response.digestList;

import com.behrouz.server.api.customer.data.LatLngData;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.utils.LocalDateTimeUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.response.digestList
 * Project Koala Server
 * 09 September 2018 15:52
 **/
public class ProviderListDigestResponse {

    private long id;

    private String name;


    private String address;// آدرس

    private String phone;// شماره تلفن ها

    private long imageId;// آیدی تصویر اصلی تامین کننده

    private boolean active;

    private Date insertDate;

    private long minDay;

    private double rate;

    private String shortDescription;

    private String fullDescription;


    protected String instagramId;

    private String telegramId;

    private long regionId;

    private List<LatLngData> location;



    public ProviderListDigestResponse() {
    }

    public ProviderListDigestResponse(long id, String name, String address, String phone, long imageId, boolean active, Date insertDate, long minDay, double rate, String shortDescription, String fullDescription, String instagramId, String telegramId,long regionId,List<LatLngData> location ) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.imageId = imageId;
        this.active = active;
        this.insertDate = insertDate;
        this.minDay = minDay;
        this.rate = rate;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.instagramId = instagramId;
        this.telegramId = telegramId;
        this.regionId = regionId;
        this.location = location;
    }

    public ProviderListDigestResponse(ProviderEntity provider, List<LatLngData> points) {
        this(
                provider.getId(),
                provider.getName(),
                provider.getAddress(),
                provider.getPhone(),
                provider.getLogo() == null ? 0 : provider.getLogo().getId(),
                provider.isActive(),
                LocalDateTimeUtil.localDateTimeToDate(provider.getInsertDate()),
                provider.getMinDay(),
                provider.getRate(),
                provider.getShortDescription(),
                provider.getFullDescription(),
                provider.getInstagramId(),
                provider.getTelegramId(),
                provider.getRegion().getId(),
                points
        );
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



    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }



    public String getPhone() {
        return phone;
    }
    public void setPhone( String phone ) {
        this.phone = phone;
    }



    public long getImageId() {
        return imageId;
    }
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }


    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }



    public Date getInsertDate() {
        return insertDate;
    }
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }


    public long getMinDay() {
        return minDay;
    }
    public void setMinDay(long minDay) {
        this.minDay = minDay;
    }



    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
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


    public long getRegionId() {
        return regionId;
    }
    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public List<LatLngData> getLocation() {
        return location;
    }
    public void setLocation(List<LatLngData> location) {
        this.location = location;
    }
}
