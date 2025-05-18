package com.behrouz.server.model;

import com.behrouz.server.model.base.BaseIdNameEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Set;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 09 July 2020
 **/

@Entity
@Table(name = "candidate_time", schema = "public")
public class CandidateTimeEntity extends BaseIdNameEntity {


    private LocalTime time;


    @OneToMany(mappedBy = "orderTime")
    @JsonBackReference
    private Set<BillEntity> bills;


    public CandidateTimeEntity() {
    }

    @Basic
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

}
