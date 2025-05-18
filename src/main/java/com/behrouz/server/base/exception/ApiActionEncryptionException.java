package com.behrouz.server.base.exception;


import com.behrouz.server.base.HttpCode;

/**
 * created by: Hapi
 **/


public class ApiActionEncryptionException extends ApiActionException{

    public ApiActionEncryptionException() {
        super( HttpCode.REQUEST_REJECT , "‌bad encryption, check private and public key");
    }
}
