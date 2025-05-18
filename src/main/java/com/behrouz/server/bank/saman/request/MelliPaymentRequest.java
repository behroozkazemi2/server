package com.behrouz.server.bank.saman.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

/**
 * Created by thunderbolt on 9/16/17.
 */

public class MelliPaymentRequest{

    @JsonProperty("MerchantId")
    private String merchantId;


    @JsonProperty("TerminalId")
    private String terminalId;

    @JsonProperty("Amount")
    private long amount;

    @JsonProperty("OrderId")
    private long orderId;

    @JsonProperty("LocalDateTime")
    private Date localDateTime;


    @JsonProperty("ReturnUrl")
    private String returnUrl;


    @JsonProperty("SignData")
    private String signData;

    @JsonProperty("UserId")
    private long userId;


    @JsonProperty("ApplicationName")
    private String applicationName;



    public MelliPaymentRequest(String merchantId, String terminalId, long amount, long orderId, Date localDateTime, String returnUrl, long userId, String signData) {
        this.merchantId = merchantId;
        this.terminalId = terminalId;
        this.amount = amount;
        this.orderId = orderId;
        this.localDateTime = localDateTime;
        this.returnUrl = returnUrl;
        this.userId = userId;
        this.signData = signData;
        this.applicationName = "مهان";
    }


    public String getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }



    public String getTerminalId() {
        return terminalId;
    }
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }



    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }




    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }



    public Date getLocalDateTime() {
        return localDateTime;
    }
    public void setLocalDateTime(Date localDateTime) {
        this.localDateTime = localDateTime;
    }




    public String getReturnUrl() {
        return returnUrl;
    }
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }



    public String getSignData() {
        return signData;
    }
    public void setSignData(String signData) {
        this.signData = signData;
    }



    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }



    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    @Override
    public String toString() {

        String result;
        try {
            result = (new ObjectMapper()).writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result = "Error in Casting";
        }

        return result;
    }
}
