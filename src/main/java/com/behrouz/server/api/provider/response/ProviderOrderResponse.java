package com.behrouz.server.api.provider.response;

import com.behrouz.server.api.customer.data.LatLngData;

import java.util.Date;
import java.util.List;

public class ProviderOrderResponse {

    private List<Long> images;

    private Date insertDate;

    private String address;

    private LatLngData point;

    private long amount;



    public ProviderOrderResponse() {
    }

    public List<Long> getImages() {
        return images;
    }
    public void setImages(List<Long> images) {
        this.images = images;
    }




    public Date getInsertDate() {
        return insertDate;
    }
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }



    public LatLngData getPoint() {
        return point;
    }
    public void setPoint(LatLngData point) {
        this.point = point;
    }



    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }



}
