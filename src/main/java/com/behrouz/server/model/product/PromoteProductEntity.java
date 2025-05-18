package com.behrouz.server.model.product;

import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 04 July 2020
 **/
@Entity
@Table(name = "product_promote", schema = "public")
public class PromoteProductEntity extends BaseEntity {


    private long orderShow;
    private ProductProviderEntity productProvider;
    private PromoteEntity promote;


    public PromoteProductEntity() {
    }

    public PromoteProductEntity(PromoteEntity promote, ProductProviderEntity productProvider) {
        this.promote = promote;
        this.productProvider = productProvider;
    }

    @ManyToOne
    @JoinColumn(name = "product_provider_id")
    public ProductProviderEntity getProductProvider() {
        return productProvider;
    }

    public void setProductProvider(ProductProviderEntity productProvider) {
        this.productProvider = productProvider;
    }

    @ManyToOne
    @JoinColumn(name = "promote_id")
    public PromoteEntity getPromote() {
        return promote;
    }

    public void setPromote(PromoteEntity promote) {
        this.promote = promote;
    }

    public long getOrderShow() {
        return orderShow;
    }

    public void setOrderShow(long orderShow) {
        this.orderShow = orderShow;
    }
}
