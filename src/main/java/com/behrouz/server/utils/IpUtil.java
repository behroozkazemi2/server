package com.behrouz.server.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class IpUtil {

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};

    public static String getClientIpAddress() {
        Optional<HttpServletRequest> requestOptional = getCurrentHttpRequest();
        if(requestOptional.isPresent()) {
            for (String header : HEADERS_TO_TRY) {
                String ip = requestOptional.get().getHeader(header);
                if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                    return ip;
                }
            }

            return requestOptional.get().getRemoteAddr();
        }
        return null;
    }

    public static Optional<HttpServletRequest> getCurrentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest);
    }
}