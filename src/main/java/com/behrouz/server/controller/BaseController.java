package com.behrouz.server.controller;

import com.behrouz.server.rest.constant.AjaxResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.controller
 * Project Koala Server
 * 09 September 2018 17:22
 **/

@Controller
public class BaseController {


    private static final Logger LOGGER = Logger.getLogger( BaseController.class.getSimpleName() );


    protected void loggerShowWarning ( String message, Object... obj ) {
        LOGGER.warning( String.format( message + "\n", obj ) );
    }


    protected void loggerShowWarning ( String message, String controllerName ) {
        Logger.getLogger( controllerName ).warning( String.format( message + "\n" ) );
    }


    protected ResponseEntity showError ( BindingResult bindingResult ) {
        List < ObjectError > errors = bindingResult.getAllErrors();
        String errorMsg = "";
        for ( ObjectError error : errors ) {
            errorMsg += error.getDefaultMessage() + "<br/>";
        }
        String finalFlashMessage = errorMsg;
        return new ResponseEntity <>( new AjaxResponse() {{
            setResult( false );
            setPayload( finalFlashMessage );
        }}, HttpStatus.OK );
    }


}