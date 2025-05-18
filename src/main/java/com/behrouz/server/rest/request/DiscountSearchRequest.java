package com.behrouz.server.rest.request;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.request
 * Project Name: behta-server
 * 26 March 2019
 **/
public class DiscountSearchRequest extends ListRequest {

    private boolean validDiscount;


    public boolean isValidDiscount () {
        return validDiscount;
    }
    public void setValidDiscount ( boolean validDiscount ) {
        this.validDiscount = validDiscount;
    }
}
