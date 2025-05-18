package com.behrouz.server.rest.request;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.request
 * Project Name: behta-server
 * 27 May 2020
 **/
public class IdNameTypeValue {

    protected long id;

    protected String name;

    protected long type;

    protected long value;


    public IdNameTypeValue() {
    }


    public IdNameTypeValue(long id, String name, long type, long value) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
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


    public long getValue() {
        return value;
    }
    public void setValue(long value) {
        this.value = value;
    }
}
