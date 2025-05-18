package com.behrouz.server.model.bank;


import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.rest.request.bank.SamanTokenRestRequest;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "saman_token_request", schema = "public")
public class SamanTokenRequestEntity extends BaseEntity {

    private String action;
    private String terminalId;
    private long amount;
    private String resNum; // bill trackingCode (must be uniq)
    private String redirectUrl;
    private String cellNumber;

    public SamanTokenRequestEntity() {
    }

    public SamanTokenRequestEntity(String action, String terminalId, long amount, String resNum, String redirectUrl, String cellNumber) {
        this.action = action;
        this.terminalId = terminalId;
        this.amount = amount;
        this.resNum = resNum;
        this.redirectUrl = redirectUrl;
        this.cellNumber = cellNumber;
    }

    public SamanTokenRequestEntity(SamanTokenRestRequest parameters) {
        this(
                parameters.getAction(),
                parameters.getTerminalId(),
                parameters.getAmount(),
                parameters.getResNum(),
                parameters.getRedirectUrl(),
                parameters.getCellNumber()
        );
    }


    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }


    public String getTerminalId() {
        return terminalId;
    }
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }


    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }


    public String getResNum() {
        return resNum;
    }
    public void setResNum(String resNum) {
        this.resNum = resNum;
    }


    public String getRedirectUrl() {
        return redirectUrl;
    }
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


    public String getCellNumber() {
        return cellNumber;
    }
    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }
}
