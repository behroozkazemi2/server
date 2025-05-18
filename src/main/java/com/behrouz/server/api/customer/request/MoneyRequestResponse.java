package com.behrouz.server.api.customer.request;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer.request
 * Project Name: behta-server
 * 06 December 2018
 **/
public class MoneyRequestResponse {

    private long amount; // rial
    private long amountRial;



    public MoneyRequestResponse(long amount) {
        this.amount = amount;
    }

    public MoneyRequestResponse() {
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setAmountRial(long amountRial) {
        this.amountRial = amountRial;
    }
    public long getAmountRial(){
        return amount*10;
    }

    public MoneyRequestResponse toToman () {
        this.amount /= 10;
        return this;
    }
}
