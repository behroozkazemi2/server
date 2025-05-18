package com.behrouz.server.api.customer.response;

import com.behrouz.server.model.product.PromoteEntity;
import com.behrouz.server.utils.LocalDateTimeUtil;

import java.util.Date;
import java.util.List;


public class PromotePromoteProductResponse {

    private String promoteName;
    private long promoteId;
    private Date startDate;
    private Date endDate;
    private List<ProductResponse> products;

    public PromotePromoteProductResponse() {
    }


    public PromotePromoteProductResponse(PromoteEntity promoteEntity, List<ProductResponse> products) {
        this.promoteName = promoteEntity.getName();
        this.promoteId = promoteEntity.getId();
        this.startDate = LocalDateTimeUtil.localDateTimeToDate(promoteEntity.getStartDate());
        this.endDate = LocalDateTimeUtil.localDateTimeToDate(promoteEntity.getEndDate());
        this.products = products;
    }


    public String getPromoteName() {
        return promoteName;
    }
    public void setPromoteName(String promoteName) {
        this.promoteName = promoteName;
    }


    public long getPromoteId() {
        return promoteId;
    }
    public void setPromoteId(long promoteId) {
        this.promoteId = promoteId;
    }


    public List<ProductResponse> getProducts() {
        return products;
    }
    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }


    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
