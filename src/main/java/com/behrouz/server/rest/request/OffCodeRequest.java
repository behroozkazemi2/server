package com.behrouz.server.rest.request;

import java.util.Date;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.request
 * Project Name: behta-server
 * 27 March 2019
 **/
public class OffCodeRequest {

    private String code;
    private Date startDate;
    private Date expireDate;
    private float percent;
    private float minAmount;
    private float maxAmount;
    private String description;
    private int usageCount;

    private int customerId;
    private int providerId;
    private int productProviderId;


    public String getCode () {
        return code;
    }
    public void setCode ( String code ) {
        this.code = code;
    }

    public Date getStartDate () {
        return startDate;
    }
    public void setStartDate ( Date startDate ) {
        this.startDate = startDate;
    }

    public Date getExpireDate () {
        return expireDate;
    }
    public void setExpireDate ( Date expireDate ) {
        this.expireDate = expireDate;
    }

    public float getPercent () {
        return percent;
    }
    public void setPercent ( float percent ) {
        this.percent = percent;
    }

    public float getMinAmount () {
        return minAmount;
    }
    public void setMinAmount ( float minAmount ) {
        this.minAmount = minAmount;
    }

    public float getMaxAmount () {
        return maxAmount;
    }
    public void setMaxAmount ( float maxAmount ) {
        this.maxAmount = maxAmount;
    }

    public String getDescription () {
        return description;
    }
    public void setDescription ( String description ) {
        this.description = description;
    }

    public int getUsageCount () {
        return usageCount;
    }
    public void setUsageCount ( int usageCount ) {
        this.usageCount = usageCount;
    }

    public int getCustomerId () {
        return customerId;
    }
    public void setCustomerId ( int customerId ) {
        this.customerId = customerId;
    }

    public int getProviderId () {
        return providerId;
    }
    public void setProviderId ( int providerId ) {
        this.providerId = providerId;
    }

    public int getProductProviderId () {
        return productProviderId;
    }
    public void setProductProviderId ( int productProviderId ) {
        this.productProviderId = productProviderId;
    }
}
