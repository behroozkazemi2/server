package com.behrouz.server.model.bank;


import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.rest.response.bank.SamanVerifyTransactionRestResponse;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "saman_verify_transaction_response", schema = "public")
public class SamanVerifyTransactionResponseEntity extends BaseEntity {

    private long resultCode;
    private String resultDescription;
    private boolean success;
    private SamanVerifyTransactionInfoResponseEntity verifyInfo;

    private BankTransactionEntity bankTransaction;

    public SamanVerifyTransactionResponseEntity() {}

    public SamanVerifyTransactionResponseEntity(long resultCode, String resultDescription, boolean success, SamanVerifyTransactionInfoResponseEntity verifyInfo, BankTransactionEntity bankTransaction) {
        this.resultCode = resultCode;
        this.resultDescription = resultDescription;
        this.success = success;
        this.verifyInfo = verifyInfo;
    }

    public SamanVerifyTransactionResponseEntity(SamanVerifyTransactionRestResponse verifyResponse, SamanVerifyTransactionInfoResponseEntity samanVerifyInfoResponse, BankTransactionEntity bankTransaction) {
        this(
                verifyResponse.getResultCode(),
                verifyResponse.getResultDescription(),
                verifyResponse.getSuccess(),
                samanVerifyInfoResponse,
                bankTransaction
        );
    }

    public long getResultCode() {
        return resultCode;
    }
    public void setResultCode(long resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDescription() {
        return resultDescription;
    }
    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }


    @ManyToOne
    @JoinColumn(name = "verify_info_id", nullable = false)
    public SamanVerifyTransactionInfoResponseEntity getVerifyInfo() {
        return verifyInfo;
    }
    public void setVerifyInfo(SamanVerifyTransactionInfoResponseEntity verifyInfo) {
        this.verifyInfo = verifyInfo;
    }



    @ManyToOne
    @JoinColumn(name = "bank_transaction_id", nullable = false)
    public BankTransactionEntity getBankTransaction() {
        return bankTransaction;
    }
    public void setBankTransaction(BankTransactionEntity bankTransaction) {
        this.bankTransaction = bankTransaction;
    }

}
