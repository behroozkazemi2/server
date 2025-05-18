package com.behrouz.server.rest.response.bank;

public class SamanVerifyTransactionRestResponse {

    private long ResultCode;
    private String ResultDescription;
    private boolean Success;
    private SamanVerifyInfoRestResponse TransactionDetail;

    public SamanVerifyTransactionRestResponse() {
    }

    public SamanVerifyTransactionRestResponse(long resultCode, String resultDescription, boolean success, SamanVerifyInfoRestResponse verifyInfo) {
        this.ResultCode = resultCode;
        this.ResultDescription = resultDescription;
        this.Success = success;
        this.TransactionDetail = verifyInfo;
    }


    public long getResultCode() {
        return ResultCode;
    }
    public void setResultCode(long resultCode) {
        ResultCode = resultCode;
    }


    public String getResultDescription() {
        return ResultDescription;
    }
    public void setResultDescription(String resultDescription) {
        ResultDescription = resultDescription;
    }


    public boolean isSuccess() {
        return Success;
    }
    public boolean getSuccess() {
        return Success;
    }
    public void setSuccess(boolean success) {
        Success = success;
    }


    public SamanVerifyInfoRestResponse getTransactionDetail() {
        return TransactionDetail;
    }
    public void setTransactionDetail(SamanVerifyInfoRestResponse verifyInfo) {
        TransactionDetail = TransactionDetail;
    }

    @Override
    public String toString() {
        return "SamanVerifyTransactionRestResponse{" +
                "ResultCode=" + ResultCode +
                ", ResultDescription='" + ResultDescription + '\'' +
                ", Success=" + Success +
                ", TransactionDetail=" + TransactionDetail +
                '}';
    }
}
