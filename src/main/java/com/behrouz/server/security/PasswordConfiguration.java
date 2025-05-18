package com.behrouz.server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.security.components
 * project name:  ximaServer
 * 11 July 2018
 **/


@Configuration
public class PasswordConfiguration {



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
