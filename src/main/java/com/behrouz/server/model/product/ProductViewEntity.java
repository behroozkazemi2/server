package com.behrouz.server.model.product;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.base.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 14:59
 **/

@Entity
@Table(name = "product_view", schema = "public")
public class ProductViewEntity extends BaseEntity {

    private Date visitDate;

    private ProductEntity product;
    private CustomerEntity customer;
    private ProductTagEntity productTag;

    @Basic
    @CreationTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    public Date getVisitDate() {
        return visitDate;
    }
    public void setVisitDate( Date visitDate ) {
        this.visitDate = visitDate;
    }


    @ManyToOne
    @JoinColumn(name = "product__id", nullable = false)
    public ProductEntity getProduct() {
        return product;
    }
    public void setProduct( ProductEntity productTag ) {
        this.product = product;
    }


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    public CustomerEntity getCustomer() {
        return customer;
    }
    public void setCustomer( CustomerEntity customer ) {
        this.customer = customer;
    }


    @ManyToOne
    @JoinColumn(name = "product_tag_id", nullable = false)
    public ProductTagEntity getProductTag() {
        return productTag;
    }
    public void setProductTag( ProductTagEntity productTag ) {
        this.productTag = productTag;
    }

    public ProductViewEntity() {
    }
}
