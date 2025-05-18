package com.behrouz.server.rest.request;

import java.io.Serializable;

public class YearMonthFilterRequest implements Serializable {

    protected long year;
    protected long month;
    protected boolean groupByMonth;


    public YearMonthFilterRequest() {
    }

    public YearMonthFilterRequest(long year, long month, boolean groupByMonth) {
        this.year = year;
        this.month = month;
        this.groupByMonth = groupByMonth;
    }

    public long getYear() {
        return year;
    }
    public void setYear(long year) {
        this.year = year;
    }

    public long getMonth() {
        return month;
    }
    public void setMonth(long month) {
        this.month = month;
    }

    public boolean isGroupByMonth() {
        return groupByMonth;
    }
    public void setGroupByMonth(boolean groupByMonth) {
        this.groupByMonth = groupByMonth;
    }
}