package com.behrouz.server.rest.request;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.balance.BalanceEntity;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.request
 * Project Name: behta-server
 * 10 December 2018
 **/
public class EditCustomerRequest {

    private long id;

    private String firstName;
    private String lastName;
    private String mobile;
    private String accountingNumber;

    private long amount;


    public EditCustomerRequest ( CustomerEntity customer, BalanceEntity balance ) {

        this.id = customer.getId();

        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.mobile = customer.getMobile();
        this.accountingNumber = customer.getAccountingNumber();

        this.amount = balance.getAmount();

    }


    public long getId () {
        return id;
    }
    public void setId ( long id ) {
        this.id = id;
    }


    public String getFirstName () {
        return firstName;
    }
    public void setFirstName ( String firstName ) {
        this.firstName = firstName;
    }


    public String getLastName () {
        return lastName;
    }
    public void setLastName ( String lastName ) {
        this.lastName = lastName;
    }


    public String getMobile () {
        return mobile;
    }
    public void setMobile ( String mobile ) {
        this.mobile = mobile;
    }


    public String getAccountingNumber () {
        return accountingNumber;
    }
    public void setAccountingNumber ( String accountingNumber ) {
        this.accountingNumber = accountingNumber;
    }


    public long getAmount () {
        return amount;
    }
    public void setAmount ( long amount ) {
        this.amount = amount;
    }
}
