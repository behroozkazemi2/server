package com.behrouz.server.model.bank.melli;


import com.behrouz.server.bank.saman.response.MelliPaymentResponse;
import com.behrouz.server.model.bank.BankTransactionEntity;
import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;
import javax.persistence.Basic;
import javax.persistence.Column;

/**
 * Created by thunderbolt on 9/16/17.

 */
@Entity
@Table(name = "bank_melli_payment_response" , schema = "public")
public class MelliPaymentResponseEntity extends BaseEntity {

    private long resCode;

    private String token;

    private String description;

    private BankTransactionEntity bankTransaction;

    private MelliPaymentRequestEntity paymentRequest;


    public MelliPaymentResponseEntity() {
    }

    public MelliPaymentResponseEntity(long resCode, String token, String description, BankTransactionEntity bankTransaction, MelliPaymentRequestEntity paymentRequest) {
        this.resCode = resCode;
        this.token = token;
        this.description = description;
        this.bankTransaction = bankTransaction;
        this.paymentRequest = paymentRequest;
    }


    public MelliPaymentResponseEntity(MelliPaymentResponse r, BankTransactionEntity bankTransaction, MelliPaymentRequestEntity paymentRequest) {
        this(
                r == null ? -2 : r.getResCode(),
                r == null ? null : r.getToken(),
                r == null ? null : r.getDescription(),
                bankTransaction,
                paymentRequest
        );
    }


    @Basic
    public long getResCode() {
        return resCode;
    }
    public void setResCode(long resCode) {
        this.resCode = resCode;
    }


    @Basic
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }


    @Basic
    @Column(length = 1500)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    @ManyToOne
    @JoinColumn(name = "bank_transaction_id", nullable = false)
    public BankTransactionEntity getBankTransaction() {
        return bankTransaction;
    }
    public void setBankTransaction(BankTransactionEntity bankTransaction) {
        this.bankTransaction = bankTransaction;
    }


    @ManyToOne
    @JoinColumn(name = "payment_request_id", nullable = false)
    public MelliPaymentRequestEntity getPaymentRequest() {
        return paymentRequest;
    }
    public void setPaymentRequest(MelliPaymentRequestEntity paymentRequest) {
        this.paymentRequest = paymentRequest;
    }
}