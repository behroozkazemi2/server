package com.behrouz.server.niazPardazandeh;

public class SmsResponse {
    private int BatchSmsId;
    private int Result;
    private String ErrorMessage;

    // getters and setters
    public int getBatchSmsId() {
        return BatchSmsId;
    }

    public void setBatchSmsId(int batchSmsId) {
        BatchSmsId = batchSmsId;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "SmsResponse{" +
                "BatchSmsId=" + BatchSmsId +
                ", Result=" + Result +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                '}';
    }
}