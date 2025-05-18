package com.behrouz.server.rest.response.bank;

public class SamanRedirectUrlRestResponse {

    private long MID;
    private long TerminalId;
    private String State;
    private long Status;
    private String RRN;
    private String RefNum;
    private String ResNum;
    private String TraceNo;
    private long Amount;
    private Long Wage;
    private String SecurePan;
    private String HashedCardNumber;

    public SamanRedirectUrlRestResponse() {
    }


    public long getMID() {
        return MID;
    }
    public void setMID(long MID) {
        this.MID = MID;
    }


    public long getTerminalId() {
        return TerminalId;
    }
    public void setTerminalId(long terminalId) {
        TerminalId = terminalId;
    }


    public String getState() {
        return State;
    }
    public void setState(String state) {
        State = state;
    }


    public long getStatus() {
        return Status;
    }
    public void setStatus(long status) {
        Status = status;
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


    public String getResNum() {
        return ResNum;
    }
    public void setResNum(String resNum) {
        ResNum = resNum;
    }


    public String getTraceNo() {
        return TraceNo;
    }
    public void setTraceNo(String traceNo) {
        TraceNo = traceNo;
    }


    public long getAmount() {
        return Amount;
    }
    public void setAmount(long amount) {
        Amount = amount;
    }


    public Long getWage() {
        return Wage;
    }
    public void setWage(Long wage) {
        Wage = wage;
    }


    public String getSecurePan() {
        return SecurePan;
    }
    public void setSecurePan(String securePan) {
        SecurePan = securePan;
    }


    public String getHashedCardNumber() {
        return HashedCardNumber;
    }
    public void setHashedCardNumber(String hashedCardNumber) {
        HashedCardNumber = hashedCardNumber;
    }


    @Override
    public String toString() {
        return "SamanRedirectUrlRestResponse{" +
                "MID=" + MID +
                ", TerminalId=" + TerminalId +
                ", State='" + State + '\'' +
                ", Status=" + Status +
                ", RRN='" + RRN + '\'' +
                ", RefNum='" + RefNum + '\'' +
                ", ResNum='" + ResNum + '\'' +
                ", TraceNo='" + TraceNo + '\'' +
                ", Amount=" + Amount +
                ", Wage=" + Wage +
                ", SecurePan='" + SecurePan + '\'' +
                ", HashedCardNumber='" + HashedCardNumber + '\'' +
                '}';
    }
}
