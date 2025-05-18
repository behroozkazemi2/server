package com.behrouz.server.model.bank;


import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.rest.response.bank.SamanRedirectUrlRestResponse;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "saman_redirect_url_response", schema = "public")
public class SamanRedirectUrlResponseEntity extends BaseEntity {

    private long mID;
    private long terminalId;
    private String state;
    private long status;
    private String rRN;
    private String refNum;
    private String resNum;
    private String traceNo;
    private long amount;
    private long wage;
    private String securePan;
    private String hashedCardNumber;

    public SamanRedirectUrlResponseEntity() {
    }

    public SamanRedirectUrlResponseEntity(long mID, long terminalId, String state, long status, String rRN, String refNum, String resNum, String traceNo, long amount, long wage, String securePan, String hashedCardNumber) {
        this.mID = mID;
        this.terminalId = terminalId;
        this.state = state;
        this.status = status;
        this.rRN = rRN;
        this.refNum = refNum;
        this.resNum = resNum;
        this.traceNo = traceNo;
        this.amount = amount;
        this.wage = wage;
        this.securePan = securePan;
        this.hashedCardNumber = hashedCardNumber;
    }

    public SamanRedirectUrlResponseEntity(SamanRedirectUrlRestResponse response) {
        this(
                response.getMID(),
                response.getTerminalId(),
                response.getState(),
                response.getStatus(),
                response.getRRN(),
                response.getRefNum(),
                response.getResNum(),
                response.getTraceNo(),
                response.getAmount(),
                response.getWage() == null ? 0 : response.getWage(),
                response.getSecurePan(),
                response.getHashedCardNumber()
        );
    }


    public long getmID() {
        return mID;
    }
    public void setmID(long mID) {
        this.mID = mID;
    }


    public long getTerminalId() {
        return terminalId;
    }
    public void setTerminalId(long terminalId) {
        this.terminalId = terminalId;
    }


    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }


    public long getStatus() {
        return status;
    }
    public void setStatus(long status) {
        this.status = status;
    }


    public String getrRN() {
        return rRN;
    }
    public void setrRN(String rRN) {
        this.rRN = rRN;
    }


    public String getRefNum() {
        return refNum;
    }
    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }


    public String getResNum() {
        return resNum;
    }
    public void setResNum(String resNum) {
        this.resNum = resNum;
    }


    public String getTraceNo() {
        return traceNo;
    }
    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }


    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }


    public long getWage() {
        return wage;
    }
    public void setWage(long wage) {
        this.wage = wage;
    }


    public String getSecurePan() {
        return securePan;
    }
    public void setSecurePan(String securePan) {
        this.securePan = securePan;
    }


    public String getHashedCardNumber() {
        return hashedCardNumber;
    }
    public void setHashedCardNumber(String hashedCardNumber) {
        this.hashedCardNumber = hashedCardNumber;
    }
}
