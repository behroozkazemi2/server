package com.behrouz.server.model.bank;



import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.rest.response.bank.SamanVerifyInfoRestResponse;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "saman_verify_transaction_info_response" , schema = "public")
public class SamanVerifyTransactionInfoResponseEntity extends BaseEntity {

    private String rrn;
    private String refNum;
    private String maskedPan;
    private String hashedPan;

    private long terminalNumber;
    private long orginalAmount;
    private long affectiveAmount;
    private String straceDate;
    private String straceNo;

    @OneToMany(mappedBy = "verifyInfo")
    @JsonManagedReference
    private Set<SamanVerifyTransactionResponseEntity> samanVerifyResponseEntities;



    public SamanVerifyTransactionInfoResponseEntity() {
    }

    public SamanVerifyTransactionInfoResponseEntity(String rrn, String refNum, String maskedPan, String hashedPan, long terminalNumber, long orginalAmount, long affectiveAmount, String straceDate, String straceNo) {
        this.rrn = rrn;
        this.refNum = refNum;
        this.maskedPan = maskedPan;
        this.hashedPan = hashedPan;
        this.terminalNumber = terminalNumber;
        this.orginalAmount = orginalAmount;
        this.affectiveAmount = affectiveAmount;
        this.straceDate = straceDate;
        this.straceNo = straceNo;
    }

    public SamanVerifyTransactionInfoResponseEntity(SamanVerifyInfoRestResponse vi) {
        this(
                vi.getRRN(),
                vi.getRefNum(),
                vi.getMaskedPan(),
                vi.getHashedPan(),
                vi.getTerminalNumber(),
                vi.getOrginalAmount(),
                vi.getAffectiveAmount(),
                vi.getStraceDate(),
                vi.getStraceNo()
        );
    }


    public String getRrn() {
        return rrn;
    }
    public void setRrn(String rrn) {
        this.rrn = rrn;
    }


    public String getRefNum() {
        return refNum;
    }
    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }


    public String getMaskedPan() {
        return maskedPan;
    }
    public void setMaskedPan(String maskedPan) {
        this.maskedPan = maskedPan;
    }


    public String getHashedPan() {
        return hashedPan;
    }
    public void setHashedPan(String hashedPan) {
        this.hashedPan = hashedPan;
    }


    public long getTerminalNumber() {
        return terminalNumber;
    }
    public void setTerminalNumber(long terminalNumber) {
        this.terminalNumber = terminalNumber;
    }


    public long getOrginalAmount() {
        return orginalAmount;
    }
    public void setOrginalAmount(long orginalAmount) {
        this.orginalAmount = orginalAmount;
    }


    public long getAffectiveAmount() {
        return affectiveAmount;
    }
    public void setAffectiveAmount(long affectiveAmount) {
        this.affectiveAmount = affectiveAmount;
    }


    public String getStraceDate() {
        return straceDate;
    }
    public void setStraceDate(String straceDate) {
        this.straceDate = straceDate;
    }


    public String getStraceNo() {
        return straceNo;
    }
    public void setStraceNo(String straceNo) {
        this.straceNo = straceNo;
    }
}
