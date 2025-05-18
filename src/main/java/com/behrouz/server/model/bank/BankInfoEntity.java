package com.behrouz.server.model.bank;


import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "bank_info", schema = "public")
public class BankInfoEntity extends BaseEntity {


    private AccountEntity account;

    private String accountNo;

    private String branchCode;

    private String cardNo;

    private String shbNo;

    public BankInfoEntity() {
    }

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    public AccountEntity getAccount() {
        return account;
    }
    public void setAccount(AccountEntity account) {
        this.account = account;
    }


    @Basic
    public String getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }


    @Basic
    public String getBranchCode() {
        return branchCode;
    }
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }



    @Basic
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }



    @Basic
    public String getShbNo() {
        return shbNo;
    }
    public void setShbNo(String shbNo) {
        this.shbNo = shbNo;
    }
}
