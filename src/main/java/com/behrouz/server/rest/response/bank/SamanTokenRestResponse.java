package com.behrouz.server.rest.response.bank;

public class SamanTokenRestResponse {

    private int status;
    private long billId;
    private String token;
    private String errorCode;
    private String errorDesc;

    public SamanTokenRestResponse() {
    }

    public SamanTokenRestResponse(int status, String token, String errorCode, String errorDesc, long billId) {
        this.status = status;
        this.token = token;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.billId = billId;
    }


    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }


    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }


    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


    public String getErrorDesc() {
        return errorDesc;
    }
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public long getBillId() {
        return billId;
    }
    public void setBillId(long billId) {
        this.billId = billId;
    }

    @Override
    public String toString() {
        return "SamanTokenRestResponse{" +
                "status=" + status +
                ", billId=" + billId +
                ", token='" + token + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorDesc='" + errorDesc + '\'' +
                '}';
    }
}
