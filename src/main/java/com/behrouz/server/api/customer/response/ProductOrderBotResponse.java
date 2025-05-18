package com.behrouz.server.api.customer.response;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 06 July 2020
 **/
public class ProductOrderBotResponse {

    private long productId;

    private String productName;

    private long billId;

    private long productBillId;


    public ProductOrderBotResponse() {
    }
    public ProductOrderBotResponse(long productId, String productName, long billId, long productBillId) {
        this.productId = productId;
        this.productName = productName;
        this.billId = billId;
        this.productBillId = productBillId;
    }



    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }


    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }


    public long getBillId() {
        return billId;
    }
    public void setBillId(long billId) {
        this.billId = billId;
    }


    public long getProductBillId() {
        return productBillId;
    }
    public void setProductBillId(long productBillId) {
        this.productBillId = productBillId;
    }
}
