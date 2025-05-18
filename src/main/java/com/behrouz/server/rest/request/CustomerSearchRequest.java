package com.behrouz.server.rest.request;

import com.behrouz.server.rest.constant.DataTableRequest;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.request
 * Project Name: behta-server
 * 15 December 2018
 **/
public class CustomerSearchRequest extends DataTableRequest {

    private String firstName;

    private String lastName;

    private String mobile;

    public CustomerSearchRequest ( String firstName, String lastName, String mobile, int page, int length, int status ) {

        super( page, length, status );

        if (!firstName.isEmpty())
            this.setFirstName(firstName);

        if (!lastName.isEmpty())
            this.setLastName(lastName);

        if (!mobile.isEmpty())
            this.setMobile(mobile);
    }

    public CustomerSearchRequest() {
    }

    public String getFirstName () {
        return firstName;
    }
    public void setFirstName ( String firstName ) {
        this.firstName = firstName;
    }


    public String getLastName () {
        return lastName;
    }
    public void setLastName ( String lastName ) {
        this.lastName = lastName;
    }


    public String getMobile () {
        return mobile;
    }
    public void setMobile ( String mobile ) {
        this.mobile = mobile;
    }
}
