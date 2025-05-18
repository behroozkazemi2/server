package com.behrouz.server.model.ticket;

import com.behrouz.server.model.base.BaseIdNameEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "ticket_importance", schema = "public")
public class TicketImportanceEntity extends BaseIdNameEntity {


    public TicketImportanceEntity() {
    }

    @OneToMany(mappedBy = "ticketImportance")
    @JsonManagedReference
    private Set<TicketEntity> account;


}
