package com.behrouz.server.rest.response.bank;

public class SamanVerifyInfoRestResponse {

    private String RRN;
    private String RefNum;
    private String MaskedPan;
    private String HashedPan;
    private String StraceDate;
    private String StraceNo;
    private long TerminalNumber;
    private long OrginalAmount;
    private long AffectiveAmount;

    public SamanVerifyInfoRestResponse() {
    }

    public SamanVerifyInfoRestResponse(String RRN, String refNum, String maskedPan, String hashedPan, String straceDate, String straceNo, long terminalNumber, long orginalAmount, long affectiveAmount) {
        this.RRN = RRN;
        this.RefNum = refNum;
        this.MaskedPan = maskedPan;
        this.HashedPan = hashedPan;
        this.StraceDate = straceDate;
        this.StraceNo = straceNo;
        this.TerminalNumber = terminalNumber;
        this.OrginalAmount = orginalAmount;
        this.AffectiveAmount = affectiveAmount;
    }


    public String getRRN() {
        return RRN;
    }
    public void setRRN(String RRN) {
        this.RRN = RRN;
    }


    public String getRefNum() {
        return RefNum;
    }
    public void setRefNum(String refNum) {
        RefNum = refNum;
    }


    public String getMaskedPan() {
        return MaskedPan;
    }
    public void setMaskedPan(String maskedPan) {
        MaskedPan = maskedPan;
    }


    public String getHashedPan() {
        return HashedPan;
    }
    public void setHashedPan(String hashedPan) {
        HashedPan = hashedPan;
    }


    public String getStraceDate() {
        return StraceDate;
    }
    public void setStraceDate(String straceDate) {
        StraceDate = straceDate;
    }


    public String getStraceNo() {
        return StraceNo;
    }
    public void setStraceNo(String straceNo) {
        StraceNo = straceNo;
    }


    public long getTerminalNumber() {
        return TerminalNumber;
    }
    public void setTerminalNumber(long terminalNumber) {
        TerminalNumber = terminalNumber;
    }


    public long getOrginalAmount() {
        return OrginalAmount;
    }
    public void setOrginalAmount(long orginalAmount) {
        OrginalAmount = orginalAmount;
    }


    public long getAffectiveAmount() {
        return AffectiveAmount;
    }
    public void setAffectiveAmount(long affectiveAmount) {
        AffectiveAmount = affectiveAmount;
    }

    @Override
    public String toString() {
        return "SamanVerifyInfoRestResponse{" +
                "RRN='" + RRN + '\'' +
                ", RefNum='" + RefNum + '\'' +
                ", MaskedPan='" + MaskedPan + '\'' +
                ", HashedPan='" + HashedPan + '\'' +
                ", StraceDate='" + StraceDate + '\'' +
                ", StraceNo='" + StraceNo + '\'' +
                ", TerminalNumber=" + TerminalNumber +
                ", OrginalAmount=" + OrginalAmount +
                ", AffectiveAmount=" + AffectiveAmount +
                '}';
    }
}
