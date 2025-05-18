package com.behrouz.server.rest.response.digestList;

public class BillDashboardRestResponse {

    protected long id;
    protected long count;

    protected Double amount;

    public BillDashboardRestResponse() {
    }

    public BillDashboardRestResponse(long id, long count, Double amount) {
        this.id = id;
        this.count = count;
        this.amount = amount;
    }


    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
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
