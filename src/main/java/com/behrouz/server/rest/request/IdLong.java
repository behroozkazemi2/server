package com.behrouz.server.rest.request;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.request
 * Project Name: behta-server
 * 27 May 2020
 **/
public class IdLong {

    protected long id;

    protected long value;


    public IdLong() {
    }
    public IdLong(long id, long value ) {
        this.id = id;
        this.value = value;
    }





    public long getId () {
        return id;
    }
    public void setId ( long id ) {
        this.id = id;
    }


    public long getValue () {
        return value;
    }
    public void setValue ( long value ) {
        this.value = value;
    }

}
