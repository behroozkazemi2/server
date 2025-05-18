package com.behrouz.server.model.product;

import com.behrouz.server.model.base.BaseIdNameEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 04 July 2020
 **/
@Entity
@Table(name = "promote", schema = "public")
public class PromoteEntity extends BaseIdNameEntity {

    private LocalDateTime startDate;
    private LocalDateTime endDate;


    @OneToMany(mappedBy = "promote")
    private Set< PromoteProductEntity > promoteProducts;

    public PromoteEntity() {
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

}
