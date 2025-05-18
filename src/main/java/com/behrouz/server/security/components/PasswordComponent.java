package com.behrouz.server.security.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.security.components
 * project name:  ximaServer
 * 11 July 2018
 **/



@Component
public class PasswordComponent {

    /*
     * the encoder creator
     */
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * password match checker
     * @param password the password
     * @param hashPassword the hashed password
     * @return password and hash are matched ? true : false
     */
    public boolean passwordMatched(String password , String hashPassword){

        return passwordEncoder.matches( password ,  hashPassword);
    }


    /**
     * create the hash password from passowrd
     * @param password the password
     * @return the hashed password
     */
    public String getHashPassword(String password){

        return passwordEncoder.encode( password );
    }
}
