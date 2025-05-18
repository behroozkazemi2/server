package com.behrouz.server.base.exception;


import com.behrouz.server.base.HttpCode;

/**
 * created by: Hapi
 **/


public class ApiActionSecurityException extends ApiActionException{


    public ApiActionSecurityException(){
        super( HttpCode.NOT_ALLOW , "not allowed" );
    }

}
