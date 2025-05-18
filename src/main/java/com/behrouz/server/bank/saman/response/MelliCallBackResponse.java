package com.behrouz.server.bank.saman.response;


//call back send on post method

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MelliCallBackResponse {

    private Long OrderId;
    private String HashedCardNo;
    private String PrimaryAccNo;
    private Integer SwitchResCode;
    private String Token;
    private Integer ResCode;


    public MelliCallBackResponse() {
    }



    public long getOrderId() {
        return OrderId == null ? 0 : OrderId;
    }
    public void setOrderId(long orderId) {
        OrderId = orderId;
    }



    public String getHashedCardNo() {
        return HashedCardNo;
    }
    public void setHashedCardNo(String hashedCardNo) {
        HashedCardNo = hashedCardNo;
    }



    public String getPrimaryAccNo() {
        return PrimaryAccNo;
    }
    public void setPrimaryAccNo(String primaryAccNo) {
        PrimaryAccNo = primaryAccNo;
    }



    public int getSwitchResCode() {
        return SwitchResCode == null ? 0 : SwitchResCode;
    }
    public void setSwitchResCode(int switchResCode) {
        SwitchResCode = switchResCode;
    }



    public String getToken() {
        return Token;
    }
    public void setToken(String token) {
        Token = token;
    }



    public int getResCode() {
        return ResCode == null ? 0 : ResCode;
    }
    public void setResCode(int resCode) {
        ResCode = resCode;
    }


    @Override
    public String toString() {
        return "MelliCallBackResponse{" +
                "OrderId=" + OrderId +
                ", HashedCardNo='" + HashedCardNo + '\'' +
                ", PrimaryAccNo='" + PrimaryAccNo + '\'' +
                ", SwitchResCode=" + SwitchResCode +
                ", Token='" + Token + '\'' +
                ", ResCode=" + ResCode +
                '}';
    }
}
