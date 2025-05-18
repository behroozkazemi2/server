package com.behrouz.server.rest.request.bank;

public class SamanTokenRestRequest {

    private String action;
    private String TerminalId;
    private long Amount;
    private String ResNum; // must be uniq
    private String RedirectUrl;
    private String CellNumber;

    public SamanTokenRestRequest() {
    }

    public SamanTokenRestRequest(String action, String terminalId, long amount, String resNum, String redirectUrl, String cellNumber) {
        this.action = action;
        this.TerminalId = terminalId;
        this.Amount = amount;
        this.ResNum = resNum;
        this.RedirectUrl = redirectUrl;
        this.CellNumber = cellNumber;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }


    public String getTerminalId() {
        return TerminalId;
    }
    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }


    public long getAmount() {
        return Amount;
    }
    public void setAmount(long amount) {
        Amount = amount;
    }


    public String getResNum() {
        return ResNum;
    }
    public void setResNum(String resNum) {
        ResNum = resNum;
    }


    public String getRedirectUrl() {
        return RedirectUrl;
    }
    public void setRedirectUrl(String redirectUrl) {
        RedirectUrl = redirectUrl;
    }


    public String getCellNumber() {
        return CellNumber;
    }
    public void setCellNumber(String cellNumber) {
        CellNumber = cellNumber;
    }
}
