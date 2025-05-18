package com.behrouz.server.security.captcha;

import com.behrouz.server.utils.CaptchaImage;
import org.springframework.web.util.WebUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.security.captcha
 * Project Koala Server
 * 09 September 2018 13:35
 **/
public class CaptchaUtil {

    /**
     * create the captcha image
     * set the captcha string into servlet request
     * @param request the servlet request
     * @return byte array
     * @throws IOException handle the exception
     */
    public static byte[] createImage(final HttpServletRequest request) throws IOException {
        CaptchaImage obj = new CaptchaImage();
        BufferedImage image = obj.getCaptchaImage();
        String captchaStr = obj.getCaptchaString();

        WebUtils.setSessionAttribute(
                request,
                CaptchaAuthenticationDetails.CAPTCHA_PARAMETER_NAME,
                captchaStr
        );

        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

        ImageIO.write(image, "jpeg", jpegOutputStream);

        return jpegOutputStream.toByteArray();
    }

    public static String getSavedCaptcha(HttpServletRequest httpRequest) {



        Object captcha = WebUtils.getSessionAttribute(
                httpRequest,
                CaptchaAuthenticationDetails.CAPTCHA_PARAMETER_NAME
        );

        if(! (captcha instanceof String) ){
            return null;
        }else{
            return (String) captcha;
        }
    }

}
