package com.behrouz.server.base.exception;


import com.behrouz.server.base.HttpCode;

/**
 * created by: Hapi
 **/


public class ApiActionFailureException extends ApiActionException{

    public ApiActionFailureException() {
        super( HttpCode.INTERNAL_SERVER_ERROR , "internal server error");
    }

    public ApiActionFailureException(String description) {
        super( HttpCode.INTERNAL_SERVER_ERROR , description);
    }
}
