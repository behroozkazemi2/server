package com.behrouz.server.model.product;

import com.behrouz.server.model.base.BaseIdNameEntity;
import com.behrouz.server.model.brand.BrandEntity;
import com.behrouz.server.model.global.CategoryEntity;
import com.behrouz.server.model.global.UnitEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 15:23
 **/

@Entity
@Table(name = "product", schema = "public")
public class ProductEntity extends BaseIdNameEntity {


    private String shortDescription;
    private String fullDescription;
    private UnitEntity productUnit;
    private double unitStep;

    private BrandEntity brand;
    private CategoryEntity category;


    @OneToMany(mappedBy = "product")
    private Set<ProductProviderEntity> productProvider;

    @OneToMany(mappedBy = "product")
    private Set<ProductImageEntity> productImage;

    @OneToMany(mappedBy = "product")
    private Set<ProductTagEntity> productTag;

    @OneToMany(mappedBy = "product")
    private Set<ProductViewEntity> productView;


    @OneToMany(mappedBy = "product")
    private Set<ProductInformationEntity> productInformationEntities;


    public ProductEntity() {
    }

    public ProductEntity(String name, String shortDescription, String fullDescription, UnitEntity productUnit, double unitStep, BrandEntity brand, CategoryEntity category) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.productUnit = productUnit;
        this.unitStep = unitStep;
        this.brand = brand;
        this.category = category;
    }


    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public double getUnitStep() {
        return unitStep;
    }

    public void setUnitStep(double unitStep) {
        this.unitStep = unitStep;
    }


    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    public UnitEntity getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(UnitEntity productUnit) {
        this.productUnit = productUnit;
    }

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
