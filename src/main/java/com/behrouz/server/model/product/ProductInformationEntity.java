package com.behrouz.server.model.product;

import com.behrouz.server.model.base.BaseIdNameEntity;

import javax.persistence.*;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 14:40
 **/

@Entity
@Table(name = "product_information", schema = "public")
public class ProductInformationEntity extends BaseIdNameEntity {


    private String value;
    private double showOrder;
    private ProductEntity product;
    private InformationCategoryEntity informationCategory;



    public ProductInformationEntity() {
    }


    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public double getShowOrder() {
        return showOrder;
    }
    public void setShowOrder(double showOrder) {
        this.showOrder = showOrder;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public ProductEntity getProduct() {
        return product;
    }
    public void setProduct(ProductEntity product) {
        this.product = product;
    }


    @ManyToOne
    @JoinColumn(name = "information_category_id", nullable = false)
    public InformationCategoryEntity getInformationCategory() {
        return informationCategory;
    }
    public void setInformationCategory(InformationCategoryEntity informationCategory) {
        this.informationCategory = informationCategory;
    }
}

