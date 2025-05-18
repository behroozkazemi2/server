package com.behrouz.server.redis;

import com.behrouz.server.redis.core.RedisBase;
import org.springframework.stereotype.Component;

/**
 * Created by hapi
 **/
@Component
public class RedisSmsMobile extends RedisBase {

    private static final String REDIS_PREFIX_KEY  = "sms-mobile";

    private static final long   REDIS_EXPIRE_TIME = 10 * 60;


    private static final String COUNT                = "count";


    private static final int LOGIN_REQUEST_LIMIT  =  10;


    public void saveLog(String mobile){

        String key = createKey( mobile );

        int count = getCount(mobile) + 1;

        getSync().hset( key , COUNT , String.valueOf(count) );

        getSync().expire( key , getExpireTime() );

    }

    private int getCount(String mobile){
        String key = createKey( mobile );
        return getSync().hexists( key , COUNT )  ? Integer.parseInt( getSync().hget( key , COUNT )) : 0;
    }


    public boolean permitted(String mobile){
        return getCount(mobile) <= LOGIN_REQUEST_LIMIT;
    }



    private String createKey( String mobile) {
        return String.format( "skm-%s:%s", getPrefixKey(),mobile);
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
