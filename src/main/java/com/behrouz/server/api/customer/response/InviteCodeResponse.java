package com.behrouz.server.api.customer.response;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.response
 * Project server
 * 30 September 2018 10:00
 **/
public class InviteCodeResponse {

    private String inviteCode; // hamun hex

    private String inviteLink; // hala bashe...

    private int countUsed; // chan nafar ba in code ozv shdn ta hala

    public InviteCodeResponse( String invitingCode, String inviteLink, int countUsed ) {
        this.inviteCode = invitingCode;
        this.inviteLink = inviteLink;
        this.countUsed = countUsed;
    }


    public String getInviteCode() {
        return inviteCode;
    }
    public void setInviteCode( String inviteCode ) {
        this.inviteCode = inviteCode;
    }


    public String getInviteLink() {
        return inviteLink;
    }
    public void setInviteLink( String inviteLink ) {
        this.inviteLink = inviteLink;
    }


    public int getCountUsed() {
        return countUsed;
    }
    public void setCountUsed( int countUsed ) {
        this.countUsed = countUsed;
    }
}

