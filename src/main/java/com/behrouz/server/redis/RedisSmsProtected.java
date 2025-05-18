package com.behrouz.server.redis;

import com.behrouz.server.redis.core.RedisBase;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

/**
 **/
@Component
public class RedisSmsProtected extends RedisBase {

    private static final String REDIS_SMS_CODE_PREFIX =  "sms-protected";

    //Per Second
    private static final long REDIS_SMS_CODE_EXPIRE_TIME = 10 * 60;
    private static final long REDIS_MAX_SMS_IN_10_MINUTE = 60;


    private static final String SMS_IDENTITY = "identity";


    private Logger logger = Logger.getLogger( RedisSmsProtected.class.getSimpleName() );


    public void saveLog(String identity, String mobile) {

        String key = getKey( mobile );

        getSync().hset( key , SMS_IDENTITY , identity );

        getSync().expire( key ,  getExpireTime());

    }


    public boolean permitSendSms(){

        String searchKey = "skm-" + getPrefixKey() + "*";

        List<String> keys = getSync().keys(searchKey);

        return keys.size() < REDIS_MAX_SMS_IN_10_MINUTE;
    }




    private String getKey(String mobile){
        return String.format( "skm-%s:%s", getPrefixKey() , mobile );
    }



    @Override
    protected String getPrefixKey() {
        return REDIS_SMS_CODE_PREFIX;
    }

    @Override
    protected long getExpireTime() {
        return REDIS_SMS_CODE_EXPIRE_TIME;
    }




}
