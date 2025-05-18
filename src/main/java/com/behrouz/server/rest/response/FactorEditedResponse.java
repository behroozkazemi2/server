package com.behrouz.server.rest.response;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.response
 * Project Name: behta-server
 * 10 March 2019
 **/
public class FactorEditedResponse {

    private float newProductPrice;

    private float newPayableAmount;


    public FactorEditedResponse ( float newProductPrice, float newPayableAmount ) {
        this.newProductPrice = newProductPrice;
        this.newPayableAmount = newPayableAmount;
    }


    public FactorEditedResponse () {
    }

    public float getNewProductPrice () {
        return newProductPrice;
    }
    public void setNewProductPrice ( float newProductPrice ) {
        this.newProductPrice = newProductPrice;
    }


    public float getNewPayableAmount () {
        return newPayableAmount;
    }
    public void setNewPayableAmount ( float newPayableAmount ) {
        this.newPayableAmount = newPayableAmount;
    }
}
