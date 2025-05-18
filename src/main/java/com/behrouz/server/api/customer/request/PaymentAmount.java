package com.behrouz.server.api.customer.request;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer.request
 * Project Name: behta-server
 * 04 July 2020
 **/

public class PaymentAmount {

    private long amount;

    private String link;

    private long billId;


    public PaymentAmount ( long amount, String link, long billId ) {
        this.amount = amount;
        this.link = link;
        this.billId = billId;
    }

    public PaymentAmount toToman(){
        this.amount /= 10;
        return this;
    }

    public long getAmount () {
        return amount;
    }
    public void setAmount ( long amount ) {
        this.amount = amount;
    }


    public String getLink () {
        return link;
    }
    public void setLink ( String link ) {
        this.link = link;
    }


    public long getBillId () {
        return billId;
    }
    public void setBillId ( long billId ) {
        this.billId = billId;
    }


    public PaymentAmount () {
    }
}
