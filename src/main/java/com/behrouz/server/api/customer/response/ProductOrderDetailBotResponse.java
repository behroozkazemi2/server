package com.behrouz.server.api.customer.response;



import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.utils.LocalDateTimeUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 07 July 2020
 **/
public class ProductOrderDetailBotResponse {

    private long billId;
    private long billProductId;
    private long productProviderId;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String description;
    private String unitAmount; //مثال: 3 کیلوگرم - 2 باکس - 15 شاخه
    private long amount ; // 10000
    private boolean customized;
    private List<Long> images;
    private Date insertDate;
    private Date orderDate;
    private String  orderTime;

    public ProductOrderDetailBotResponse(BillProductProviderEntity billProduct, List<Long> imageList) {
        this.billId =  billProduct.getBill().getId();
        this.billProductId = billProduct.getId();
        this.productProviderId = billProduct.getProductProvider().getId();
        this.name = billProduct.getProductProvider().getProduct().getName();
        this.shortDescription = billProduct.getProductProvider().getProduct().getShortDescription();
        this.fullDescription = billProduct.getProductProvider().getProduct().getFullDescription();
        this.description = "";

        this.unitAmount = "" + billProduct.getOrderCount() + " " + billProduct.getProductProvider().getProduct().getProductUnit().getName();

        this.amount = 0;
//        this.customized = billProduct.getProductProvider().isCustomized(); todo
        this.images = imageList;
        this.insertDate = LocalDateTimeUtil.localDateTimeToDate(billProduct.getBill().getInsertDate());
        this.orderDate = billProduct.getBill().getOrderDate();
        this.orderTime = billProduct.getBill().getOrderTime().getName();
    }


    public ProductOrderDetailBotResponse(long billId, long billProductId, long productProviderId, String name, float rate, String shortDescription, String fullDescription, String unitAmount, long amount, boolean customized, List<Long> images, Date insertDate, Date orderDate) {
        this.billId = billId;
        this.billProductId = billProductId;
        this.productProviderId = productProviderId;
        this.name = name;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.unitAmount = unitAmount;
        this.amount = amount;
        this.customized = customized;
        this.images = images;
        this.insertDate = insertDate;
        this.orderDate = orderDate;
    }


    public long getBillId() {
        return billId;
    }
    public void setBillId(long billId) {
        this.billId = billId;
    }


    public long getBillProductId() {
        return billProductId;
    }
    public void setBillProductId(long billProductId) {
        this.billProductId = billProductId;
    }


    public long getProductProviderId() {
        return productProviderId;
    }
    public void setProductProviderId(long productProviderId) {
        this.productProviderId = productProviderId;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public String getShortDescription() {
        return shortDescription;
    }
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }


    public String getFullDescription() {
        return fullDescription;
    }
    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }


    public String getUnitAmount() {
        return unitAmount;
    }
    public void setUnitAmount(String unitAmount) {
        this.unitAmount = unitAmount;
    }


    public long getAmount() {
        return amount;
    }
    public void setAmount(long amount) {
        this.amount = amount;
    }


    public boolean isCustomized() {
        return customized;
    }
    public void setCustomized(boolean customized) {
        this.customized = customized;
    }


    public List<Long> getImages() {
        return images;
    }
    public void setImages(List<Long> images) {
        this.images = images;
    }


    public Date getInsertDate() {
        return insertDate;
    }
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }


    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}

