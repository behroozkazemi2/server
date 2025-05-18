package com.behrouz.server.model;

import com.behrouz.server.api.customer.request.CallSupportRequest;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.strategy.StrategyGenerator;

import javax.persistence.*;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.model
 * Project Name: behta-server
 * 10 December 2018
 **/

@Entity
@Table(name = "call_support", schema = "public")
public class CallSupportEntity extends BaseEntity {

    private String subject;
    private String description;
    private String trackingCode;

    private CustomerEntity customer;


    public CallSupportEntity ( CustomerEntity customer,
                               CallSupportRequest callSupportRequest) {

        this.subject = callSupportRequest.getSubject();
        this.description = callSupportRequest.getDescription();

        this.customer = customer;

    }


    @Basic
    public String getSubject () {
        return subject;
    }
    public void setSubject ( String subject ) {
        this.subject = subject;
    }


    @Basic
    public String getDescription () {
        return description;
    }
    public void setDescription ( String description ) {
        this.description = description;
    }

    @Basic
    public String getTrackingCode () {
        return trackingCode;
    }
    public void setTrackingCode ( String trackingCode ) {
        this.trackingCode = trackingCode;
    }


    @ManyToOne
    @JoinColumn( name = "customer_id" )
    public CustomerEntity getCustomer () {
        return customer;
    }
    public void setCustomer ( CustomerEntity customer ) {
        this.customer = customer;
    }


    public CallSupportEntity () {
    }


    @PrePersist
    void prePersist() {
        this.trackingCode = StrategyGenerator.generateCallSupportTrackingCode();
    }
}
