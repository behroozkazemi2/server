package com.behrouz.server.model.balance;

import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.model
 * Project Name: behta-server
 * 09 December 2018
 **/

@Entity
@Table(name = "balance_history", schema = "public")
public class BalanceHistoryEntity extends BaseEntity {

    private AccountEntity account;

    private long amount;

    private int refType;

    private long refId;

    private String trackingCode;

    private String description;


    public BalanceHistoryEntity() {
    }

    public BalanceHistoryEntity(AccountEntity account, long amount, int refType, long refId, String trackingCode, String description) {
        this.account = account;
        this.amount = amount;
        this.refType = refType;
        this.refId = refId;
        this.trackingCode = trackingCode;
        this.description = description;
    }



    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    public AccountEntity getAccount() {
        return account;
    }
    public void setAccount(AccountEntity account) {
        this.account = account;
    }



    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }



    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public int getRefType() {
        return refType;
    }
    public void setRefType(int refType) {
        this.refType = refType;
    }



    public long getRefId() {
        return refId;
    }
    public void setRefId(long refId) {
        this.refId = refId;
    }



    public String getTrackingCode() {
        return trackingCode;
    }
    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }
}
