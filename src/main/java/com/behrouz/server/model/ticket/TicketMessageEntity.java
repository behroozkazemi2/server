package com.behrouz.server.model.ticket;


import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ticket_message", schema = "public")
public class TicketMessageEntity extends BaseEntity {

    @Lob
    private String text;

    private TicketEntity ticket;

    private AccountEntity account;




    public TicketMessageEntity(String text, TicketEntity ticket, AccountEntity account) {
        this.text = text;
        this.ticket = ticket;
        this.account = account;
    }


    public TicketMessageEntity() {
    }

    @Column(length = 2550)
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }


    @OneToMany(mappedBy = "ticketMessage")
    @JsonManagedReference
    private Set<TicketMessageDocumentEntity> ticketMessageDocumnet;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "account_id", nullable = false)
    public AccountEntity getAccount() {
        return account;
    }
    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ticket_id", nullable = false)
    public TicketEntity getTicket() {
        return ticket;
    }
    public void setTicket(TicketEntity ticket) {
        this.ticket = ticket;
    }

}
