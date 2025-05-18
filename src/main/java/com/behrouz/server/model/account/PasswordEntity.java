package com.behrouz.server.model.account;

import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 14:56
 **/

@Entity
@Table(name = "password", schema = "public")
public class PasswordEntity extends BaseEntity {

    private String hashPassword;

    private OperatorEntity operator;

    public PasswordEntity( String hashPassword, OperatorEntity operator ) {
        this.hashPassword = hashPassword;
        this.operator = operator;
    }



    @Basic
    public String getHashPassword() {
        return hashPassword;
    }
    public void setHashPassword( String hashPassword ) {
        this.hashPassword = hashPassword;
    }



    @ManyToOne
    @JoinColumn(name = "operator_id",  nullable = false)
    public OperatorEntity getOperator() {
        return operator;
    }
    public void setOperator( OperatorEntity operator ) {
        this.operator = operator;
    }

    public PasswordEntity() {
    }
}
