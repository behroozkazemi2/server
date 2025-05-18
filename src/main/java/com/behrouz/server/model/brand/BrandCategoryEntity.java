package com.behrouz.server.model.brand;

import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.global.CategoryEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "brand_category", schema = "public")
public class BrandCategoryEntity extends BaseEntity {


    private BrandEntity brand;
    private CategoryEntity category;


    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    public BrandEntity getBrand() {
        return brand;
    }
    public void setBrand(BrandEntity brand) {
        this.brand = brand;
    }

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    public CategoryEntity getCategory() {
        return category;
    }
    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
}
