package com.behrouz.server.rest.request;

import java.util.Date;
import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.request
 * Project Name: behta-server
 * 26 March 2019
 **/
public class DiscountRequest {


    //General
    private float discountPercent;
    private Date startDate;
    private Date expireDate;

    //Only for ProductProvider
    private long productProviderId;

    //Only for Provider
    private long providerId;

    private boolean forProvider;

    private List<Long> productProviderIds;


    public float getDiscountPercent () {
        return discountPercent;
    }
    public void setDiscountPercent ( float discountPercent ) {
        this.discountPercent = discountPercent;
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


    public long getProductProviderId () {
        return productProviderId;
    }
    public void setProductProviderId ( long productProviderId ) {
        this.productProviderId = productProviderId;
    }


    public long getProviderId () {
        return providerId;
    }
    public void setProviderId ( long providerId ) {
        this.providerId = providerId;
    }


    public boolean isForProvider() {
        return forProvider;
    }
    public void setForProvider(boolean forProvider) {
        this.forProvider = forProvider;
    }


    public List<Long> getProductProviderIds() {
        return productProviderIds;
    }
    public void setProductProviderIds(List<Long> productProviderIds) {
        this.productProviderIds = productProviderIds;
    }
}
