package com.behrouz.server.model.account;

import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.api.customer.request.VerifyRequest;
import com.behrouz.server.model.ApplicationVersionEntity;
import com.behrouz.server.model.MobilePlatformEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 11:30
 **/

@Entity
@Table(name = "customer_login", schema = "public")
public class CustomerLoginEntity extends BaseEntity {


    private String notificationId;
    private String token;
    private String imei;
    private String ip;
    private boolean expired;
    private boolean logout;
    private Date logoutDate;
    private boolean uninstall;
    private Date uninstallDate;

    private MobilePlatformEntity mobilePlatform;
    private ApplicationVersionEntity applicationVersion;
    private AccountEntity account;


    public CustomerLoginEntity() {
    }


    public CustomerLoginEntity(String notificationId, String token, String imei, String ip, AccountEntity account) {
        this.notificationId = notificationId;
        this.token = token;
        this.imei = imei;
        this.ip = ip;
        this.account = account;
    }

    public CustomerLoginEntity(String notificationId, String token, String imei, String ip, boolean expired, boolean logout, Date logoutDate, boolean uninstall, Date uninstallDate, MobilePlatformEntity mobilePlatform, ApplicationVersionEntity applicationVersion, AccountEntity account) {
        this.notificationId = notificationId;
        this.token = token;
        this.imei = imei;
        this.ip = ip;
        this.expired = expired;
        this.logout = logout;
        this.logoutDate = logoutDate;
        this.uninstall = uninstall;
        this.uninstallDate = uninstallDate;
        this.mobilePlatform = mobilePlatform;
        this.applicationVersion = applicationVersion;
        this.account = account;
    }

    public CustomerLoginEntity(VerifyRequest verifyRequest,
                               ApplicationVersionEntity applicationVersion,
                               MobilePlatformEntity mobilePlatform,
                               String token,
                               AccountEntity account) {

        this.notificationId = verifyRequest.getNotificationId();
        this.imei = verifyRequest.getImei();

        this.token = token;

        this.applicationVersion = applicationVersion;
        this.mobilePlatform = mobilePlatform;

        this.account = account;

    }

    @Basic
    public String getNotificationId() {
        return notificationId;
    }
    public void setNotificationId( String notificationId ) {
        this.notificationId = notificationId;
    }


    @Basic
    public String getToken() {
        return token;
    }
    public void setToken( String token ) {
        this.token = token;
    }

    @Basic
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }


    @Basic
    public String getImei() {
        return imei;
    }
    public void setImei( String imei ) {
        this.imei = imei;
    }


    @Basic
    @Column(columnDefinition = "boolean default false")
    public boolean isExpired() {
        return expired;
    }
    public void setExpired( boolean expired ) {
        this.expired = expired;
    }


    @Basic
    @Column(columnDefinition = "boolean default false")
    public boolean isLogout() {
        return logout;
    }
    public void setLogout( boolean logout ) {
        this.logout = logout;
    }


    @Basic
    public Date getLogoutDate() {
        return logoutDate;
    }
    public void setLogoutDate( Date logoutDate ) {
        this.logoutDate = logoutDate;
    }


    @Basic
    @Column(columnDefinition = "boolean default false")
    public boolean isUninstall() {
        return uninstall;
    }
    public void setUninstall( boolean uninstall ) {
        this.uninstall = uninstall;
    }


    @Basic
    public Date getUninstallDate() {
        return uninstallDate;
    }
    public void setUninstallDate( Date uninstallDate ) {
        this.uninstallDate = uninstallDate;
    }


    @ManyToOne
    @JoinColumn(name = "mobile_platform_id")
    public MobilePlatformEntity getMobilePlatform() {
        return mobilePlatform;
    }
    public void setMobilePlatform( MobilePlatformEntity mobilePlatform ) {
        this.mobilePlatform = mobilePlatform;
    }


    @ManyToOne
    @JoinColumn(name = "application_version_id")
    public ApplicationVersionEntity getApplicationVersion() {
        return applicationVersion;
    }
    public void setApplicationVersion( ApplicationVersionEntity applicationVersion ) {
        this.applicationVersion = applicationVersion;
    }


    @ManyToOne
    @JoinColumn(name = "account_id")
    public AccountEntity getAccount () {
        return account;
    }
    public void setAccount ( AccountEntity account ) {
        this.account = account;
    }



}
