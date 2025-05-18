package com.behrouz.server.model.bank;


import com.behrouz.server.model.bank.melli.*;
import com.behrouz.server.model.bank.melli.*;
import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name= "bank_transaction", schema = "public")
public class BankTransactionEntity extends BaseEntity {


    private String description;

    private long accountId;

    private long mobile;

    private long refId;

    private long amount;

    private String token;

    private String redirectUrl;

    private String userPayUrl;

    private String gateway;

    private String bankToken;

    @OneToMany(mappedBy = "transaction")
    private Set<MelliCallBackResponseEntity> callback;

    @OneToMany(mappedBy = "transaction")
    private Set<MelliPaymentRequestEntity> payRequest;

    @OneToMany(mappedBy = "transaction")
    private Set<MelliPaymentResponseEntity> payResponse;

    @OneToMany(mappedBy = "transaction")
    private Set<MelliVerifyRequestEntity> verifyRequest;

    @OneToMany(mappedBy = "transaction")
    private Set<MelliVerifyResponseEntity> verifyResponse;

    @OneToMany(mappedBy = "bankTransaction")
    private Set<SamanVerifyTransactionResponseEntity> samanVerifyTransactionResponseEntity;


    public BankTransactionEntity() {
    }



    public BankTransactionEntity(long accountId, long mobile, long refId, long amount, String token, String redirectUrl, String userPayUrl, String bankToken) {
        this.accountId = accountId;
        this.mobile = mobile;
        this.refId = refId;
        this.amount = amount;
        this.token = token;
        this.redirectUrl = redirectUrl;
        this.userPayUrl = userPayUrl;
        this.bankToken = bankToken;
    }



    @Basic
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    @Basic
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Basic
    public long getMobile() {
        return mobile;
    }
    public void setMobile(long mobile) {
        this.mobile = mobile;
    }


    @Basic
    public long getRefId() {
        return refId;
    }
    public void setRefId(long refId) {
        this.refId = refId;
    }


    @Basic
    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }


    @Basic
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }


    @Basic
    public String getRedirectUrl() {
        return redirectUrl;
    }
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


    @Basic
    public String getUserPayUrl() {
        return userPayUrl;
    }
    public void setUserPayUrl(String userPayUrl) {
        this.userPayUrl = userPayUrl;
    }


    @Basic
    public String getGateway() {
        return gateway;
    }
    public void setGateway(String gateway) {
        this.gateway = gateway;
    }


    @Basic
    public String getBankToken() {
        return bankToken;
    }
    public void setBankToken(String bankToken) {
        this.bankToken = bankToken;
    }
}
