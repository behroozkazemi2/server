package com.behrouz.server.rest.request;

public class FactorListRequest extends ListRequest {

    private long factorType;
    private int page;
    private int length;

    public FactorListRequest() {
    }

    public FactorListRequest(long factorType, int page, int length) {
        this.factorType = factorType;
        this.page = page;
        this.length = length;
    }


    public long getFactorType() {
        return factorType;
    }
    public void setFactorType(long factorType) {
        this.factorType = factorType;
    }


    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }


    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
}
