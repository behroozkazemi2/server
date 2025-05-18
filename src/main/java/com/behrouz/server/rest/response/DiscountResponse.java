package com.behrouz.server.rest.response;

import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.model.product.ProductImageEntity;
import com.behrouz.server.utils.LocalDateTimeUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.response
 * Project Name: behta-server
 * 26 March 2019
 **/
public class DiscountResponse {

    private long id;
    private float discountPercent;
    private Date insertDate;
    private Date startDate;
    private Date expireDate;
    private boolean deleted;

    private String productProviderName;
    private long productProvideImageId;

    private String providerName;

    public DiscountResponse ( OffCodeEntity discount,
                              List <ProductImageEntity> productProviderImages) {

        this.id = discount.getId();
        this.discountPercent = discount.getPercent();
        this.insertDate = LocalDateTimeUtil.localDateTimeToDate(discount.getInsertDate());
        this.startDate = discount.getStartDate();
        this.expireDate = discount.getExpireDate();
        this.deleted = discount.isDeleted();

        this.productProviderName = discount.getProductProvider().getProduct().getName();
        this.productProvideImageId =
                productProviderImages == null || productProviderImages.isEmpty()
                        ? 0
                        : productProviderImages.get( 0 ).getImage().getId() ;

        this.providerName = discount.getProductProvider().getProvider().getName();
    }


    public long getId () {
        return id;
    }
    public void setId ( long id ) {
        this.id = id;
    }

    public float getDiscountPercent () {
        return discountPercent;
    }
    public void setDiscountPercent ( float discountPercent ) {
        this.discountPercent = discountPercent;
    }

    public Date getInsertDate () {
        return insertDate;
    }
    public void setInsertDate ( Date insertDate ) {
        this.insertDate = insertDate;
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

    public boolean isDeleted () {
        return deleted;
    }
    public void setDeleted ( boolean deleted ) {
        this.deleted = deleted;
    }

    public String getProductProviderName () {
        return productProviderName;
    }
    public void setProductProviderName ( String productProviderName ) {
        this.productProviderName = productProviderName;
    }

    public long getProductProvideImageId () {
        return productProvideImageId;
    }
    public void setProductProvideImageId ( long productProvideImageId ) {
        this.productProvideImageId = productProvideImageId;
    }

    public String getProviderName () {
        return providerName;
    }
    public void setProviderName ( String providerName ) {
        this.providerName = providerName;
    }

}
