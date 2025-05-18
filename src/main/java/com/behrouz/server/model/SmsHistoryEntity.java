package com.behrouz.server.model;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.base.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 15:42
 **/

@Entity
@Table(name = "sms_history", schema = "public")
public class SmsHistoryEntity extends BaseEntity {

    private String content;
    private Date sentDate;
    private String mobile;
    private String description;
    private long refAccountingId;


    public SmsHistoryEntity() {
    }

    public SmsHistoryEntity( String smsCode, String mobile, String description, CustomerEntity sentTo ) {
        this.content = smsCode;
        this.mobile = mobile;
        this.description = description;
        this.refAccountingId = sentTo != null ? sentTo.getId() : 0;
    }


    @Basic
    public String getContent() {
        return content;
    }
    public void setContent( String content ) {
        this.content = content;
    }


    @Basic
    @CreationTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    public Date getSentDate() {
        return sentDate;
    }
    public void setSentDate( Date sentDate ) {
        this.sentDate = sentDate;
    }


    @Basic
    public String getMobile() {
        return mobile;
    }
    public void setMobile( String mobile ) {
        this.mobile = mobile;
    }


    @Basic
    public String getDescription() {
        return description;
    }
    public void setDescription( String description ) {
        this.description = description;
    }


    @Basic
    public long getRefAccountingId() {
        return refAccountingId;
    }
    public void setRefAccountingId( long refAccountingId ) {
        this.refAccountingId = refAccountingId;
    }

}
