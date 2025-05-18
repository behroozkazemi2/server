package com.behrouz.server.model.product;

import com.behrouz.server.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 14:40
 **/

@Entity
@Table(name = "product_tag", schema = "public")
public class ProductTagEntity extends BaseEntity {


    private TagEntity tag;
    private ProductEntity product;


    private Set< ProductViewEntity > productViews;



    public ProductTagEntity() {
    }


    public ProductTagEntity(TagEntity tag, ProductEntity product) {
        this.tag = tag;
        this.product = product;

    }


    public ProductTagEntity(ProductTagEntity productTag) {
        this.tag = productTag.getTag();
        this.product = productTag.getProduct();
    }


    @OneToMany(mappedBy = "productTag")
    @JsonBackReference
    public Set< ProductViewEntity > getProductViews() {
        return productViews;
    }
    public void setProductViews( Set< ProductViewEntity > productViews ) {
        this.productViews = productViews;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public ProductEntity getProduct() {
        return product;
    }
    public void setProduct( ProductEntity product ) {
        this.product = product;
    }


  @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    public TagEntity getTag() {
        return tag;
    }
    public void setTag( TagEntity tag ) {
        this.tag = tag;
    }




}
