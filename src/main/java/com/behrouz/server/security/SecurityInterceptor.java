package com.behrouz.server.security;

import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.security
 * project name:  ximaServer
 * 11 July 2018
 **/


@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

//    @Autowired
//    private OperatorPackageRoleRepository operatorPackageRoleRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null)
        {
            SecurityExpression securityExpression = getSecurityExpression();
            if (securityExpression != null) {
                modelAndView.getModel().put("sec", securityExpression);
            }
        }
    }

    public SecurityExpression getSecurityExpression() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityExpression securityExpression = new SecurityExpression(authentication);
//            securityExpression.setOperatorPackageRoleRepository(operatorPackageRoleRepository);
            securityExpression.setTrustResolver(new AuthenticationTrustResolverImpl());
            return securityExpression;
        }
        return null;
    }
}
