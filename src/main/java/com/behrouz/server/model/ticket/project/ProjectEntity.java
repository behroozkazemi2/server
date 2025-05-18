package com.behrouz.server.model.ticket.project;


import com.behrouz.server.model.base.BaseIdNameEntity;
import com.behrouz.server.model.ticket.TicketEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "project", schema = "public")
public class ProjectEntity extends BaseIdNameEntity {


    public ProjectEntity() {
    }

    public ProjectEntity(String name) {
        this.name = name;
    }




    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private Set<TicketEntity> ticket;


}
