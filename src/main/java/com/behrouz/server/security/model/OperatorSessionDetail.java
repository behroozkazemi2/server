package com.behrouz.server.security.model;

import java.io.Serializable;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.security.session
 * project name:  ximaServer
 * 11 July 2018
 **/


public class OperatorSessionDetail implements Serializable {


    private UserSessionDetail user;


    private boolean firstLogin;


    public UserSessionDetail getUser() {
        return user;
    }
    public void setUser(UserSessionDetail user) {
        this.user = user;
    }



    public boolean isFirstLogin() {
        return firstLogin;
    }
    public void setFirstLogin(boolean firstLogin) {
        this.firstLogin = firstLogin;
    }
}
