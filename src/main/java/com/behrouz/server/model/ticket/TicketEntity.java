package com.behrouz.server.model.ticket;


import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.ticket.project.ProjectEntity;
import com.behrouz.server.utils.StringGeneratorUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "ticket", schema = "public")
public class TicketEntity extends BaseEntity {


    private String subject;

    private String trackingCode;

    private AccountEntity account;

    private boolean closed;

    private boolean read;

    private TicketImportanceEntity ticketImportance;

    //TODO change this to AccountTypeEntity
    private boolean lastMessageDeveloper;
    private ProjectEntity project;
    public TicketEntity() {
    }

    public TicketEntity(String subject,  AccountEntity account, TicketImportanceEntity importance) {
        this.subject = subject;
        this.account = account;
        this.ticketImportance = importance;
        this.trackingCode= StringGeneratorUtil.generateDigit(6);
    }


    public void update(String subject){
      this.subject = subject;

    };

    @Column(columnDefinition = "boolean default false")
    public boolean isRead() {
        return read;
    }
    public void setRead(boolean read) {
        this.read = read;
    }

    @Column(columnDefinition = "boolean default false")
    public boolean isClosed() {
        return closed;
    }
    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTrackingCode() {
        return trackingCode;
    }
    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

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
    @JoinColumn(name = "project_id", nullable = false)
    public ProjectEntity getProject() {
        return project;
    }
    public void setProject(ProjectEntity project) {
        this.project = project;
    }


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ticket_importance_id", nullable = false)
    public TicketImportanceEntity getTicketImportance() {
        return ticketImportance;
    }
    public void setTicketImportance(TicketImportanceEntity ticketImportance) {
        this.ticketImportance = ticketImportance;
    }

    @Column(columnDefinition = "Boolean default false")
    public boolean isLastMessageDeveloper() {
        return lastMessageDeveloper;
    }

    public void setLastMessageDeveloper(boolean lastMessageDeveloper) {
        this.lastMessageDeveloper = lastMessageDeveloper;
    }
}
