package com.behrouz.server.rest.request;

import com.behrouz.server.model.product.ProductImageEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.model.product.ProductProviderPriceEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Abolfazl
 * Package com.behrouz.server.rest.request
 * Project newxima
 * 29 January 2019 10:09 AM
 **/
public class WebProductProviderRequest {

    private String name;

    private long productProviderId; // vase edit azash estfade mishe, sikh nazan

    // Connect Product to Provider
    private long productId;
    private long providerId;
    private long unitId; // vahede forushi k in provider support mikone
    private String unitName;
    private float ratio;

    private float price; // ghymt vahed
    private float offPercent; // takhfif darsadi
    private float offPrice; // takhfif ghymati

    private String shortDescription;
    private String fullDescription;

    private List<Long> images;

    private boolean exist; // provider darash ya na
    private float rate;
    private float order;

    public  WebProductProviderRequest ( ProductProviderEntity productProvider,
                                    ProductProviderPriceEntity productPrice,
                                    List <ProductImageEntity> productProviderImages,
                                    float discount) {

        this.name = productProvider.getProduct().getName();

        this.productProviderId = productProvider.getId();

        this.productId = productProvider.getId();
        this.providerId = productProvider.getProvider().getId();
        this.unitId = productProvider.getProduct().getProductUnit().getId();
        this.unitName = productProvider.getProduct().getProductUnit().getName();
        this.ratio = productProvider.getProduct().getProductUnit().getRatio();

        this.price = productPrice.getFinalAmount();
        this.offPercent = discount;
        this.offPrice = productPrice.getOffPrice();

        this.shortDescription = productProvider.getProduct().getShortDescription();
        this.fullDescription = productProvider.getProduct().getFullDescription();

        if ( productProviderImages != null  ){
            this.images = productProviderImages.stream().map(
                    e -> e.getImage().getId()
            ).collect(Collectors.toList());
        }

        this.exist = productProvider.isProductProviderExistence();
        this.rate = productProvider.getRate();
        this.order = productProvider.getProductProviderOrder();

    }

    public WebProductProviderRequest () {
    }


    public long getProductProviderId () {
        return productProviderId;
    }
    public void setProductProviderId ( long productProviderId ) {
        this.productProviderId = productProviderId;
    }


    public long getProductId() {
        return productId;
    }
    public void setProductId( long productId ) {
        this.productId = productId;
    }


    public long getProviderId() {
        return providerId;
    }
    public void setProviderId( long providerId ) {
        this.providerId = providerId;
    }


    public long getUnitId() {
        return unitId;
    }
    public void setUnitId( long unitId ) {
        this.unitId = unitId;
    }


    public String getUnitName () {
        return unitName;
    }
    public void setUnitName ( String unitName ) {
        this.unitName = unitName;
    }


    public float getPrice() {
        return price;
    }
    public void setPrice( float price ) {
        this.price = price;
    }


    public float getOffPercent() {
        return offPercent;
    }
    public void setOffPercent( float offPercent ) {
        this.offPercent = offPercent;
    }


    public float getOffPrice() {
        return offPrice;
    }
    public void setOffPrice( float offPrice ) {
        this.offPrice = offPrice;
    }


    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription( String shortDescription ) {
        this.shortDescription = shortDescription;
    }


    public String getFullDescription() {
        return fullDescription;
    }
    public void setFullDescription( String fullDescription ) {
        this.fullDescription = fullDescription;
    }


    public boolean isExist() {
        return exist;
    }
    public void setExist( boolean exist ) {
        this.exist = exist;
    }


    public List< Long > getImages() {
        return images;
    }
    public void setImages( List< Long > images ) {
        this.images = images;
    }


    public float getRate() {
        return rate;
    }
    public void setRate( float rate ) {
        this.rate = rate;
    }


    public float getOrder() {
        return order;
    }
    public void setOrder( float order ) {
        this.order = order;
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public float getRatio() {
        return ratio;
    }
    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
