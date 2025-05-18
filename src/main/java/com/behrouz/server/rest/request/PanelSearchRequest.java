package com.behrouz.server.rest.request;

import com.behrouz.server.rest.constant.DataTableRequest;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.rest.constant
 * Project Koala Server
 * 09 September 2018 16:36
 **/
public class PanelSearchRequest extends DataTableRequest {

    private String pCode;
    private String pName;
    private String cNumber;// customer number

    public PanelSearchRequest ( String productName, int page, int length, int status ) {

        super( page, length, status );

        if (!productName.isEmpty())
            this.pName = productName;

    }

    public PanelSearchRequest ( String productName ) {
        if (!productName.isEmpty())
            this.pName = productName;
    }

    public PanelSearchRequest ( String name, int status ) {
        this.pName = name;
        this.setStatus( status );
    }


    public String getpCode() {
        return pCode;
    }
    public void setpCode(String pCode) {
        this.pCode = pCode;
    }



    public String getpName() {
        return pName;
    }
    public void setpName(String pName) {
        this.pName = pName;
    }



    public String getcNumber() {
        return cNumber;
    }
    public void setcNumber(String cNumber) {
        this.cNumber = cNumber;
    }


}
