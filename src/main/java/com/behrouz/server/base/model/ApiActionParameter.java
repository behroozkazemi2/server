package com.behrouz.server.base.model;


import com.behrouz.server.base.annotation.ApiActionParam;

import java.lang.reflect.Parameter;

/**
 * created by: Hapi
 **/


public class ApiActionParameter {

    private Parameter parameter;

    private ApiActionParam apiActionParam;


    public ApiActionParameter(Parameter parameter) {

        this.parameter = parameter;

        if( parameter.isAnnotationPresent(ApiActionParam.class)){
            this.apiActionParam = parameter.getAnnotation( ApiActionParam.class );
        }
    }


    public Parameter getParameter() {
        return parameter;
    }
    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    public ApiActionParam getApiActionParam() {
        return apiActionParam;
    }
    public void setApiActionParam(ApiActionParam apiActionParam) {
        this.apiActionParam = apiActionParam;
    }
}


