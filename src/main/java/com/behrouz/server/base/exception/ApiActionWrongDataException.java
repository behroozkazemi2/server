package com.behrouz.server.base.exception;

import com.behrouz.server.base.HttpCode;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.driverApplication
 * Project ximaserver
 * 12 July 2018 11:53
 **/


public class ApiActionWrongDataException extends ApiActionException {

    public ApiActionWrongDataException() {
        super(HttpCode.REQUEST_REJECT , "data wrong");
    }

    public ApiActionWrongDataException(String description) {
        super(HttpCode.REQUEST_REJECT , description);
    }
}
