package com.behrouz.server.base.components;

import com.behrouz.server.base.exception.ApiActionBadRequestException;
import com.behrouz.server.base.exception.ApiActionEncryptionException;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionFailureException;
import com.behrouz.server.base.util.RequestWrapper;
import com.behrouz.server.base.util.XimaServerEncryption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.behrouz.server.base.ApiRequestBody;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.HttpCode;
import com.behrouz.server.values.DebugValues;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * created by: Hapi
 **/

@Component
public class ApiHandlerInterceptor implements HandlerInterceptor {


    /**
     * logger show warn and information
     */
    private final static Logger log = Logger.getLogger( ApiHandlerInterceptor.class.getSimpleName() );

    /**
     * end pint of debug without encryption
     * sample request:
     *  {"action":"app.inspector.verify","params":{"code":"99100","mobile":"09305109961"},"subsystem":2,"version":1}
     */
    private static final String SIMPLE_API_PATH = "/api/simple";

    /**
     * api for clients
     * received request in encrypted mode
     */
    private static final String API_PATH = "/api";

    /**
     * object mapper convert :
     *      decrypt data to request -> (api request body)
     *      encrypt response to encrypted string <- (api response body)
     */
    private final static ObjectMapper MAPPER = new ObjectMapper(  );


    /**
     * the component distribute request into api action
     * check the action and param sub system (todo)
     * invoke the request into action method
     * final for send always for crating class
     */
    private final RequestDistributor requestDistributor;


    /**
     * the constructor of adapter
     * @param requestDistributor the autowired request distributor should be send
     */
    public ApiHandlerInterceptor(RequestDistributor requestDistributor){

        this.requestDistributor = requestDistributor;
    }


    /**
     * method get request before received to controller
     * @param request the received request
     * @param response response of request
     * @return the true if pass the life cycle after method to controller
     * false if method end
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){


        if ( DebugValues.DELAY_RESPONSE ){

            try {
                TimeUnit.SECONDS.sleep((int) (Math.random() * 3) );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        String decryptedRequest     = null;
        String decryptedResponse    = null;

        String requestString    ;
        String responseString   = null;

        int    responseCode     = HttpCode.OK.getValue();

        try {
            requestString = new RequestWrapper( request ).getBody();

            //get path
            String path = request.getRequestURI();

            ApiResponseBody apiResponse = null;

            switch ( path ) {
                case API_PATH:
                    requestString = decryptBody( requestString );
                    decryptedRequest = requestString;
                    try {
                        apiResponse = requestHandle( requestString );
                    }catch (InvocationTargetException e) {
                        invocationExceptionOccurred(e);
                    }
                    break;
                case SIMPLE_API_PATH:
                    decryptedRequest = requestString;
                    if ( DebugValues.DEBUG_MODE ) {
                        try {
                            apiResponse = requestHandle( requestString );
                        }catch (InvocationTargetException e) {
                            invocationExceptionOccurred(e);
                        }
                    } else {
                        throw new ApiActionFailureException();
                    }
                    break;
                default:
                    throw new ApiActionFailureException();
            }


            decryptedResponse =
                    stringFormatResponse( apiResponse );

            responseString =
                    encryptResponse( decryptedResponse, request.getRequestURI().equals( API_PATH ) );


        } catch (ApiActionException e) {

            ApiResponseBody apiResponse = ApiResponseBody.generateResponse( e.getHttpCode() , e.getDescription() );

            try {
                decryptedResponse =
                        stringFormatResponse( apiResponse );

                responseString =
                        encryptResponse( decryptedResponse, request.getRequestURI().equals( API_PATH ) );

            } catch (ApiActionException e1) {
                responseCode = HttpCode.INTERNAL_SERVER_ERROR.getValue();

            }


        } catch (Exception e) {
            e.printStackTrace();
            responseCode = HttpCode.INTERNAL_SERVER_ERROR.getValue();
        } finally {

            response.setStatus( responseCode );
            response.setCharacterEncoding( "UTF-8" );
            try {
                response.getWriter().write( responseString != null ? responseString : "");
            } catch (IOException e) {
                e.printStackTrace();

            }

        }


        saveLog( decryptedRequest  , decryptedResponse);
        return true;
    }

    private void invocationExceptionOccurred(InvocationTargetException e) throws ApiActionException {


        if(e.getTargetException() instanceof ApiActionException) {
            ApiActionException code = (ApiActionException) e.getTargetException();
            System.err.printf("\n\t\t\tApi Action Result: %s\n",code.getHttpCode().toString());
            throw code;

        }else{
            e.printStackTrace();
            throw new ApiActionException( HttpCode.INTERNAL_SERVER_ERROR );
        }
    }


    /**
     * encrypt api response into encrypted string
     * @param apiResponse the api response
     * @return the string contains response in string mode
     * @apiNote userEncryption = false just use in debug mode
     */
    private String stringFormatResponse(ApiResponseBody apiResponse) throws ApiActionFailureException {
        try {
            return MAPPER.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ApiActionFailureException();
        }

    }



    /**
     * encrypt api response into encrypted string
     * @param apiResponseString the api response in string format
     * @param useEncryption use encryption
     * @return the string contains response in string mode
     * @apiNote userEncryption = false just use in debug mode
     */
    private String encryptResponse(String apiResponseString, boolean useEncryption) throws ApiActionEncryptionException {


        if (useEncryption) {
            try {
                return new XimaServerEncryption().encrypt(apiResponseString);
            } catch (NoSuchAlgorithmException e) {
                throw new ApiActionEncryptionException();
            }
        }else{
            return apiResponseString;
        }

    }





    /**
     * this method check the received body can be decrypt or not
     * @param body the encrypted body
     * @return observed to encryption (have a same key between server and client) ? decrypted body : null
     */
    private String decryptBody(final String body) throws ApiActionEncryptionException {
        try {
            return new XimaServerEncryption().decrypt(body);
        } catch (Exception e) {
            log.warning( "bad encrypted received!" );
            throw new ApiActionEncryptionException();
        }
    }


    /**
     * the String body should change into ApiRequestBody
     * request distribute into api action method
     * response set for request
     * @param body the body in json format
     * @return the api response received
     */
    private ApiResponseBody requestHandle(String body) throws ApiActionException, InvocationTargetException, IllegalAccessException {
        ApiRequestBody requestBody;
        try {
             requestBody =
                     MAPPER.readValue( body , ApiRequestBody.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiActionBadRequestException();
        }


        return requestDistributor.distribute( requestBody );


    }


    /**
     * save the log into database
     * the log variable has the string json format
     * @param requestString the request body json format
     * @param responseString the response body json format
     */
    private void saveLog(String requestString , String responseString){

        if(DebugValues.DEBUG_MODE || requestString == null || requestString.length() < DebugValues.LOG_LENGTH_BYTE) {
            log.warning( "request  body: " + requestString );
        }else{
            log.warning( "request  body has a large content" );
        }

        if(DebugValues.DEBUG_MODE || responseString == null || responseString.length() < DebugValues.LOG_LENGTH_BYTE) {
            log.warning( "response body: " + responseString );
        }else{
            log.warning( "response body has a large content" );
        }

    }





}
