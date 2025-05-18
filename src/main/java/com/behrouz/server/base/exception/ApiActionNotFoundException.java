package com.behrouz.server.base.exception;


import com.behrouz.server.base.HttpCode;

/**
 * created by: Hapi
 **/


public class ApiActionNotFoundException extends ApiActionException {


    public ApiActionNotFoundException() {
        super(HttpCode.REQUEST_REJECT);
    }

    public ApiActionNotFoundException(String description) {
        super(HttpCode.REQUEST_REJECT , description);
    }

}
