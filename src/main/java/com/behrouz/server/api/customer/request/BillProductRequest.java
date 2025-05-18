package com.behrouz.server.api.customer.request;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 07 July 2020
 **/
public class BillProductRequest {

    private int billId;

    private int billProductId;

    public BillProductRequest(int billId, int billProductId) {
        this.billId = billId;
        this.billProductId = billProductId;
    }

    public BillProductRequest() {
    }

    public int getBillId() {
        return billId;
    }
    public void setBillId(int billId) {
        this.billId = billId;
    }


    public int getBillProductId() {
        return billProductId;
    }
    public void setBillProductId(int billProductId) {
        this.billProductId = billProductId;
    }
}
