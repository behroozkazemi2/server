package com.behrouz.server.rest.response.bank;

public class SamanReverseTransactionRestResponse {

    private long ResultCode;
    private String ResultDescription;
    private boolean Success;
    private SamanVerifyInfoRestResponse VerifyInfo;

    public SamanReverseTransactionRestResponse() {
    }

    public SamanReverseTransactionRestResponse(long resultCode, String resultDescription, boolean success, SamanVerifyInfoRestResponse verifyInfo) {
        this.ResultCode = resultCode;
        this.ResultDescription = resultDescription;
        this.Success = success;
        this.VerifyInfo = verifyInfo;
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


    public boolean getSuccess() {
        return Success;
    }
    public void setSuccess(boolean success) {
        Success = success;
    }


    public SamanVerifyInfoRestResponse getVerifyInfo() {
        return VerifyInfo;
    }
    public void setVerifyInfo(SamanVerifyInfoRestResponse verifyInfo) {
        VerifyInfo = verifyInfo;
    }
}
