package com.behrouz.server.security.captcha;

import org.springframework.security.authentication.AuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.security.captcha
 * Project Koala Server
 * 09 September 2018 13:35
 **/
public class CaptchaAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, CaptchaAuthenticationDetails> {

    @Override
    public CaptchaAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new CaptchaAuthenticationDetails(context);
    }
}
