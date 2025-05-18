package com.behrouz.server.redis;

import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.model.account.OperatorEntity;
import com.behrouz.server.redis.core.RedisBase;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.redis
 * project name:  ximaServer
 * 13 July 2018
 **/

    /* * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * *
     * * * *                               * * * *
     * * * *                               * * * *
     * * * *         Redis Token           * * * *
     * * * *                               * * * *
     * * * *                               * * * *
     * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * */

@Component
public class RedisToken extends RedisBase {

    /**
     * redis token saved:
     *      - company ids   (List of integers)  if not found value is null
     *      - user id       (Integer)           if not found value is 0
     *      - accounting id (Integer)           if not found value is 0
     *
     *
     * method :
     *      check authenticated     -> call {@link #isAuthenticated(ApiActionRequest)}
     *
     *      when user login         ->  call {@link #login(String, long, String, String, String, boolean)}
     *
     *      when user logout        ->  call {@link #logout(ApiActionRequest)}
     *
     *      need to ids(bellow)     ->  call {@link #getAccountId(ApiActionRequest)}
     *          user_id
     *          driver_id
     *          owner_id
     *          person_id
     *          operator_id
     *          inspector_id
     *
     *
     *
     */


    /*
     * accounting key
     */
    private static final String ACCOUNT_ID       = "accountId";


    /*
     *  uuid
     */
    private static final String UUID =  "uuid";


    /*
     *  notification id
     */
    private static final String NOTIFICATION_ID =  "notificationId";

    /*
     * provider id
     */
    private static final String PROVIDER_ID =  "provider";


    /*
     *  the prefix key for use in generate key
     */
    private static final String REDIS_TOKEN_PREFIX =  "token";


    /*
     *  registration is active or deactivated
     */
    private static final String REGISTRATION        =   "registration";

    /*
     *  mobile
     */
    private static final String MOBILE              =   "mobile";


    /*
     * redis time out key expire in second
     * expire time is: 60 day * 24 hours * 60 minute * 60 second (2 month)
     */
    private static final long REDIS_TOKEN_EXPIRE_TIME = 2 * 30 * 24 * 60 * 60;


    private static final long REDIS_TOKEN_PANEL_EXPIRE_TIME = 3 * 24 * 60 * 60;


    /**
     * check the user authenticated
     * @param request the api action request params
     * @return user exist ? true : false
     */
    public boolean isAuthenticated(ApiActionRequest request){
        return getSync().hexists( getKey( request ) , ACCOUNT_ID );
    }


    /**
     * get user id from api action request
     * @param request the api action request
     * @return exist ? user's id : 0
     */
    public int getAccountId(ApiActionRequest request) {

        String value = getSync().hget(getKey(request), ACCOUNT_ID);

        if (value == null) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    /**
     * get provider id from api action request
     * @param request the api action request
     * @return exist ? provider's id : -1
     */
    public int getProviderId(ApiActionRequest request) {

        String value = getSync().hget(getKey(request), PROVIDER_ID);

        if (value == null) {
            return -1;
        }
        return Integer.parseInt(value);
    }


    /**
     * get uuid from api action request
     * @param request the api action request
     * @return exist ? accounting's id : 0
     */
    public String getUUID(ApiActionRequest request) {
        return getSync().hget(getKey(request), UUID);
    }


    /**
     * get firebase notification id from api action request
     * @param request the api action request
     * @return exist ? accounting's id : 0
     */
    public String getNotificationId(ApiActionRequest request) {
        return getSync().hget(getKey(request), NOTIFICATION_ID);
    }


    /**
     * get firebase notification id from api action request
     * @param token api token
     * @return user registration = true ? force to register
     */
    public boolean isRegistration(ApiActionRequest request) {
        String val = getSync().hget(getKey(request), REGISTRATION);
        return Boolean.parseBoolean(val);
    }



    /**
     * delete all redis token key save into redis
     * @param request the api action request
     */
    public void logout(ApiActionRequest request) {

        String key = getKey( request );

        getSync().hdel( key , ACCOUNT_ID );
        getSync().hdel( key , UUID );
        getSync().hdel( key , NOTIFICATION_ID );

    }


    /**
     * get user id from api action request
     */
    public LocalDateTime login(String token , long accountId, String mobile, String notificationToken, String uuid, boolean registration) {

        String key = getKey( token );

        getSync().hset( key , ACCOUNT_ID        , String.valueOf( accountId ) );
        getSync().hset( key , MOBILE            , mobile );
        getSync().hset( key , UUID              , uuid );
        getSync().hset( key , NOTIFICATION_ID   , notificationToken );
        getSync().hset( key , REGISTRATION      , String.valueOf( registration ) );

        getSync().expire( key ,  getExpireTime());

        return LocalDateTime.now().plusSeconds(getExpireTime());

    }

    /**
     * get user id from api action request
     * @param operator the OperatorEntity
     */
    public void login(OperatorEntity operator, String token) {

        String key = getKey( token );

        getSync().hset( key , ACCOUNT_ID  , String.valueOf( operator.getId() ));
        getSync().hset( key , PROVIDER_ID , String.valueOf( operator.getProviderId() ));

        getSync().expire( key ,  getExpirePanel());

    }


    /**
     * get firebase notification id from api action request
     * @param apiActionRequest api action request
     * @return mobile
     */
    public String getMobile(ApiActionRequest apiActionRequest) {
        return getSync().hget(getKey(apiActionRequest), MOBILE);
    }



    /**
     * generate the key for redis key
     * use from prefix {@link #REDIS_TOKEN_PREFIX}
     * @param apiActionRequest the api action use from it's token
     * @return
     */
    private String getKey(ApiActionRequest apiActionRequest){
        return String.format( "ks-%s:%s",getPrefixKey() , apiActionRequest.getToken() );
    }

    /**
     * generate the key for redis key
     * use from prefix {@link #REDIS_TOKEN_PREFIX}
     * @param token token
     * @return
     */
    private String getKey(String token){
        return String.format( "ks-%s:%s",getPrefixKey() , token );
    }


    @Override
    protected String getPrefixKey() {
        return REDIS_TOKEN_PREFIX;
    }


    @Override
    protected long getExpireTime() {
        return REDIS_TOKEN_EXPIRE_TIME;
    }

    protected long getExpirePanel(){
        return REDIS_TOKEN_PANEL_EXPIRE_TIME;
    }
}
