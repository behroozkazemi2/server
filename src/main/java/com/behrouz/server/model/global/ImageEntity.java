package com.behrouz.server.model.global;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.account.OperatorEntity;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.product.ProductImageEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 09:45
 **/

@Entity
@Table(name = "image", schema = "public")
public class ImageEntity extends BaseEntity {


    private byte[] image;

    @OneToMany(mappedBy = "logo")
    public Set<ProviderEntity> provider;


    @OneToMany(mappedBy = "image")
    private Set<CategoryEntity> categoryImage;

    @OneToMany(mappedBy = "image")
    private Set<ProductImageEntity> productImage;

    @OneToMany(mappedBy = "avatar")
    public Set<OperatorEntity> operatorImage;

    @OneToMany(mappedBy = "avatar")
    public Set<CustomerEntity> customerImage;

    @OneToMany(mappedBy = "image")
    public Set<BannerEntity> bannerEntity;


    public ImageEntity() {

    }
    public ImageEntity( byte[] bytes ) {
        this.image = bytes;
    }

    @Basic
    public byte[] getImage() {
        return image;
    }
    public void setImage( byte[] image ) {
        this.image = image;
    }

}
