package com.behrouz.server.rest.request;

public class InformationRestRequest {

    private long id;
    private String name;
    private String value;
    private long productId;
    private long informationCategoryId;
    private double showOrder;

    public InformationRestRequest() {
    }

    public InformationRestRequest(long id, String name, String value, long productId, long informationCategoryId, double showOrder) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.productId = productId;
        this.informationCategoryId = informationCategoryId;
        this.showOrder = showOrder;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getShowOrder() {
        return showOrder;
    }
    public void setShowOrder(double showOrder) {
        this.showOrder = showOrder;
    }

    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getInformationCategoryId() {
        return informationCategoryId;
    }
    public void setInformationCategoryId(long informationCategoryId) {
        this.informationCategoryId = informationCategoryId;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
