package com.behrouz.server.base.components;

import com.behrouz.server.base.configuration.MethodLoaderConfiguration;
import com.behrouz.server.base.exception.*;
import com.behrouz.server.base.model.ApiActionMethod;
import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.base.ApiRequestBody;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.*;
import com.behrouz.server.redis.RedisToken;
import com.behrouz.server.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

;

/**
 * created by: Hapi
 **/


@Component
public class RequestDistributor {

    /**
     * show the logs
     */
    private static final Logger logger = Logger.getLogger( RequestDistributor.class.getSimpleName() );

    /**
     * method loader configuration
     */
    @Autowired
    private MethodLoaderConfiguration methodLoaderConfiguration;

    /**
     * api action invoker for run special method by special parameter
     */
    @Autowired
    private ApiActionInvoker apiActionInvoker;


    /**
     * redis holder token
     */
    @Autowired
    private RedisToken redisToken;



    /**
     * distribute the request body between api action methods
     * @param apiRequestBody the param getValue tha send to method
     * @return the api response for request
     */
    public ApiResponseBody distribute(ApiRequestBody apiRequestBody) throws InvocationTargetException, IllegalAccessException, ApiActionException {

        //request param is null
        if(apiRequestBody == null){
            throw new ApiActionBadRequestException();
        }


        //request param has not any action
        if( StringUtil.isNullOrEmpty(apiRequestBody.getAction())){
            throw new ApiActionUnknownActionException();
        }


        ApiActionMethod apiActionMethod =
                getMethod( apiRequestBody.getAction(), apiRequestBody.getVersion() );

        //action not found
        if(apiActionMethod == null){
            throw new ApiActionUnknownActionException();
        }


        // TODO: 3/16/18 check the subsystem is validate or not
        checkMethodAnnotations( apiActionMethod , apiRequestBody );


        return apiActionInvoker.invoke(apiActionMethod.getMethod() , apiRequestBody);

    }


    /**
     * find the method fby special action
     * @param action the action name
     * @return if found method return the reflation method o.w return null
     */
    private ApiActionMethod getMethod(String action , int version) throws ApiActionFailureException, ApiActionUnknownActionException, ApiActionWrongDataException {



        try {
            List < ApiActionMethod > methods =
                    methodLoaderConfiguration.getApiActionMethods().getOrDefault( action, null );
            if(methods == null || methods.isEmpty() ){
                throw new ApiActionUnknownActionException();
            }

            return findFirstMethodByVersion(methods , version);


        } catch (URISyntaxException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            throw new ApiActionFailureException();
        }
    }


    /**
     * find first method by the version
     * if find some method should send the best
     * best order:
     *      -the method without {@linkplain ApiAction.Version#ALL}
     *      -the package name lexicographical
     *      -the method write top than other method in class
     *
     * @param methods
     * @param versionCode
     * @return
     */
    private ApiActionMethod findFirstMethodByVersion(List < ApiActionMethod > methods , int versionCode) throws ApiActionWrongDataException {
        List<ApiActionMethod> methodsByVersion = new ArrayList <>(  );


        ApiAction.Version version = ApiAction.Version.getByVersion( versionCode );

        methodsByVersion.addAll( methodByVersion(methods , version) );
        methodsByVersion.addAll( methodByVersion(methods , ApiAction.Version.ALL) );



        return methodsByVersion.get( 0 );
    }


    /**
     * get ApiActionMethod by special version
     * @param methods the methods
     * @param version the version
     * @return list of methods contains the version
     * methods contains {@linkplain ApiAction.Version}
     */
    private Collection < ? extends ApiActionMethod > methodByVersion(List < ApiActionMethod > methods, ApiAction.Version version) {

        List<ApiActionMethod> methodList =
                new ArrayList <>(  );

        for(ApiActionMethod method : methods){
            List < ApiAction.Version > versions =
                    Arrays.asList( method.getApiAction().versions() );

            if( versions.contains( version)){
                methodList.add(  method );
            }

        }

        return methodList;
    }


    /**
     * check the method has param by annotation api action param
     * and execute the parameter accept null
     * @param method the method
     * @param apiRequestBody apiRequest.param used
     * @throws ApiActionParamException the exception should handle in parent method
     */
    private void checkMethodAnnotations(ApiActionMethod method , ApiRequestBody apiRequestBody) throws ApiActionParamException, ApiActionSecurityException {

        checkApiActionParam(method , apiRequestBody);

        checkTokenRequired(method , apiRequestBody);

        checkTokenRegistered(method , apiRequestBody);



    }




    /**
     * token requirement check
     * for some method same as register method the token is not required
     * @param method the method check
     * @param apiRequestBody the annotation
     */
    private void checkTokenRequired(ApiActionMethod method, ApiRequestBody apiRequestBody) throws ApiActionSecurityException {

        if(method.getApiAction().tokenRequired()){
           if(StringUtil.isNullOrEmpty( apiRequestBody.getToken() ) || !isValidateToken(apiRequestBody)){
               logger.warning( "api action required token but the token is not validate!" );
               throw new ApiActionSecurityException();
           }
        }
    }


    /**
     * token requirement check registered or is secondary token
     * for some method same as register method the token is not required
     * @param method the method check
     * @param apiRequestBody the annotation
     */
    private void checkTokenRegistered(ApiActionMethod method, ApiRequestBody apiRequestBody) throws ApiActionSecurityException {

        if(method.getApiAction().tokenRequired() && method.getApiAction().forceRegisteredToken()){
            if(redisToken.isRegistration(new ApiActionRequest( apiRequestBody ))){
                logger.warning( "api action required registered token but the token is not registered!" );
                throw new ApiActionSecurityException();
            }
        }
    }


    /**
     * check the token is valid or no
     * @param requestBody the requestBody
     * @return validate ? true : false
     */
    private boolean isValidateToken(ApiRequestBody requestBody) {
        return redisToken.isAuthenticated( new ApiActionRequest( requestBody ) );
    }


    /**
     * check the {@linkplain ApiActionParam} for method parameter
     * @param method the method
     * @param apiRequestBody the apiRequestBody
     * @throws ApiActionParamException parameter can't be null but the null object pass to method
     */
    private void checkApiActionParam(ApiActionMethod method , ApiRequestBody apiRequestBody)throws ApiActionParamException {

        if(method.getParameter() != null && method.getParameter().getApiActionParam() != null && !method.getParameter().getApiActionParam().nullable() && apiRequestBody.getParams() == null){
            throw new ApiActionParamException();
        }
    }
}
