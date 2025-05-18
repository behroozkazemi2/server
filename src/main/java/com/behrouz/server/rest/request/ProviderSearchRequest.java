package com.behrouz.server.rest.request;

/**
 * Created by Abolfazl
 * Package com.hashtagmarket.webapplication.okhttp.model.request
 * Project xima-webapplication
 * 21 January 2019 3:55 PM
 **/
public class ProviderSearchRequest extends ListRequest {

    private String search;

    private long providerId;
    private long status;
    private String customerName;
    private String trackingCode;
    private String customerMobile;
    private long orderDate;
    private long category;
    private long brand;
    private long deliverDate;


    public ProviderSearchRequest() {
    }

    public ProviderSearchRequest(int page, int length) {
        super(page, length);
    }

    public ProviderSearchRequest(int page, int length, String search) {
        super(page, length);
        this.search = search;
    }
    public ProviderSearchRequest(int page, int length, String search,
                                 long status,
                                 String customerName,
                                 String trackingCode,
                                 String customerMobile,
                                 long orderDate,
                                 long deliverDate) {
        super(page, length);
        this.search = search;
        this.status = status;
        this.customerName = customerName;
        this.trackingCode = trackingCode;
        this.customerMobile = customerMobile;
        this.orderDate = orderDate;
        this.deliverDate = deliverDate;
    }

    public String getSearch() {
        return search;
    }
    public void setSearch(String search) {
        this.search = search;
    }


    public long getProviderId() {
        return providerId;
    }
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }


    public long getStatus() {
        return status;
    }
    public void setStatus(long status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTrackingCode() {
        return trackingCode;
    }
    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }
    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public long getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public long getDeliverDate() {
        return deliverDate;
    }
    public void setDeliverDate(long deliverDate) {
        this.deliverDate = deliverDate;
    }

    public long getCategory() {
        return category;
    }
    public void setCategory(long category) {
        this.category = category;
    }

    public long getBrand() {
        return brand;
    }
    public void setBrand(long brand) {
        this.brand = brand;
    }
}
