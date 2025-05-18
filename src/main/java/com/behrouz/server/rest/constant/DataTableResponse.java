package com.behrouz.server.rest.constant;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.constant
 * Project Koala Server
 * 09 September 2018 15:58
 **/
public class DataTableResponse<T> {

    private int draw;
    private int start;
    private long recordsTotal;
    private long recordsFiltered;
    private List<T> data;



    public DataTableResponse ( RequestResponseList responseList, int draw, int start ) {

        this.data = responseList.getList();
        this.recordsTotal = responseList.getTotal();
        this.recordsFiltered = responseList.getTotal();
        this.draw = draw;
        this.start = start;

    }

    public DataTableResponse ( List<T> data, long count ) {

        this.data = data;
        this.recordsTotal = count;
        this.recordsFiltered = count;

    }


    public DataTableResponse () {
    }



    public int getDraw() {
        return draw;
    }
    public void setDraw(int draw) {
        this.draw = draw;
    }



    public long getRecordsTotal() {
        return recordsTotal;
    }
    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }



    public long getRecordsFiltered() {
        return recordsFiltered;
    }
    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }



    public List<T> getData() {
        return data;
    }
    public DataTableResponse setData(List<T> data) {
        this.data = data;
        return this;
    }




    public int getStart() {
        return start;
    }
    public void setStart(int start) {
        this.start = start;
    }

}