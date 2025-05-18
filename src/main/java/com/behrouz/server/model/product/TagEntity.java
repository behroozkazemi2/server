package com.behrouz.server.model.product;

import com.behrouz.server.model.base.BaseIdNameEntity;
import com.behrouz.server.api.provider.request.TagAddRequest;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 14:40
 **/

@Entity
@Table(name = "tag", schema = "public")
public class TagEntity extends BaseIdNameEntity {

    @OneToMany(mappedBy = "tag")
    private Set< ProductTagEntity > productTag;

    public TagEntity() {
    }

    public TagEntity(TagAddRequest tagRequest) {
        this.name = tagRequest.getName();
    }

}
