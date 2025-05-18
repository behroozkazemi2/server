package com.behrouz.server.model.bank.melli;

import com.behrouz.server.bank.saman.response.MelliVerifyResponse;
import com.behrouz.server.model.bank.BankTransactionEntity;
import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;

/**
 * Created by thunderbolt on 9/16/17.
 */
@Entity
@Table(name = "bank_melli_verify_response" , schema = "public")
public class MelliVerifyResponseEntity extends BaseEntity {

    private long resCode;

    private long amount;

    private String description;

    private String retrivalRefNo;

    private String systemTraceNo;

    private long orderId;

    private long switchResCode;

    private String transactionDate;

    private BankTransactionEntity bankTransaction;


    private MelliVerifyRequestEntity verifyRequest;

    public MelliVerifyResponseEntity() {
    }


    public MelliVerifyResponseEntity(long resCode, long amount, String description, String retrivalRefNo, String systemTraceNo, long orderId, long switchResCode, String transactionDate, BankTransactionEntity bankTransaction, MelliVerifyRequestEntity verifyRequest) {
        this.resCode = resCode;
        this.amount = amount;
        this.description = description;
        this.retrivalRefNo = retrivalRefNo;
        this.systemTraceNo = systemTraceNo;
        this.orderId = orderId;
        this.switchResCode = switchResCode;
        this.transactionDate = transactionDate;
        this.bankTransaction = bankTransaction;
        this.verifyRequest = verifyRequest;
    }


    public MelliVerifyResponseEntity(MelliVerifyResponse r, BankTransactionEntity bankTransaction, MelliVerifyRequestEntity verifyRequest){
        this(
                r == null ? 0 : r.getResCode(),
                r == null ? 0 : r.getAmount(),
                r == null ? null : r.getDescription(),
                r == null ? null : r.getRetrivalRefNo(),
                r == null ? null : r.getSystemTraceNo(),
                r == null ? 0 : r.getOrderId(),
                r == null ? 0 : r.getSwitchResCode(),
                r == null ? null : r.getTransactionDate(),
                bankTransaction,
                verifyRequest
        );
    }



    @Basic
    public long getResCode() {
        return resCode;
    }
    public void setResCode(long resCode) {
        this.resCode = resCode;
    }

    @Basic
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    @Basic
    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }


    @Basic
    public String getRetrivalRefNo() {
        return retrivalRefNo;
    }
    public void setRetrivalRefNo(String retrivalRefNo) {
        this.retrivalRefNo = retrivalRefNo;
    }


    @Basic
    public String getSystemTraceNo() {
        return systemTraceNo;
    }
    public void setSystemTraceNo(String systemTraceNo) {
        this.systemTraceNo = systemTraceNo;
    }


    @Basic
    public long getOrderId() {
        return orderId;
    }
    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Basic
    public long getSwitchResCode() {
        return switchResCode;
    }
    public void setSwitchResCode(long switchResCode) {
        this.switchResCode = switchResCode;
    }


    @Basic
    public String getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }



    @ManyToOne
    @JoinColumn(name = "bank_transaction_id", nullable = false)
    public BankTransactionEntity getBankTransaction() {
        return bankTransaction;
    }
    public void setBankTransaction(BankTransactionEntity bankTransaction) {
        this.bankTransaction = bankTransaction;
    }


    @ManyToOne
    @JoinColumn(name = "verify_request_id", nullable = false)
    public MelliVerifyRequestEntity getVerifyRequest() {
        return verifyRequest;
    }
    public void setVerifyRequest(MelliVerifyRequestEntity verifyRequest) {
        this.verifyRequest = verifyRequest;
    }
}
