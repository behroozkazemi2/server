package com.behrouz.server.api.customer.response;

import com.behrouz.server.rest.constant.RequestDetailResponse;

import java.util.Date;
import java.util.List;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 09 July 2020
 **/
public class CandidateDateTimeResponse {

    private List<Date> dates;

    private List<RequestDetailResponse> times;


    public CandidateDateTimeResponse() {
    }

    public CandidateDateTimeResponse(List<Date> dates, List<RequestDetailResponse> times) {
        this.dates = dates;
        this.times = times;
    }

    public List<Date> getDates() {
        return dates;
    }
    public void setDates(List<Date> dates) {
        this.dates = dates;
    }


    public List<RequestDetailResponse> getTimes() {
        return times;
    }
    public void setTimes(List<RequestDetailResponse> times) {
        this.times = times;
    }


}