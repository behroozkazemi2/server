package com.behrouz.server.base.exception;


/**
 * created by: Hapi
 **/


import com.behrouz.server.base.HttpCode;

/**
 * null point param send to action
 * ApiActionParam when nullable = false and send null param to method
 * this exception throw
 */
public class ApiActionParamException extends ApiActionException {

    public ApiActionParamException(){
        super( HttpCode.REQUEST_REJECT , "bad parameter");
    }


}
