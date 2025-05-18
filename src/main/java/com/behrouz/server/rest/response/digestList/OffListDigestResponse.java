package com.behrouz.server.rest.response.digestList;

import com.behrouz.server.rest.response.OffCodePanelResponse;

import java.util.Date;

/**
 * Created by Abolfazl
 * Package com.behrouz.server.rest.response.digestList
 * Project newxima
 * 04 April 2019 9:53 AM
 **/
public class OffListDigestResponse {

    private long id;
    private String code;
    private boolean forAll;
    private Date insertDate;
    private Date startDate;
    private Date expireDate;
    private float percent;
    private float minAmount;
    private float maxAmount;
    private String description;
    private long maxUsageCount;
    private boolean deleted;

    private boolean forCustomer;
    private String customerFirstName;
    private String customerLastName;
    private String customerMobile;

    private boolean forProvider;
    private String providerName;
    private long providerImageId;

    private boolean forProductProvider;
    private String productProviderName;
    private long productProviderImageId;


    public OffListDigestResponse(OffCodePanelResponse e) {
        this.id = e.getId();
        this.code = e.getCode();
        this.forAll = e.isForAll();
        this.insertDate = e.getInsertDate();
        this.startDate = e.getStartDate();
        this.expireDate = e.getExpireDate();
        this.percent = e.getPercent();
        this.minAmount = e.getMinAmount();
        this.maxAmount = e.getMaxAmount();
        this.description = e.getDescription();
        this.maxUsageCount = e.getMaxUsageCount();
        this.deleted = e.isDeleted();
        if ( e.isForCustomer() ){
            this.forCustomer = true;
            this.customerFirstName = e.getCustomerFirstName();
            this.customerLastName = e.getCustomerLastName();
            this.customerMobile = e.getCustomerMobile();
        }

        if ( e.isForProvider() ) {
            this.forProvider = true;
            this.providerName = e.getProviderName();
            this.providerImageId = e.getProviderImageId();
        }

        if ( e.isForProductProvider() ){
            this.forProductProvider = true;
            this.productProviderName = e.getProductName();
            this.productProviderImageId = e.getProductProviderImageId();
        }
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public boolean isForAll() {
        return forAll;
    }
    public void setForAll(boolean forAll) {
        this.forAll = forAll;
    }


    public Date getInsertDate() {
        return insertDate;
    }
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }


    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getExpireDate() {
        return expireDate;
    }
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }


    public float getPercent() {
        return percent;
    }
    public void setPercent(float percent) {
        this.percent = percent;
    }


    public float getMinAmount() {
        return minAmount;
    }
    public void setMinAmount(float minAmount) {
        this.minAmount = minAmount;
    }


    public float getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount(float maxAmount) {
        this.maxAmount = maxAmount;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public long getMaxUsageCount() {
        return maxUsageCount;
    }
    public void setMaxUsageCount(long maxUsageCount) {
        this.maxUsageCount = maxUsageCount;
    }


    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    public boolean isForCustomer() {
        return forCustomer;
    }
    public void setForCustomer(boolean forCustomer) {
        this.forCustomer = forCustomer;
    }


    public String getCustomerFirstName() {
        return customerFirstName;
    }
    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }


    public String getCustomerLastName() {
        return customerLastName;
    }
    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }


    public String getCustomerMobile() {
        return customerMobile;
    }
    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }


    public boolean isForProvider() {
        return forProvider;
    }
    public void setForProvider(boolean forProvider) {
        this.forProvider = forProvider;
    }


    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }


    public long getProviderImageId() {
        return providerImageId;
    }
    public void setProviderImageId(long providerImageId) {
        this.providerImageId = providerImageId;
    }


    public boolean isForProductProvider() {
        return forProductProvider;
    }
    public void setForProductProvider(boolean forProductProvider) {
        this.forProductProvider = forProductProvider;
    }


    public String getProductProviderName() {
        return productProviderName;
    }
    public void setProductProviderName(String productProviderName) {
        this.productProviderName = productProviderName;
    }


    public long getProductProviderImageId() {
        return productProviderImageId;
    }
    public void setProductProviderImageId(long productProviderImageId) {
        this.productProviderImageId = productProviderImageId;
    }

}
