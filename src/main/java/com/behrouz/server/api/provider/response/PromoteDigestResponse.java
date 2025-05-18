package com.behrouz.server.api.provider.response;

import com.behrouz.server.model.product.PromoteEntity;
import com.behrouz.server.rest.request.IdName;

import java.util.List;

public class PromoteDigestResponse {

    private long id;
    private String name;
    private String startDate;
    private String endDate;
    private long startDateTimeStamp;
    private long endDateTimeStamp;
    private List<IdName> productProviderIds;
    private String startTime;
    private String endTime;

    public PromoteDigestResponse() {
    }

    public PromoteDigestResponse(long id, String name, long startDateTimeStamp, long endDateTimeStamp) {
        this.id = id;
        this.name = name;
        this.startDateTimeStamp = startDateTimeStamp;
        this.endDateTimeStamp = endDateTimeStamp;
    }

    public PromoteDigestResponse(PromoteEntity p, List<IdName> productProviderIds) {
        this.id = p.getId();
        this.name = p.getName();
        this.startDate = p.getStartDate() + "";
        this.endDate = p.getEndDate() + "";
        this.startTime = p.getStartDate().getHour() + ":" + p.getStartDate().getMinute() ;
        this.endTime = p.getEndDate().getHour() + ":" + p.getEndDate().getMinute() ;
        this.productProviderIds = productProviderIds;
    }


    public PromoteDigestResponse(long id, String name, String startDate, String endDate, List<IdName> productProviderIds) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.productProviderIds = productProviderIds;
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


    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public long getStartDateTimeStamp() {
        return startDateTimeStamp;
    }
    public void setStartDateTimeStamp(long startDateTimeStamp) {
        this.startDateTimeStamp = startDateTimeStamp;
    }


    public long getEndDateTimeStamp() {
        return endDateTimeStamp;
    }
    public void setEndDateTimeStamp(long endDateTimeStamp) {
        this.endDateTimeStamp = endDateTimeStamp;
    }


    public List<IdName> getProductProviderIds() {
        return productProviderIds;
    }
    public void setProductProviderIds(List<IdName> productProviderIds) {
        this.productProviderIds = productProviderIds;
    }


    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
