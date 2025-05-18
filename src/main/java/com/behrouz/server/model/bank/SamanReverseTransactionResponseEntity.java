package com.behrouz.server.model.bank;


import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.rest.response.bank.SamanReverseTransactionRestResponse;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "saman_reverse_transaction_response", schema = "public")
public class SamanReverseTransactionResponseEntity extends BaseEntity {

    private long resultCode;
    private String resultDescription;
    private boolean success;
    private SamanReverseTransactionInfoResponseEntity verifyInfo;

    public SamanReverseTransactionResponseEntity() {}

    public SamanReverseTransactionResponseEntity(long resultCode, String resultDescription, boolean success, SamanReverseTransactionInfoResponseEntity verifyInfo) {
        this.resultCode = resultCode;
        this.resultDescription = resultDescription;
        this.success = success;
        this.verifyInfo = verifyInfo;
    }

    public SamanReverseTransactionResponseEntity(SamanReverseTransactionRestResponse verifyResponse, SamanReverseTransactionInfoResponseEntity samanVerifyInfoResponse) {
        this(
                verifyResponse.getResultCode(),
                verifyResponse.getResultDescription(),
                verifyResponse.getSuccess(),
                samanVerifyInfoResponse
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
    public SamanReverseTransactionInfoResponseEntity getVerifyInfo() {
        return verifyInfo;
    }
    public void setVerifyInfo(SamanReverseTransactionInfoResponseEntity verifyInfo) {
        this.verifyInfo = verifyInfo;
    }
}
