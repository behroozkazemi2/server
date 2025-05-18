package com.behrouz.server.model.global;

import com.behrouz.server.model.base.BaseIdNameEntity;
import com.behrouz.server.model.product.ProductEntity;
import com.behrouz.server.rest.response.digestList.UnitListDigestResponse;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 14:26
 **/

@Entity
@Table(name = "unit", schema = "public")
public class UnitEntity extends BaseIdNameEntity {

    private boolean dividable;
    private float ratio;
    private float tolerance;

    @OneToMany(mappedBy = "productUnit")
    private Set<ProductEntity> productUnit;




    public UnitEntity() {
    }

    public UnitEntity(UnitListDigestResponse tagRequest) {
        this.name = tagRequest.getName();
        this.dividable = tagRequest.isDividable();
        this.ratio = tagRequest.getRatio();
        this.tolerance = tagRequest.getTolerance();
    }

    @Basic
    public boolean isDividable() {
        return dividable;
    }
    public void setDividable( boolean dividable ) {
        this.dividable = dividable;
    }


    @Basic
    public float getRatio() {
        return ratio;
    }
    public void setRatio( float ratio ) {
        this.ratio = ratio;
    }

    @Basic
    public float getTolerance() {
        return tolerance;
    }
    public void setTolerance( float tolerance ) {
        this.tolerance = tolerance;
    }

    public void update(UnitListDigestResponse tagRequest) {
        this.name = tagRequest.getName();
        this.dividable = tagRequest.isDividable();
        this.ratio = tagRequest.getRatio();
        this.tolerance = tagRequest.getTolerance();
    }
}
