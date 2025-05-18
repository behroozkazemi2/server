package com.behrouz.server.rest.request.bank;

public class SamanVerifyTransactionRestRequest {

    private String RefNum;
    private long TerminalNumber;

    public SamanVerifyTransactionRestRequest() {
    }

    public SamanVerifyTransactionRestRequest(String refNum, long terminalNumber) {
        RefNum = refNum;
        TerminalNumber = terminalNumber;
    }

    public String getRefNum() {
        return RefNum;
    }
    public void setRefNum(String refNum) {
        RefNum = refNum;
    }

    public long getTerminalNumber() {
        return TerminalNumber;
    }
    public void setTerminalNumber(long terminalNumber) {
        TerminalNumber = terminalNumber;
    }

    @Override
    public String toString() {
        return "SamanVerifyTransactionRestRequest{" +
                "RefNum='" + RefNum + '\'' +
                ", TerminalNumber=" + TerminalNumber +
                '}';
    }
}
