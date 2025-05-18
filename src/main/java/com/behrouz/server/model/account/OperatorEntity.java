package com.behrouz.server.model.account;

import com.behrouz.server.model.global.ImageEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 14:54
 **/

@Entity
@Inheritance
public class OperatorEntity extends AccountEntity {

    private String firstName;
    private String lastName;
    private String username;

    private ImageEntity avatar;


    private long providerId; // 0 is super admin



    @OneToMany(mappedBy = "operator")
    private Set<OperatorLoginHistoryEntity> operatorLoginHistories;

    @OneToMany(mappedBy = "operator")
    private Set<PasswordEntity> passwords;


    public OperatorEntity() {
    }

    public OperatorEntity(String name) {
        this.username = name;

    }


    @Basic
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    @Basic
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Basic
    @Column(unique = true)
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }


    @ManyToOne
    @JoinColumn(name = "avatar_id")
    public ImageEntity getAvatar() {
        return avatar;
    }
    public void setAvatar(ImageEntity avatar) {
        this.avatar = avatar;
    }


    @Column(columnDefinition = "BigInt default 0")
    public long getProviderId() {
        return providerId;
    }
    public void setProviderId(long providerId) {
        this.providerId = providerId;
    }


}
