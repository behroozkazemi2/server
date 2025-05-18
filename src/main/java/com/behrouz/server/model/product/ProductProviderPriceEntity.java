package com.behrouz.server.model.product;

import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 15:23
 **/

@Entity
@Table(name = "product_provider_price", schema = "public")
public class ProductProviderPriceEntity extends BaseEntity {

    private long primitiveAmount;
    private float offPercent;
    private long offPrice;
    private long finalAmount;

    private ProductProviderEntity productProvider;


    public ProductProviderPriceEntity(int id, long primitiveAmount, float offPercent, long offPrice, long finalAmount, ProductProviderEntity productProvider) {
        this.id = id;
        this.primitiveAmount = primitiveAmount;
        this.offPercent = offPercent;
        this.offPrice = offPrice;
        this.finalAmount = finalAmount;
        this.productProvider = productProvider;
    }

    public ProductProviderPriceEntity(long price, long offPrice, float offPercent, long finalAmount, ProductProviderEntity productProvider ) {

        this.primitiveAmount = price;
        this.offPercent = offPercent;
        this.offPrice = offPrice;
        this.finalAmount = finalAmount;
        this.productProvider = productProvider;

    }

    public ProductProviderPriceEntity() {
    }


    public long getPrimitiveAmount () {
        return primitiveAmount;
    }
    public void setPrimitiveAmount ( long primitiveAmount ) {
        this.primitiveAmount = primitiveAmount;
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

    public long getFinalAmount () {
        return finalAmount;
    }
    public void setFinalAmount ( long finalAmount ) {
        this.finalAmount = finalAmount;
    }

    @ManyToOne
    @JoinColumn(name = "product_provider_id", nullable = false)
    public ProductProviderEntity getProductProvider() {
        return productProvider;
    }
    public void setProductProvider( ProductProviderEntity productProvider ) {
        this.productProvider = productProvider;
    }

}
