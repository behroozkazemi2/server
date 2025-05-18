package com.behrouz.server.model.global;

import com.behrouz.server.model.base.BaseIdNameEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "banner_type", schema = "public")
public class BannerTypeEntity extends BaseIdNameEntity {
    public BannerTypeEntity() {
    }
    @OneToMany(mappedBy = "bannerType")
    public Set<BannerEntity> bannerEntity;

}
