package com.behrouz.server.model;

import com.behrouz.server.model.account.CustomerLoginEntity;
import com.behrouz.server.model.base.BaseIdNameEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 11:38
 **/

@Entity
@Table(name = "mobile_platform", schema = "public")
public class MobilePlatformEntity extends BaseIdNameEntity {


    private Set<CustomerLoginEntity> customerLogin;

    @OneToMany(mappedBy = "mobilePlatform")
    @JsonBackReference
    public Set< CustomerLoginEntity > getCustomerLogin() {
        return customerLogin;
    }
    public void setCustomerLogin( Set< CustomerLoginEntity > customerLogin ) {
        this.customerLogin = customerLogin;
    }


    public MobilePlatformEntity() {
    }
}
