package com.behrouz.server.rest.request;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.request
 * Project Koala Server
 * 09 September 2018 16:47
 **/
public class ChangeStatusRequest {

    //this id of vacation that we want to change status id
    private int id;
    //the new status for this vacation
    private long status;



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }



    public long getStatus() {
        return status;
    }
    public void setStatus(long status) {
        this.status = status;
    }


}
