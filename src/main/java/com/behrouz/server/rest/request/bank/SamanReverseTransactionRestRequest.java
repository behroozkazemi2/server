package com.behrouz.server.rest.request.bank;

public class SamanReverseTransactionRestRequest {

    private String RefNum;
    private long TerminalNumber;

    public SamanReverseTransactionRestRequest() {
    }

    public SamanReverseTransactionRestRequest(String refNum, long terminalNumber) {
        RefNum = refNum;
        TerminalNumber = terminalNumber;
    }

    public SamanReverseTransactionRestRequest(SamanVerifyTransactionRestRequest parameters) {
        this(parameters.getRefNum(), parameters.getTerminalNumber());
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
}
