package com.behrouz.server.api.customer.response;

import com.behrouz.server.utils.date.PersianDateUtil;
import com.behrouz.server.model.OffCodeEntity;

import java.util.Date;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer.response
 * Project Name: behta-server
 * 20 January 2019
 **/
public class OffCodeResponse {

    private Date insertDate;
    private Date expireDate;
    private String offCode;
    private long maxAllowedUsage;
    private long remainUsage;
    private String explain;
    private float offPercent;
    private boolean expired;
    private String stInsertDate;
    private String stExpireDate;

    public OffCodeResponse ( OffCodeEntity offCode, long offCodeUsage, long customerUsed, boolean expired ) {
        this.insertDate = offCode.getStartDate();
        this.expireDate = offCode.getExpireDate();
        this.offCode = offCode.getCode();
        this.maxAllowedUsage = offCodeUsage;
        this.remainUsage = Math.max( offCodeUsage - customerUsed, 0 );
        this.explain = offCode.getDescription();
        this.offPercent = offCode.getPercent();
        this.expired = expired;
        this.stInsertDate = String.valueOf(PersianDateUtil.getPersianDate(offCode.getStartDate()));
        this.stExpireDate = String.valueOf(PersianDateUtil.getPersianDate(offCode.getExpireDate()));
    }


    public Date getInsertDate () {
        return insertDate;
    }
    public void setInsertDate ( Date insertDate ) {
        this.insertDate = insertDate;
    }


    public Date getExpireDate () {
        return expireDate;
    }
    public void setExpireDate ( Date expireDate ) {
        this.expireDate = expireDate;
    }


    public String getOffCode () {
        return offCode;
    }
    public void setOffCode ( String offCode ) {
        this.offCode = offCode;
    }


    public long getMaxAllowedUsage () {
        return maxAllowedUsage;
    }
    public void setMaxAllowedUsage ( long maxAllowedUsage ) {
        this.maxAllowedUsage = maxAllowedUsage;
    }


    public long getRemainUsage () {
        return remainUsage;
    }
    public void setRemainUsage ( long remainUsage ) {
        this.remainUsage = remainUsage;
    }


    public String getExplain () {
        return explain;
    }
    public void setExplain ( String explain ) {
        this.explain = explain;
    }


    public float getOffPercent () {
        return offPercent;
    }
    public void setOffPercent ( float offPercent ) {
        this.offPercent = offPercent;
    }


    public boolean isExpired () {
        return expired;
    }
    public void setExpired ( boolean expired ) {
        this.expired = expired;
    }

    public String getStInsertDate() {
        return stInsertDate;
    }
    public void setStInsertDate(String stInsertDate) {
        this.stInsertDate = stInsertDate;
    }

    public String getStExpireDate() {
        return stExpireDate;
    }
    public void setStExpireDate(String stExpireDate) {
        this.stExpireDate = stExpireDate;
    }
}
