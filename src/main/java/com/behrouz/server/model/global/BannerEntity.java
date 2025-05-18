package com.behrouz.server.model.global;

import com.behrouz.server.model.base.BaseIdNameEntity;

import javax.persistence.*;


@Entity
@Table(name = "banner", schema = "public")
public class BannerEntity extends BaseIdNameEntity {
    private ImageEntity image;
    private BannerTypeEntity bannerType;
    private String link;

    public BannerEntity() {
    }

    public BannerEntity(ImageEntity image, BannerTypeEntity bannerType, String link) {
        this.image = image;
        this.bannerType = bannerType;
        this.link = link;
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
    @JoinColumn(name = "type_id")
    public BannerTypeEntity getBannerType() {
        return bannerType;
    }
    public void setBannerType(BannerTypeEntity bannerType) {
        this.bannerType = bannerType;
    }


    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
}
