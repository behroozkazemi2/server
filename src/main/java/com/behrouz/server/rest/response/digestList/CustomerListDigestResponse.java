package com.behrouz.server.rest.response.digestList;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.global.AddressEntity;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.response.digestList
 * Project Koala Server
 * 10 September 2018 11:10
 **/
public class CustomerListDigestResponse {

    private long id;

    private String name;

    private String mobile;

    private String address;

    public CustomerListDigestResponse() {
    }

    public CustomerListDigestResponse(CustomerEntity customer,
                                      AddressEntity customerAddress ) {
        this.id = customer.getId();
        this.name = String.format( "%s %s" , customer.getFirstName(), customer.getLastName() );
        this.mobile = customer.getMobile();

        if (customerAddress != null) {
            this.address = customerAddress.getAddress();
        }
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }



    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


}
