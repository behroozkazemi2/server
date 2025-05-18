package com.behrouz.server.model.bank.melli;


import com.behrouz.server.bank.saman.request.MelliPaymentRequest;
import com.behrouz.server.model.bank.BankTransactionEntity;
import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

/**
 * Created by thunderbolt on 9/16/17.
 */

@Entity
@Table(name = "bank_melli_payment_request" , schema = "public")
public class MelliPaymentRequestEntity extends BaseEntity {

    private String merchantId;

    private String terminalId;

    private long amount;

    private long orderId;

    private LocalDateTime localDateTime;

    private String returnUrl;

    private String signData;

    private long userId;

    private String applicationName;

    private BankTransactionEntity bankTransaction;

    @OneToMany(mappedBy = "paymentRequest")
    private Set<MelliPaymentResponseEntity> paymentResponse;


    public MelliPaymentRequestEntity() {
    }


    public MelliPaymentRequestEntity(String merchantId, String terminalId, long amount, long orderId, LocalDateTime localDateTime, String returnUrl, String signData, long userId, String applicationName, BankTransactionEntity bankTransaction) {
        this.merchantId = merchantId;
        this.terminalId = terminalId;
        this.amount = amount;
        this.orderId = orderId;
        this.localDateTime = localDateTime;
        this.returnUrl = returnUrl;
        this.signData = signData;
        this.userId = userId;
        this.applicationName = applicationName;
        this.bankTransaction = bankTransaction;
    }


    public MelliPaymentRequestEntity(MelliPaymentRequest paymentRequest, BankTransactionEntity bankTransaction){
        this(
                paymentRequest.getMerchantId(),
                paymentRequest.getTerminalId(),
                paymentRequest.getAmount(),
                paymentRequest.getOrderId(),
                paymentRequest.getLocalDateTime() == null ? null : paymentRequest.getLocalDateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                paymentRequest.getReturnUrl(),
                paymentRequest.getSignData(),
                paymentRequest.getUserId(),
                paymentRequest.getApplicationName(),
                bankTransaction
        );
    }



    @Basic
    public String getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    @Basic
    public String getTerminalId() {
        return terminalId;
    }
    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }


    @Basic
    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }



    @Basic
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }


    @Basic
    public String getReturnUrl() {
        return returnUrl;
    }
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }


    @Basic
    public String getSignData() {
        return signData;
    }
    public void setSignData(String signData) {
        this.signData = signData;
    }

    @Basic
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }


    @Basic
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }


    @Basic
    public String getApplicationName() {
        return applicationName;
    }
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }



    @ManyToOne
    @JoinColumn(name = "bank_transaction_id", nullable = false)
    public BankTransactionEntity getBankTransaction() {
        return bankTransaction;
    }
    public void setBankTransaction(BankTransactionEntity bankTransaction) {
        this.bankTransaction = bankTransaction;
    }


}
