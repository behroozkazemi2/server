package com.behrouz.server.model.bank.melli;


import com.behrouz.server.bank.saman.response.MelliCallBackResponse;
import com.behrouz.server.model.bank.BankTransactionEntity;
import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;
import javax.persistence.Basic;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name = "bank_melli_call_back_response" , schema = "public")
public class MelliCallBackResponseEntity extends BaseEntity {

    private long OrderId;

    private String HashedCardNo;

    private String PrimaryAccNo;

    private int SwitchResCode;

    private String Token;

    private int ResCode;

    private BankTransactionEntity bankTransaction;


    public MelliCallBackResponseEntity() {
    }

    public MelliCallBackResponseEntity(long orderId, String hashedCardNo, String primaryAccNo, int switchResCode, String token, int resCode, BankTransactionEntity bankTransaction) {
        this.OrderId = orderId;
        this.HashedCardNo = hashedCardNo;
        this.PrimaryAccNo = primaryAccNo;
        this.SwitchResCode = switchResCode;
        this.Token = token;
        this.ResCode = resCode;
        this.bankTransaction = bankTransaction;
    }

    public MelliCallBackResponseEntity(MelliCallBackResponse callBackResponse, BankTransactionEntity bankTransaction) {
        this(
                callBackResponse.getOrderId(),
                callBackResponse.getHashedCardNo(),
                callBackResponse.getPrimaryAccNo(),
                callBackResponse.getSwitchResCode(),
                callBackResponse.getToken(),
                callBackResponse.getResCode(),
                bankTransaction
        );
    }




    @Basic
    public long getOrderId() {
        return OrderId;
    }
    public void setOrderId(long orderId) {
        OrderId = orderId;
    }


    @Basic
    public String getHashedCardNo() {
        return HashedCardNo;
    }
    public void setHashedCardNo(String hashedCardNo) {
        HashedCardNo = hashedCardNo;
    }


    @Basic
    public String getPrimaryAccNo() {
        return PrimaryAccNo;
    }
    public void setPrimaryAccNo(String primaryAccNo) {
        PrimaryAccNo = primaryAccNo;
    }


    @Basic
    public int getSwitchResCode() {
        return SwitchResCode;
    }
    public void setSwitchResCode(int switchResCode) {
        SwitchResCode = switchResCode;
    }


    @Basic
    public String getToken() {
        return Token;
    }
    public void setToken(String token) {
        Token = token;
    }


    @Basic
    public int getResCode() {
        return ResCode;
    }
    public void setResCode(int resCode) {
        ResCode = resCode;
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
