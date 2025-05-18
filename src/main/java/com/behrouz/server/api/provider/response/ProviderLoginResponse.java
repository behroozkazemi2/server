package com.behrouz.server.api.provider.response;

import com.behrouz.server.model.account.OperatorEntity;

public class ProviderLoginResponse {

    private long id;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private String emergencyPhone;

    private String instagramId;

    private String telegramId;

    private long avatar;

    private String token;

    private String username;

    //master operator
    private boolean master;

    private long providerId;


    public ProviderLoginResponse() {
    }

    public ProviderLoginResponse(long id, String firstName, String lastName, long avatar, String token, String username, boolean master, long providerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.token = token;
        this.username = username;
        this.master = master;
        this.providerId = providerId;
    }

    public ProviderLoginResponse(OperatorEntity operator, String token) {
        this(
            operator.getId(),
            operator.getFirstName(),
            operator.getLastName(),
            operator.getAvatar() != null ? operator.getAvatar().getId() : 0,
            token,
            operator.getUsername(),
            operator.getProviderId() == 0,
            operator.getProviderId()
        );
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getInstagramId() {
        return instagramId;
    }

    public void setInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public long getAvatar() {
        return avatar;
    }

    public void setAvatar(long avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }
}
