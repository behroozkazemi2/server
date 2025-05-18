package com.behrouz.server.model.product;

import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.rest.request.ProductImageRequest;

import javax.persistence.*;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 14:43
 **/

@Entity
@Table(name = "product_image", schema = "public")
public class ProductImageEntity extends BaseEntity {

    private long imageOrder;

    private ProductEntity product;
    private ImageEntity image;


    public ProductImageEntity() {
    }

    public ProductImageEntity(ProductImageRequest productProviderImage,
                              ImageEntity image,
                              ProductEntity newProduct) {

        this.imageOrder = productProviderImage.getImageOrder();
        this.product = newProduct;
        this.image = image;

    }

    public ProductImageEntity(ProductEntity product, ImageEntity newImage ) {
        this.product = product;
        this.image = newImage;
    }

    @Basic
    public long getImageOrder() {
        return imageOrder;
    }
    public void setImageOrder( long imageOrder ) {
        this.imageOrder = imageOrder;
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
    @JoinColumn(name = "image_id", nullable = false)
    public ImageEntity getImage() {
        return image;
    }
    public void setImage( ImageEntity image ) {
        this.image = image;
    }



}
