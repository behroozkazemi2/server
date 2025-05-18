package com.behrouz.server.rest.constant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.constant
 * Project Koala Server
 * 09 September 2018 15:55
 **/
public class RequestResponseList<T> implements Serializable {


    private long total;

    private List<T> list;


    public RequestResponseList() {
    }


    public RequestResponseList(List < T > list, long total) {
        this.list = list;
        this.total = total;
    }



    public long getTotal() {
        return total;
    }
    public void setTotal(long total) {
        this.total = total;
    }



    public List < T > getList() {
        return list;
    }
    public void setList(List < T > list) {
        this.list = list;
    }


    public<R> List<R> getList(Class<R> type) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(mapper.writeValueAsString(getList()) , TypeFactory.defaultInstance().constructCollectionLikeType(List.class , type));
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}