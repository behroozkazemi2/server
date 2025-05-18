package com.behrouz.server.model.account;

import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.security.model.OperatorSessionDetail;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project server
 * 24 September 2018 12:42
 **/
@Entity
@Table(name = "operator_login_history", schema = "public")
public class OperatorLoginHistoryEntity extends BaseEntity {

    private String token;
    private Date date;

    private OperatorEntity operator;

    public OperatorLoginHistoryEntity( PasswordEntity passwordEntity, OperatorSessionDetail sessionDetail ) {
        this.token = sessionDetail.getUser().getToken();
        this.operator = passwordEntity.getOperator();
    }

    @Basic
    public String getToken() {
        return token;
    }
    public void setToken( String token ) {
        this.token = token;
    }


    @Basic
    @CreationTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    public Date getDate() {
        return date;
    }
    public void setDate( Date date ) {
        this.date = date;
    }


    @ManyToOne
    @JoinColumn(name = "operator_id")
    public OperatorEntity getOperator() {
        return operator;
    }
    public void setOperator( OperatorEntity operator ) {
        this.operator = operator;
    }

    public OperatorLoginHistoryEntity() {
    }
}
