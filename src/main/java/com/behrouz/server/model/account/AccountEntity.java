package com.behrouz.server.model.account;

import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.balance.BalanceEntity;
import com.behrouz.server.model.balance.BalanceHistoryEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.model.ticket.TicketMessageEntity;
import com.behrouz.server.strategy.StrategyGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 09:33
 **/

@Entity
@Inheritance
@Table(name = "account", schema = "public")
public class AccountEntity extends BaseEntity {

    protected String accountingNumber;
    protected Date updateDate;
    protected boolean banned;

    protected String mobile;

    protected LocalDate birthDate;

    @OneToMany(mappedBy = "account")
    protected Set<AddressEntity> addresses;


    @OneToMany(mappedBy = "account")
    private Set<BalanceEntity> balances;

    @OneToMany(mappedBy = "account")
    protected Set<BalanceHistoryEntity> balanceHistories;



    @OneToMany(mappedBy = "account")
    protected Set<TicketMessageEntity> ticketMessageEntity;


    @Basic
    @Column(unique = true)
    public String getAccountingNumber() {
        return accountingNumber;
    }
    public void setAccountingNumber( String accountingNumber ) {
        this.accountingNumber = accountingNumber;
    }


    @Basic
    @Length(min = 11, max = 11, message = "The mobile number is too short or too long!")
    @Column(unique = true)
    public String getMobile () {
        return mobile;
    }
    public void setMobile ( String mobile ) {
        this.mobile = mobile;
    }


    @Basic
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }



    @Basic
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate( Date updateDate ) {
        this.updateDate = updateDate;
    }


    @Basic
    @Column(columnDefinition = "boolean default false")
    public boolean isBanned() {
        return banned;
    }
    public void setBanned( boolean banned ) {
        this.banned = banned;
    }




    public AccountEntity(  ) {
    }


    @PrePersist
    void prePersist() {
        this.accountingNumber = StrategyGenerator.generateAccountingNumber();
    }

}
