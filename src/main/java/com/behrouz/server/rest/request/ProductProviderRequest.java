package com.behrouz.server.rest.request;

import com.behrouz.server.model.product.ProductImageEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.model.product.ProductProviderPriceEntity;
import com.behrouz.server.model.product.ProductTagEntity;
import com.behrouz.server.utils.thymeleaf.ThymeleafBase64Model;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.request
 * Project serverProductResponse
 * 24 September 2018 09:37
 **/
public class ProductProviderRequest implements ThymeleafBase64Model {

    private long productProviderId; // vase edit azash estfade mishe, sikh nazan

    // Connect Product to Provider
    private long productId;
    private long providerId;
    private long unitId; // vahede forushi k in provider support mikone
    private String unitName;

    private long price; // ghymt vahed
    private float offPercent; // takhfif darsadi
    private long offPrice; // takhfif ghymati

    private String shortDescription;
    private String fullDescription;

    private List<ProductImageRequest> images;

    private boolean exist; // provider darash ya na
    private float rate;
    private float order;


    // Add Product
    private String name;
    private long categoryId;
    private List<Long> tagIds; // id name dare fght


    private double minAllow;
    private double maxAllow;


    public ProductProviderRequest ( ProductProviderEntity productProvider,
                                    ProductProviderPriceEntity productPrice,
                                    List <ProductImageEntity> productProviderImages,
                                    List <ProductTagEntity> tags ) {

        this.productProviderId = productProvider.getId();

        this.providerId = productProvider.getProvider().getId();
        this.unitId = productProvider.getProduct().getProductUnit().getId(); // vahed forush
        this.unitName = productProvider.getProduct().getProductUnit().getName(); // vahed forush

        this.price = productPrice.getFinalAmount();
        this.offPercent = productPrice.getOffPercent();
        this.offPrice = productPrice.getOffPrice();

        this.shortDescription = productProvider.getProduct().getShortDescription();
        this.fullDescription = productProvider.getProduct().getFullDescription();

        if ( productProviderImages != null ) {
            this.images = productProviderImages
                    .stream()
                    .map( e -> new ProductImageRequest( e.getImage(), e.getImageOrder() ) )
                    .collect( Collectors.toList() );
        }

        this.exist = productProvider.isProductProviderExistence();
        this.rate = productProvider.getRate();

        this.order = productProvider.getProductProviderOrder();
        this.minAllow = productProvider.getMinAllow();
        this.maxAllow = productProvider.getMaxAllow();

        this.name = productProvider.getProduct().getName();
        this.categoryId = productProvider.getProduct().getCategory().getId();
        this.tagIds = tags.stream().map( e -> e.getProduct().getId() ).collect( Collectors.toList() );
    }


    public ProductProviderRequest () {
    }


    public double getMinAllow () {
        return minAllow;
    }
    public void setMinAllow ( double minAllow ) {
        this.minAllow = minAllow;
    }


    public double getMaxAllow () {
        return maxAllow;
    }
    public void setMaxAllow ( double maxAllow ) {
        this.maxAllow = maxAllow;
    }


    public String getName () {
        return name;
    }
    public void setName ( String name ) {
        this.name = name;
    }


    public long getCategoryId () {
        return categoryId;
    }
    public void setCategoryId ( long categoryId ) {
        this.categoryId = categoryId;
    }


    public List < Long > getTagIds () {
        return tagIds;
    }
    public void setTagIds ( List < Long > tagIds ) {
        this.tagIds = tagIds;
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


    public long getPrice () {
        return price;
    }
    public void setPrice ( long price ) {
        this.price = price;
    }


    public float getOffPercent () {
        return offPercent;
    }
    public void setOffPercent ( float offPercent ) {
        this.offPercent = offPercent;
    }


    public long getOffPrice () {
        return offPrice;
    }
    public void setOffPrice ( long offPrice ) {
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


    public List< ProductImageRequest > getImages() {
        return images;
    }
    public void setImages( List< ProductImageRequest > images ) {
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


    @Override
    public String toString () {
        return "ProductProviderRequest{" +
                "productProviderId=" + productProviderId +
                ", productId=" + productId +
                ", providerId=" + providerId +
                ", unitId=" + unitId +
                ", unitName='" + unitName + '\'' +
                ", price=" + price +
                ", offPercent=" + offPercent +
                ", offPrice=" + offPrice +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", images=" + images +
                ", exist=" + exist +
                ", rate=" + rate +
                ", order=" + order +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", tagIds=" + tagIds +
                ", minAllow=" + minAllow +
                ", maxAllow=" + maxAllow +
                '}';
    }
}
