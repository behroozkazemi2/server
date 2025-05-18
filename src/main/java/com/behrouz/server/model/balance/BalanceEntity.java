package com.behrouz.server.model.balance;

import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.model
 * Project Name: behta-server
 * 06 December 2018
 **/

@Entity
@Table(name = "balance", schema = "public")
public class BalanceEntity extends BaseEntity {

    private long amount;

    private AccountEntity account;

    private String description;




    public BalanceEntity () {
    }

    public BalanceEntity(AccountEntity account, long amount, String description) {
        this.account = account;
        this.amount = amount;
        this.description = description;
    }

    public BalanceEntity ( String token, long amount, CustomerEntity account ) {
        this.amount = amount;
        this.account = account;
    }

    public BalanceEntity ( AccountEntity account ) {
        this.account = account;
        this.amount = 0;
    }

    public BalanceEntity ( AccountEntity account, long amount ) {
        this.amount = amount;
        this.account = account;
    }



    @Basic
    public long getAmount () {
        return amount;
    }
    public void setAmount ( long amount ) {
        this.amount = amount;
    }


    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    public AccountEntity getAccount() {
        return account;
    }
    public void setAccount(AccountEntity account) {
        this.account = account;
    }




    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}
