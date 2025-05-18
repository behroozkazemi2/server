package com.behrouz.server.model.bank;


import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.rest.response.bank.SamanTokenRestResponse;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "saman_token_response", schema = "public")
public class SamanTokenResponseEntity extends BaseEntity {

    private int status;
    private String token;
    private String errorCode;
    private String errorDesc;
    private String billTrackingCode;

    public SamanTokenResponseEntity() {
    }

    public SamanTokenResponseEntity(int status, String token, String errorCode, String errorDesc, String billTrackingCode) {
        this.status = status;
        this.token = token;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.billTrackingCode = billTrackingCode;
    }

    public SamanTokenResponseEntity(SamanTokenRestResponse bankRes, String billTrackingCode) {
        this(
                bankRes.getStatus(),
                bankRes.getToken(),
                bankRes.getErrorDesc(),
                bankRes.getErrorCode(),
                billTrackingCode
        );
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getBillTrackingCode() {
        return billTrackingCode;
    }
    public void setBillTrackingCode(String billTrackingCode) {
        this.billTrackingCode = billTrackingCode;
    }
}
