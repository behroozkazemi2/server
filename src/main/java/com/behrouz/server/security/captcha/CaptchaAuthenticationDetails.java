package com.behrouz.server.security.captcha;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.security.captcha
 * Project Koala Server
 * 09 September 2018 13:33
 **/
public class CaptchaAuthenticationDetails {

    public static final String CAPTCHA_PARAMETER_NAME = "captcha 8TagMarket panel";

    private String answer = "answer";
    private String captcha = "captcha";

    CaptchaAuthenticationDetails(HttpServletRequest request) {
        this.answer = request.getParameter("captcha");
        this.captcha = (String) WebUtils.getSessionAttribute(request, CAPTCHA_PARAMETER_NAME);
    }

    CaptchaAuthenticationDetails(boolean isAutoLogin) {
        if(isAutoLogin)
            answer = captcha = "auto_login";
    }

    public boolean isCorrect() {
        return answer != null && captcha != null && answer.toLowerCase().equals(captcha.toLowerCase());
    }
}
