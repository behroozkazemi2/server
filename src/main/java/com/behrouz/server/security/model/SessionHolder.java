package com.behrouz.server.security.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import javax.servlet.http.HttpServletRequest;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.security.session.model
 * project name:  ximaServer
 * 11 July 2018
 **/


public class SessionHolder {

    


    public static boolean isLogin() {
        return 
                getOperatorSessionDetail() != null;
    }
    public static boolean isFirstLogin() {
        return getOperatorSessionDetail() != null && getOperatorSessionDetail().isFirstLogin();
    }


    public static long getOperatorId(){
        return getUserSessionDetail().getId();
    }

    
    public static String getFullName(){
        UserSessionDetail user = getUserSessionDetail();
        return String.format( "%s %s", user.getFirstName(),user.getLastName());
    }


    public static byte[] getAvatar(){
        return getUserSessionDetail().getAvatar();
    }





    public static void setFirstLogin(boolean firstLogin, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OperatorSessionDetail details =  (OperatorSessionDetail) token.getDetails();
        details.setFirstLogin(firstLogin);
        token.setDetails(details);
        SecurityContextHolder.getContext().setAuthentication(token);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }







    private static UserSessionDetail getUserSessionDetail(){
        OperatorSessionDetail operator
                = getOperatorSessionDetail();
        if(operator != null){
            return operator.getUser();
        }else{
            return null;
        }
    }


    private static OperatorSessionDetail getOperatorSessionDetail() {
        if(SecurityContextHolder.getContext().getAuthentication() == null){
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !(authentication.getDetails() instanceof OperatorSessionDetail) ) {
            return null;
        }else {

            return ( (OperatorSessionDetail) authentication.getDetails() );
        }
    }




    public static void updateOperatorAvatar(byte[] avatar, HttpServletRequest request) {
        UserSessionDetail user = getOperatorSessionDetail().getUser();
        user.setAvatar(avatar);


        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        OperatorSessionDetail details =  (OperatorSessionDetail) token.getDetails();
        details.setUser(user);
        token.setDetails(details);
        SecurityContextHolder.getContext().setAuthentication(token);
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

    }

}


