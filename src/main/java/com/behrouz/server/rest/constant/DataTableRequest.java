package com.behrouz.server.rest.constant;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.constant
 * Project Koala Server
 * 09 September 2018 15:58
 **/
public class DataTableRequest {

    private int page;

    private int limit;

    private String search;

    private int status;

    public DataTableRequest() {

    }

    public DataTableRequest(int page, int length, int status) {
        this.page = page;
        this.limit = length;
        this.status = status;
    }


    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }



    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }



    public String getSearch() {
        return search;
    }
    public void setSearch(String search) {
        this.search = search;
    }



    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }


}

