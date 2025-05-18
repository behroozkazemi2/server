package com.behrouz.server.model.product;

import com.behrouz.server.model.base.BaseIdNameEntity;
import com.behrouz.server.api.provider.request.TagAddRequest;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 14:40
 **/

@Entity
@Table(name = "information_category", schema = "public")
public class InformationCategoryEntity extends BaseIdNameEntity {

    private Double showOrder;


    @OneToMany(mappedBy = "informationCategory")
    private Set<ProductInformationEntity> productInformationEntities;
    public InformationCategoryEntity() {
    }

    public InformationCategoryEntity(TagAddRequest tagRequest) {
        this.name = tagRequest.getName();
    }


    public Double getShowOrder() {
        return showOrder;
    }
    public void setShowOrder(Double showOrder) {
        this.showOrder = showOrder;
    }
}
