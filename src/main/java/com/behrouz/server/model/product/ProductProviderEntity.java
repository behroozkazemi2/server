package com.behrouz.server.model.product;

import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.api.customer.request.CustomProductRequest;
import com.behrouz.server.model.CommentEntity;
import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.model.account.ProviderEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 12:22
 **/

@Entity
@Table(name = "product_provider", schema = "public")
public class ProductProviderEntity extends BaseEntity {


    private float rate;

    private float productProviderOrder;

    private boolean productProviderExistence;

    private ProviderEntity provider;

    private ProductEntity product;

    private double minAllow;

    private double maxAllow;

    private long prepareHour;
    private long productCount;
    private long storageCount;

    @OneToMany(mappedBy = "productProvider")
    private Set<ProductProviderPriceEntity> productPrices;


    @OneToMany(mappedBy = "productProvider")
    private Set<CustomerOrderEntity> customerOrders;


    @OneToMany(mappedBy = "productProvider")
    private Set<CommentEntity> comments;

    @OneToMany(mappedBy = "productProvider")
    private Set<OffCodeEntity> offCodes;


    @OneToMany(mappedBy = "productProvider")
    private Set< PromoteProductEntity > promoteProducts;

    public ProductProviderEntity() {
    }


    public ProductProviderEntity(ProductEntity product,ProviderEntity provider,float productProviderOrder, boolean productProviderExistence , double minAllow, double maxAllow, long prepareHour, long productCount) {
        this.product = product;
        this.productProviderOrder = productProviderOrder;
        this.productProviderExistence = productProviderExistence;
        this.provider = provider;
        this.minAllow = minAllow;
        this.maxAllow = maxAllow;
        this.prepareHour = prepareHour;
        this.productCount = productCount;
    }

    public ProductProviderEntity (CustomProductRequest request, ProviderEntity provider ) {
         this.productProviderExistence = true;
         this.provider = provider;

    }

    @Column(columnDefinition = "real default 0")
    public double getMinAllow () {
        return minAllow;
    }
    public void setMinAllow ( double minAllow ) {
        this.minAllow = minAllow;
    }


    @Column(columnDefinition = "real default 0")
    public double getMaxAllow () {
        return maxAllow;
    }
    public void setMaxAllow ( double maxAllow ) {
        this.maxAllow = maxAllow;
    }


    @Basic
    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }

    @Basic
    public float getProductProviderOrder() {
        return productProviderOrder;
    }
    public void setProductProviderOrder( float productProviderOrder ) {
        this.productProviderOrder = productProviderOrder;
    }


    @Basic
    public boolean isProductProviderExistence() {
        return productProviderExistence;
    }
    public void setProductProviderExistence( boolean productProviderExistence ) {
        this.productProviderExistence = productProviderExistence;
    }

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    public ProviderEntity getProvider() {
        return provider;
    }
    public void setProvider( ProviderEntity provider ) {
        this.provider = provider;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public ProductEntity getProduct() {
        return product;
    }
    public void setProduct( ProductEntity product ) {
        this.product = product;
    }

    @Column(columnDefinition = "real default 0")
    public long getProductCount() {
        return productCount;
    }
    public void setProductCount(long productCount) {
        this.productCount = productCount;
    }

    @Column(columnDefinition = "integer default 48")
    public long getPrepareHour() {
        return prepareHour;
    }
    public void setPrepareHour(long prepareHour) {
        this.prepareHour = prepareHour;
    }

    @Column(columnDefinition = "real default 0")
    public long getStorageCount() {
        return storageCount;
    }
    public void setStorageCount(long storageCount) {
        this.storageCount = storageCount;
    }
}
