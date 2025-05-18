package com.behrouz.server.base.exception;


/**
 * created by: Hapi
 **/


import com.behrouz.server.base.HttpCode;

/**
 * received bad request
 * param not found or
 * param format is not in api action param format
 */
public class ApiActionBadRequestException extends ApiActionException {

    public ApiActionBadRequestException(){
        super( HttpCode.REQUEST_REJECT , "‌bad parameter");
    }


}
