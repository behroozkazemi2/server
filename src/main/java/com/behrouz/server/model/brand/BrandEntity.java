package com.behrouz.server.model.brand;

import com.behrouz.server.model.base.BaseIdNameEntity;
import com.behrouz.server.model.product.ProductEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 15:23
 **/

@Entity
@Table(name = "brand", schema = "public")
public class BrandEntity extends BaseIdNameEntity {

    private String description;


    @OneToMany(mappedBy = "brand")
    private Set<ProductEntity> brand;

    @OneToMany(mappedBy = "brand")
    private Set<BrandCategoryEntity> brandCategory;


    public BrandEntity() {
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


}
