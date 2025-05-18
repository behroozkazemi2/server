package com.behrouz.server.model;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.model
 * Project Name: behta-server
 * 19 January 2019
 **/
@Entity
@Table(name = "message", schema = "public")
public class MessageEntity extends BaseEntity {

    private String subject;
    private String content;
    private boolean read;

    private CustomerEntity customer;




    @Basic
    public String getSubject () {
        return subject;
    }
    public void setSubject ( String subject ) {
        this.subject = subject;
    }


    @Basic
    public String getContent () {
        return content;
    }
    public void setContent ( String content ) {
        this.content = content;
    }


    @Basic
    @Column(columnDefinition = "boolean default false")
    public boolean isRead () {
        return read;
    }
    public void setRead ( boolean read ) {
        this.read = read;
    }


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    public CustomerEntity getCustomer () {
        return customer;
    }
    public void setCustomer ( CustomerEntity customer ) {
        this.customer = customer;
    }

    public MessageEntity () {
    }
}
