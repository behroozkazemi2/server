package com.behrouz.server.redis;

import com.behrouz.server.api.customer.request.RegisterRequest;
import com.behrouz.server.redis.core.RedisBase;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.redis
 * Project server
 * 16 September 2018 14:53
 **/
@Component
public class RedisImei extends RedisBase {

    private static final String REDIS_PREFIX_KEY  = "imei-holder";

    private static final long   REDIS_EXPIRE_TIME = 12 * 60 * 60;

    private static final String MOBILE            = "mobile";

    private static final String REGISTER_REQUEST  = "register_request";

    private static final int LOGIN_REQUEST_LIMIT  =  8;


    public void saveMobileRequest(String imei){

        String key = createKey( imei );

        if(!getSync().hexists(key , MOBILE ) ){

            getSync().hset( key , MOBILE , "1" );
            getSync().expire( key , getExpireTime() );

        }else{

            int count = Integer.parseInt( getSync().hget( key , MOBILE ) ) + 1;
            getSync().hset( key , MOBILE , String.valueOf( ++count ) );

        }
    }

    public boolean permitted(String imei){
        String key = createKey( imei );
        return !getSync().hexists( key , MOBILE ) || Integer.parseInt( getSync().hget( key , MOBILE ) ) <= LOGIN_REQUEST_LIMIT;
    }


    public void saveRegisterRequest(String imei, RegisterRequest request){
        String key = createKey( imei );

        String jsonString = toJson( request );

        if(getSync().hexists( key , REGISTER_REQUEST)){
            getSync().hset( key , REGISTER_REQUEST , jsonString );
        }else{
            getSync().hset( key , REGISTER_REQUEST , jsonString );
            getSync().expire( key , REDIS_EXPIRE_TIME );
        }

        saveMobileRequest( imei );
    }


    public RegisterRequest loadRegisterRequest(String imei){
        String key = createKey( imei );

        String value = getSync().hexists( key , REGISTER_REQUEST ) ? getSync().hget( key , REGISTER_REQUEST ) : null;

        return fromJson( value , TypeFactory.defaultInstance().constructType( RegisterRequest.class ) );

    }


    private String createKey( String imei) {
        return String.format( "ks-%s:%s", getPrefixKey(),imei);
    }






    @Override
    protected String getPrefixKey() {
        return REDIS_PREFIX_KEY;
    }

    @Override
    protected long getExpireTime() {
        return REDIS_EXPIRE_TIME;
    }
}
