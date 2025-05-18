package com.behrouz.server.model.bill;

import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 15:19
 **/

/**
 * Sefaresh
 *
 * When a user has some order and click on "Sefarsh" we generate a BillEntity
 *
 * When a Bill is ready to send to customer, we make another Bill that is modified version of previous Bill
 *
 */
@Entity
@Table(name = "bill_bill_status", schema = "public")
public class BillBillStatusEntity extends BaseEntity  {

    private BillEntity bill;

    private BillStatusEntity status;

    private AccountEntity account;


    public BillBillStatusEntity() {
    }

    public BillBillStatusEntity(BillEntity bill, BillStatusEntity status, AccountEntity account) {
        this.bill = bill;
        this.status = status;
        this.account = account;
    }

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    public BillEntity getBill() {
        return bill;
    }
    public void setBill(BillEntity bill) {
        this.bill = bill;
    }


    @ManyToOne
    @JoinColumn(name = "bill_status_id", nullable = false)
    public BillStatusEntity getStatus() {
        return status;
    }
    public void setStatus(BillStatusEntity status) {
        this.status = status;
    }


    @ManyToOne
    @JoinColumn(name = "account_id")
    public AccountEntity getAccount() {
        return account;
    }
    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
