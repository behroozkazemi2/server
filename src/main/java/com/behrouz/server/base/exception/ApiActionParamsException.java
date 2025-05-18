package com.behrouz.server.base.exception;


/**
 * created by: Hapi
 **/


import com.behrouz.server.base.HttpCode;

/**
 * extra param seen from api action method
 * when method has more than 2 params and param invalid
 */
public class ApiActionParamsException extends ApiActionException {

    public ApiActionParamsException(){
        super( HttpCode.REQUEST_REJECT , "invalid parameter" );
    }


}
