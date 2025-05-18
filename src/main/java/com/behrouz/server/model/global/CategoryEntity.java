package com.behrouz.server.model.global;

import com.behrouz.server.model.base.BaseIdNameEntity;
import com.behrouz.server.model.brand.BrandCategoryEntity;
import com.behrouz.server.model.product.ProductEntity;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "category", schema = "public")
public class CategoryEntity extends BaseIdNameEntity {

    private ImageEntity image;
    private String description;
    private String icon;
    private CategoryEntity parent;
    private double showOrder;

    @OneToMany(mappedBy = "category")
    private Set<ProductEntity> product;

    @OneToMany(mappedBy = "category")
    private Set<BrandCategoryEntity> brandCategory;

    @OneToMany(mappedBy = "parent")
    private Set<CategoryEntity> categoryParent;

    public CategoryEntity() {
    }



    public CategoryEntity(String category) {
        if (category != null && !category.isEmpty()) {
            this.name = category;
        }
    }

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = true)
    public ImageEntity getImage() {
        return image;
    }
    public void setImage(ImageEntity image) {
        this.image = image;
    }
    @ManyToOne

    @JoinColumn(name = "parent_id", nullable = true)
    public CategoryEntity getParent() {
        return parent;
    }
    public void setParent(CategoryEntity parent) {
        this.parent = parent;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(columnDefinition = "real default 0")
    public double getShowOrder() {
        return showOrder;
    }
    public void setShowOrder(double showOrder) {
        this.showOrder = showOrder;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
}
