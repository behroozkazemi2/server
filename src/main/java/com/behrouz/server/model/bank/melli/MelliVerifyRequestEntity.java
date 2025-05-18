package com.behrouz.server.model.bank.melli;


import com.behrouz.server.bank.saman.request.MelliVerifyRequest;
import com.behrouz.server.model.bank.BankTransactionEntity;
import com.behrouz.server.model.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by thunderbolt on 9/16/17.
 */

@Entity
@Table(name = "bank_melli_verify_request" , schema = "public")
public class MelliVerifyRequestEntity extends BaseEntity {

    private String token;

    private String signData;

    private BankTransactionEntity bankTransaction;

    @OneToMany(mappedBy = "verifyRequest")
    private Set<MelliVerifyResponseEntity> verifyResponse;



    public MelliVerifyRequestEntity() {
    }


    public MelliVerifyRequestEntity(String token, String signData, BankTransactionEntity bankTransaction) {
        this.token = token;
        this.signData = signData;
        this.bankTransaction = bankTransaction;
    }

    public MelliVerifyRequestEntity(MelliVerifyRequest r, BankTransactionEntity bankTransaction) {
        this(
                r.getToken(),
                r.getSignData(),
                bankTransaction
        );
    }



    @Basic
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }


    @Basic
    public String getSignData() {
        return signData;
    }
    public void setSignData(String signData) {
        this.signData = signData;
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
