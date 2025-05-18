package com.behrouz.server.model.bill;


import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.product.ProductProviderEntity;

import javax.persistence.*;

@Entity
@Table(name = "bill_product_provider", schema = "public")
public class BillProductProviderEntity extends BaseEntity {


    private BillEntity bill;

    private ProductProviderEntity productProvider;

    private float orderCount;

    private long priceId;

    private long realAmount; // Real Value of products
    private long discountAmount;    // kol takhfif ha
    private long payableAmount; // nahaii


    public BillProductProviderEntity() {
    }


    public BillProductProviderEntity(BillEntity bill, ProductProviderEntity productProvider, long priceId, long payableAmount, float count, long realAmount, long discountAmount ) {
        this.bill = bill;
        this.productProvider = productProvider;
        this.priceId = priceId;
        this.payableAmount = payableAmount;
        this.orderCount = count;
        this.realAmount = (long) (realAmount * count);
        this.discountAmount = (long) (discountAmount * count);
    }


    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    public BillEntity getBill() {
        return bill;
    }
    public void setBill(BillEntity bill) {
        this.bill = bill;
    }


    @ManyToOne
    @JoinColumn(name = "product_provider_id", nullable = false)
    public ProductProviderEntity getProductProvider() {
        return productProvider;
    }
    public void setProductProvider(ProductProviderEntity productProvider) {
        this.productProvider = productProvider;
    }


    @Basic
    public long getPriceId() {
        return priceId;
    }
    public void setPriceId(long priceId) {
        this.priceId = priceId;
    }

    @Basic
    public long getRealAmount() {
        return realAmount;
    }
    public void setRealAmount(long realAmount) {
        this.realAmount = realAmount;
    }

    @Basic
    public long getDiscountAmount() {
        return discountAmount;
    }
    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Basic
    public long getPayableAmount() {
        return payableAmount;
    }
    public void setPayableAmount(long payableAmount) {
        this.payableAmount = payableAmount;
    }


    public float getOrderCount() {
        return orderCount;
    }
    public void setOrderCount(float orderCount) {
        this.orderCount = orderCount;
    }

    @Override
    public String toString() {
        return "BillProductProviderEntity{" +
                "payableAmount=" + payableAmount +
                ", discountAmount=" + discountAmount +
                ", realAmount=" + realAmount +
                ", priceId=" + priceId +
                ", orderCount=" + orderCount +
                ", productProvider=" + productProvider +
                ", bill=" + bill +
                '}';
    }
}
