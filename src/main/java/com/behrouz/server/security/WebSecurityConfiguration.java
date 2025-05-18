package com.behrouz.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.security
 * Project Koala Server
 * 09 September 2018 13:29
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
        implements AuthenticationSuccessHandler {


    @Autowired
    private AuthenticationProvider authProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/files/**", // api
                        "/api", // api
                        "/api/**", //api
                        "/pay/**", //pay
                        "/api/simple", // api just run in debug mode
                        "/api/simple/**",// api just run in debug mode,
                        "/test/**",
                        "/site;/**",
                        "/payment/melli/**"// payment method

                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().
                disable();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    }



    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

    }






}