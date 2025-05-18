package com.behrouz.server.model.order;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.model.product.ProductProviderEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 12:20
 **/

@Entity
@Table(name = "customer_order", schema = "public")
public class CustomerOrderEntity extends BaseEntity {

    private float orderCount;
    private CustomerEntity customer;
    private ProductProviderEntity productProvider;

    @OneToMany(mappedBy = "order")
    private Set<BillProductProviderEntity> billCustomerOrder;


    public CustomerOrderEntity() {
    }

    public CustomerOrderEntity( ProductProviderEntity productProvider, CustomerEntity customer, float orderCount ) {
        this.productProvider = productProvider;
        this.customer = customer;
        this.orderCount = orderCount;
    }

    @Basic
    public float getOrderCount() {
        return orderCount;
    }
    public void setOrderCount( float orderCount ) {
        this.orderCount = orderCount;
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
    @JoinColumn(name = "product_provider_id")
    public ProductProviderEntity getProductProvider() {
        return productProvider;
    }
    public void setProductProvider( ProductProviderEntity productProvider ) {
        this.productProvider = productProvider;
    }
}
