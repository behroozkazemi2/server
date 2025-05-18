package com.behrouz.server.rest.request;


/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.request
 * Project Name: behta-server
 * 27 May 2020
 **/
public class IdDouble {

    protected long id;

    protected Double amount;

    public IdDouble() {
    }

    public IdDouble(long id, Double amount) {
        this.id = id;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
