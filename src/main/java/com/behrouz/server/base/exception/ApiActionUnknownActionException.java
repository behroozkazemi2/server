package com.behrouz.server.base.exception;


/**
 * created by: Hapi
 **/


import com.behrouz.server.base.HttpCode;

/**
 * unknown action received
 */
public class ApiActionUnknownActionException extends ApiActionException {

    public ApiActionUnknownActionException(){
        super( HttpCode.REQUEST_REJECT , "action not found");
    }


}
