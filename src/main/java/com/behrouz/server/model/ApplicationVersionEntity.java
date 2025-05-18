package com.behrouz.server.model;

import com.behrouz.server.model.account.CustomerLoginEntity;
import com.behrouz.server.model.base.BaseIdNameEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 11:41
 **/

@Entity
@Table(name = "application_version", schema = "public")
public class ApplicationVersionEntity extends BaseIdNameEntity {

    private Date releaseDate;
    private boolean forceInstall;

    private Set<CustomerLoginEntity> customerLogin;


    @Basic
    public Date getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate( Date releaseDate ) {
        this.releaseDate = releaseDate;
    }


    @Basic
    public boolean isForceInstall() {
        return forceInstall;
    }
    public void setForceInstall( boolean forceInstall ) {
        this.forceInstall = forceInstall;
    }


    @OneToMany(mappedBy = "applicationVersion")
    @JsonBackReference
    public Set< CustomerLoginEntity > getCustomerLogin() {
        return customerLogin;
    }
    public void setCustomerLogin( Set< CustomerLoginEntity > customerLogin ) {
        this.customerLogin = customerLogin;
    }

    public ApplicationVersionEntity() {
    }
}
