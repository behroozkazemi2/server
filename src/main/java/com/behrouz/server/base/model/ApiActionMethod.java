package com.behrouz.server.base.model;


import com.behrouz.server.base.annotation.ApiAction;

import java.lang.reflect.Method;

/**
 * created by: Hapi
 **/


public class ApiActionMethod {

    private Method method;

    private ApiAction apiAction;

    private ApiActionParameter parameter;


    public ApiActionMethod(Method method) {

        this.method = method;

        this.apiAction = method.getAnnotation( ApiAction.class );

        if(method.getParameterCount() >= 1){
            if(method.getParameters()[0].getType() != int.class){
                parameter = new ApiActionParameter(method.getParameters()[0]);
            }else if(method.getParameterCount() > 1 && method.getParameters()[0].getType() != int.class) {
                parameter = new ApiActionParameter(method.getParameters()[1]);
            }
        }

    }


    public ApiActionMethod() {
    }


    public Method getMethod() {
        return method;
    }


    public ApiAction getApiAction() {
        return apiAction;
    }

    public ApiActionParameter getParameter() {
        return parameter;
    }
}
