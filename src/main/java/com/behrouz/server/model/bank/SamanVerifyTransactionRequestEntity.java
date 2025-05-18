package com.behrouz.server.model.bank;


import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.rest.request.bank.SamanVerifyTransactionRestRequest;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "saman_verify_transaction_request", schema = "public")
public class SamanVerifyTransactionRequestEntity extends BaseEntity {

    private String refNum;
    private long terminalNumber;

    public SamanVerifyTransactionRequestEntity() {
    }

    public SamanVerifyTransactionRequestEntity(String refNum, long terminalNumber) {
        this.refNum = refNum;
        this.terminalNumber = terminalNumber;
    }

    public SamanVerifyTransactionRequestEntity(SamanVerifyTransactionRestRequest request) {
        this(request.getRefNum(), request.getTerminalNumber());
    }

    public String getRefNum() {
        return refNum;
    }
    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }


    public long getTerminalNumber() {
        return terminalNumber;
    }
    public void setTerminalNumber(long terminalNumber) {
        this.terminalNumber = terminalNumber;
    }
}
