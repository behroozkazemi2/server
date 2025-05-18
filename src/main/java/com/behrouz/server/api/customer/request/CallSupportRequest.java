package com.behrouz.server.api.customer.request;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer.request
 * Project Name: behta-server
 * 10 December 2018
 **/
public class CallSupportRequest {

    private String subject;

    private String description;

    private byte[] image;


    public CallSupportRequest () {
    }


    public String getSubject () {
        return subject;
    }
    public void setSubject ( String subject ) {
        this.subject = subject;
    }


    public String getDescription () {
        return description;
    }
    public void setDescription ( String description ) {
        this.description = description;
    }


    public byte[] getImage () {
        return image;
    }
    public void setImage ( byte[] image ) {
        this.image = image;
    }
}
