package com.behrouz.server.model.bank;


import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.rest.request.bank.SamanReverseTransactionRestRequest;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "saman_reverse_transaction_request", schema = "public")
public class SamanReverseTransactionRequestEntity extends BaseEntity {

    private String refNum;
    private long terminalNumber;

    public SamanReverseTransactionRequestEntity() {
    }

    public SamanReverseTransactionRequestEntity(String refNum, long terminalNumber) {
        this.refNum = refNum;
        this.terminalNumber = terminalNumber;
    }

    public SamanReverseTransactionRequestEntity(SamanReverseTransactionRestRequest request) {
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
