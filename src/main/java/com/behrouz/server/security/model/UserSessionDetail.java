package com.behrouz.server.security.model;

import com.behrouz.server.model.global.ImageEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.security.session
 * project name:  ximaServer
 * 11 July 2018
 **/


public class UserSessionDetail implements Serializable {


    private long id;

    private String firstName;

    private String lastName;

    private byte[] avatar;

    private String token;

    private Date loginDate;

    private long addressId;


    public UserSessionDetail() {
    }


    public UserSessionDetail(long id, String firstName, String lastName, ImageEntity avatar, String token, Date loginDate, long addressId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

        if ( avatar != null ) {
            this.avatar = avatar.getImage();
        }

        this.token = token;
        this.loginDate = loginDate;
        this.addressId = addressId;
    }
    public UserSessionDetail(long id, String firstName, String lastName, ImageEntity avatar, String token, Date loginDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

        if ( avatar != null ) {
            this.avatar = avatar.getImage();
        }

        this.token = token;
        this.loginDate = loginDate;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public byte[] getAvatar() {
        return avatar;
    }
    public void setAvatar( byte[] avatar ) {
        this.avatar = avatar;
    }


    public String getToken() {
        return token;
    }
    public void setToken( String token ) {
        this.token = token;
    }


    public Date getLoginDate() {
        return loginDate;
    }
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }


    public long getAddressId() {
        return addressId;
    }
    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
}
