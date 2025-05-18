package com.behrouz.server.api.provider.query;

public class QueryPairObject<F,S> {

    private F first;

    private S second;


    public QueryPairObject(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }
    public void setFirst(F first) {
        this.first = first;
    }

    public S getSecond() {
        return second;
    }
    public void setSecond(S second) {
        this.second = second;
    }
}
