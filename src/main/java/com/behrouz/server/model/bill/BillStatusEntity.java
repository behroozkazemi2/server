package com.behrouz.server.model.bill;

import com.behrouz.server.model.base.BaseIdNameEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 15:22
 **/

@Entity
@Table(name = "bill_status", schema = "public")
public class BillStatusEntity extends BaseIdNameEntity {

    @OneToMany(mappedBy = "status")
    private Set<BillBillStatusEntity> bills;

    public BillStatusEntity () {
    }


    public BillStatusEntity id(long id){
        this.id = id;
        return this;
    }


}
