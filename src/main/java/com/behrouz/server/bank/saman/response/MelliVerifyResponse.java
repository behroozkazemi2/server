package com.behrouz.server.bank.saman.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by thunderbolt on 9/16/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MelliVerifyResponse implements Serializable{

    @JsonProperty("ResCode")
    private long resCode;

    @JsonProperty("Amount")
    private long amount;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("RetrivalRefNo")
    private String retrivalRefNo;

    @JsonProperty("SystemTraceNo")
    private String systemTraceNo;

    @JsonProperty("OrderId")
    private long orderId;

    @JsonProperty("SwitchResCode")
    private long switchResCode;

    @JsonProperty("TransactionDate")
    private String transactionDate;




    public long getResCode() {
        return resCode;
    }
    public void setResCode(long resCode) {
        this.resCode = resCode;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }



    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }



    public String getRetrivalRefNo() {
        return retrivalRefNo;
    }
    public void setRetrivalRefNo(String retrivalRefNo) {
        this.retrivalRefNo = retrivalRefNo;
    }



    public String getSystemTraceNo() {
        return systemTraceNo;
    }
    public void setSystemTraceNo(String systemTraceNo) {
        this.systemTraceNo = systemTraceNo;
    }



    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }


    public long getSwitchResCode() {
        return switchResCode;
    }
    public void setSwitchResCode(long switchResCode) {
        this.switchResCode = switchResCode;
    }



    public String getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
