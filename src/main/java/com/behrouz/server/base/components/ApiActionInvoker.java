package com.behrouz.server.base.components;

import com.behrouz.server.base.exception.ApiActionParamsException;
import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.base.ApiRequestBody;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.redis.RedisToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * created by: Hapi
 **/


@Component
public class ApiActionInvoker {



    /**
     * user for create object and autowire the object
     * for run method
     */
    private final AutowireCapableBeanFactory autowireCapableBeanFactory;

    /**
     * redis holder token
     */
    private final RedisToken redisToken;

    private static final Logger log = Logger.getLogger( ApiActionInvoker.class.getSimpleName() );





    @Autowired
    public ApiActionInvoker(AutowireCapableBeanFactory autowireCapableBeanFactory, RedisToken redisToken ) {
        this.autowireCapableBeanFactory = autowireCapableBeanFactory;
        this.redisToken = redisToken;
    }

    /**
     * invoke method and distribute between invokeMethod
     * types:
     *      method has not any params
     *      method method has a param contains subsystem
     *      method method has a param contains request parameter object
     *      method method has two parameter request parameter and subsystem
     *      any other return failure message
     * @param method the method for execute
     * @param requestBody the request body
     * @return the executed result (contains http code and parameter (if result is ok)
     */
    public ApiResponseBody invoke(Method method, ApiRequestBody requestBody) throws InvocationTargetException, IllegalAccessException, ApiActionParamsException {



        switch ( method.getParameterCount() ){

            //method has not any params
            case 0:

                return invokeMethod(method);

            case 1:

                //method method has a param contains subsystem
                if(method.getParameterTypes()[0] == ApiActionRequest.class){
                    return invokeMethod( method , new ApiActionRequest( requestBody ) );

                }

                //method method has a param user's id
                else if(method.getParameterTypes()[0] == int.class || method.getParameterTypes()[0] == long.class){
                    return invokeMethod( method , getUserId( requestBody ) );
                }

                //method method has a param contains request parameter object
                else {
                    return invokeMethod( method , requestBody.getParams(method.getParameterTypes()[0]) );
                }


            case 2:
                //method method has two parameter request parameter and subsystem
                return invokeMethod( method , requestBody );

            case 3:

                //method method has three parameter request parameter and subsystem
                return invokeMethod( method , requestBody , new ApiActionRequest( requestBody ) );

            default:
                //any other return failure message
                log.warning( "method parameter not valid maybe contains 3 or more parameter..." );
                throw new ApiActionParamsException();

        }
    }




    /**
     * other type of method with 2 parameter
     * parameter is ApiRequestBody
     * should distribute between method
     * parameters is : [ user id or api action param , parameter ]
     * the method type:
     *      the first parameter ApiActionRequest , the second parameter user's id
     *      the first parameter user's id        , the second parameter ApiActionRequest
     *      the first parameter RequestBodyParam , the second parameter ApiActionRequest
     *      the first parameter RequestBodyParam , the second parameter user's id
     *      any other type is invalid (not define type)
     * @param method the action method
     * @return the api response after execute if type is invalid return failure response
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method, ApiRequestBody requestBody) throws InvocationTargetException, IllegalAccessException {


        // the first parameter ApiActionRequest , the second parameter user's id
        if(method.getParameterTypes()[ 0 ] == ApiActionRequest.class  && (method.getParameterTypes()[1] == int.class || method.getParameterTypes()[1] == long.class)){
            return invokeMethod(  method, new ApiActionRequest( requestBody ), getUserId( requestBody )  );
        }

        // the first parameter user's id , the second parameter ApiActionRequest
        else if((method.getParameterTypes()[0] == int.class || method.getParameterTypes()[0] == long.class)  && method.getParameterTypes()[ 1 ] == ApiActionRequest.class){
            return invokeMethod(  method, getUserId( requestBody ), new ApiActionRequest( requestBody ) );
        }

        // the first parameter RequestBodyParam , the second parameter ApiActionRequest
        else if(method.getParameterTypes()[ 1 ] == ApiActionRequest.class ){
            return invokeMethod(method, requestBody.getParams( method.getParameterTypes()[ 0 ] ), new ApiActionRequest( requestBody ));
        }

        // the first parameter RequestBodyParam , the second parameter user's id
        else if((method.getParameterTypes()[1] == int.class || method.getParameterTypes()[1] == long.class)){
            return invokeMethod(method, requestBody.getParams( method.getParameterTypes()[ 0 ] ), getUserId(requestBody));
        }

        // invalidate type
        else{
            //any other type is invalid (not define type)
            log.warning( "the method parameters type is not validate check the method parameters." );
            return new ApiResponseBody().failure();
        }
    }


    /**
     * other type of method with 3 parameter
     * parameter is ApiRequestBody
     * should distribute between method
     * parameters is : [ userId , api action request , parameter ]
     * the method type:
     *      first parameter RequestBodyParam , second parameter ApiActionRequest  , third param user's id
     *      first parameter RequestBodyParam , second parameter user's id         , third param ApiActionRequest
     *      any other type is invalid (not define type)
     * @param method the method should be invoke
     * @param requestBody the request body client send
     * @param apiActionRequest the created api action request
     * @return the api response after execute if type is invalid return failure response
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method, ApiRequestBody requestBody, ApiActionRequest apiActionRequest) throws InvocationTargetException, IllegalAccessException  {


        // first parameter RequestBodyParam , second parameter ApiActionRequest  , third param user's id
        if(method.getParameterTypes()[ 1 ] == ApiActionRequest.class && method.getParameterTypes()[ 2 ] == int.class){
            return invokeMethod(method , requestBody.getParams( method.getParameterTypes()[ 0 ] ) , apiActionRequest , getUserId( requestBody ));
        }

        // first parameter RequestBodyParam , second parameter user's id  , third param ApiActionRequest
        else if(method.getParameterTypes()[ 1 ] == int.class && method.getParameterTypes()[ 2 ] == ApiActionRequest.class){
            return invokeMethod(method , requestBody.getParams( method.getParameterTypes()[ 0 ] )  , getUserId(requestBody) , apiActionRequest);
        }

        // invalidate type
        else{
            //any other type is invalid (not define type)
            log.warning( "the method parameters type is not validate check the method parameters." );
            return new ApiResponseBody().failure();
        }

    }





    /**
     * execute method without any parameter
     * @param method the action method
     * @return the api response after execute
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method) throws InvocationTargetException, IllegalAccessException {
        return (ApiResponseBody) method.invoke( getAutowiredObject( method ) );
    }



    /**
     * other type of method with 1 parameter
     * parameter is integer getValue and contains subsystem id
     * @param method the action method
     * @return the api response after execute
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method, ApiActionRequest apiActionRequest) throws InvocationTargetException, IllegalAccessException {
        return (ApiResponseBody) method.invoke( getAutowiredObject( method ) , apiActionRequest );
    }


    /**
     * other type of method with 1 parameter
     * parameter is integer getValue and contains subsystem id
     * @param method the action method
     * @return the api response after execute
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method, int userId) throws InvocationTargetException, IllegalAccessException {
        return (ApiResponseBody) method.invoke( getAutowiredObject( method ) , userId );
    }



    /**
     * other type of method with 1 parameter
     * parameter is object type
     * @param method the action method
     * @return the api response after execute
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method, Object param) throws InvocationTargetException, IllegalAccessException {

        return (ApiResponseBody) method.invoke( getAutowiredObject( method ) , param );
    }


    /**
     * other type of method with 2 parameter
     * call from invoke method that is execute and call
     * @param method the action method
     * @param apiActionRequest the api action param (contains token, uuid, subsystem, ...)
     * @param userId the user's id
     * @return the api response after execute
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method , ApiActionRequest apiActionRequest , int userId) throws InvocationTargetException, IllegalAccessException {
        return (ApiResponseBody) method.invoke( getAutowiredObject( method ), apiActionRequest, userId  );
    }



    /**
     * other type of method with 2 parameter
     * call from invoke method that is execute and call
     * @param method the action method
     * @param apiActionRequest the api action param (contains token, uuid, subsystem, ...)
     * @param userId the user's id
     * @return the api response after execute
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method, int userId, ApiActionRequest apiActionRequest ) throws InvocationTargetException, IllegalAccessException {
        return (ApiResponseBody) method.invoke( getAutowiredObject( method ), userId, apiActionRequest  );
    }



    /**
     * other type of method with 2 parameter
     * call from invoke method that is execute and call
     * @param method the action method
     * @param apiActionRequest the api action param (contains token, uuid, subsystem, ...)
     * @param param parameter
     * @return the api response after execute
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method , Object param, ApiActionRequest apiActionRequest) throws InvocationTargetException, IllegalAccessException {
        return (ApiResponseBody) method.invoke( getAutowiredObject( method ), param, apiActionRequest  );
    }


    /**
     * other type of method with 2 parameter
     * call from invoke method that is execute and call
     * @param method the action method
     * @param userId the user's id
     * @param param parameter
     * @return the api response after execute
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method , Object param, int userId) throws InvocationTargetException, IllegalAccessException {
        return (ApiResponseBody) method.invoke( getAutowiredObject( method ), param, userId  );
    }



    /**
     * other type of method with 2 parameter
     * call from invoke method that is execute and call
     * @param method the action method
     * @param param parameter
     * @param apiActionRequest the api action param (contains token, uuid, subsystem, ...)
     * @param userId parameter user id
     * @return the api response after execute
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method, Object param, ApiActionRequest apiActionRequest, int userId) throws InvocationTargetException, IllegalAccessException {
        return (ApiResponseBody) method.invoke( getAutowiredObject( method ), param, apiActionRequest, userId);
    }

    /**
     * other type of method with 2 parameter
     * call from invoke method that is execute and call
     * @param method the action method
     * @param param parameter
     * @param userId parameter user id
     * @param apiActionRequest the api action param (contains token, uuid, subsystem, ...)
     * @return the api response after execute
     * @apiNote exception handle in parent method
     */
    private ApiResponseBody invokeMethod(Method method, Object param, int userId, ApiActionRequest apiActionRequest) throws InvocationTargetException, IllegalAccessException {
        return (ApiResponseBody) method.invoke( getAutowiredObject( method ), param, userId, apiActionRequest );
    }




    /**
     * create the autowired object from method and declaring class
     * @param method the method for crated parent class (autowire)
     * @return the created object
     */
    private Object getAutowiredObject(Method method){
        return
                autowireCapableBeanFactory.createBean(method.getDeclaringClass());
    }


    /**
     * get user id from redis by key api action request
     * @param requestBody the request body
     * @return the user id (if not exist value is 0)
     */
    private int getUserId(ApiRequestBody requestBody) {
        return getAccountId( new ApiActionRequest( requestBody ) );
    }



    /**
     * get user id from redis by key api action request
     * @param requestBody the api action request body
     * @return the user id (if not exist value is 0)
     */
    private int getAccountId(ApiActionRequest requestBody) {
        return redisToken.getAccountId(  requestBody );
    }







}
