package com.behrouz.server.rest.request;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.request
 * Project Name: behta-server
 * 27 May 2020
 **/
public class IdNameType {

    protected long id;

    protected String name;

    protected long type;


    public IdNameType() {
    }

    public IdNameType(long id, long type) {
        this.id = id;
        this.type = type;
    }

    public IdNameType(long id, String name, long type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public long getId () {
        return id;
    }
    public void setId ( long id ) {
        this.id = id;
    }


    public String getName () {
        return name;
    }
    public void setName ( String name ) {
        this.name = name;
    }


    public long getType() {
        return type;
    }
    public void setType(long type) {
        this.type = type;
    }
}
