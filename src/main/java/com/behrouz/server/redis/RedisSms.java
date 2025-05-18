package com.behrouz.server.redis;

import com.behrouz.server.redis.core.RedisBase;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

/**
 **/
@Component
public class RedisSms extends RedisBase {

    private static final String REDIS_SMS_CODE_PREFIX =  "sms-token";

    //Per Second
    private static final long REDIS_SMS_CODE_EXPIRE_TIME = 5 * 60;

    //Per Second
    private static final long MIN_SECOND_CONSECUTIVE_SEND = 45;

    private static final String SMS_CODE   = "code";

    private static final String SMS_MOBILE = "mobile";

    private static final String SMS_DATE   = "date";

    private Logger logger = Logger.getLogger( RedisSms.class.getSimpleName() );


    public void saveSmsVerifyCode(String identity, String mobile, String code) {

        String key = getKey( identity );

        getSync().hset( key , SMS_CODE , code );
        getSync().hset( key , SMS_MOBILE , mobile );
        getSync().hset( key , SMS_DATE , String.valueOf( new Date().getTime() ) );

        getSync().expire( key ,  getExpireTime());

    }


    public boolean canSend(String identity){

        Date date = getDate( identity );

        if (date == null) {
            return true;
        }

        Date curr = new Date(  );

        long diffidenceMillis = curr.getTime() - date.getTime();

        return  diffidenceMillis > MIN_SECOND_CONSECUTIVE_SEND * 1000;

    }


    public boolean isCorrect(String identity, String mobile, String code) {

        if(!getSync().hexists( getKey( identity ) , SMS_CODE )){
            logger.warning( String.format( "the identity %s code not found(5 five history)",identity) );
            return false;
        }

        String redisCode   = getCode(identity);
        String redisMobile = getMobile(identity);

        return redisCode.equals(code) && redisMobile.equals(mobile);

    }


    public String getCode(String identity){
        if(getSync().hexists( getKey( identity ) , SMS_CODE )){
            return getSync().hget(getKey(identity), SMS_CODE);
        }else{
            return null;
        }
    }

    public String getMobile(String identity){
        if(getSync().hexists( getKey( identity ) , SMS_MOBILE )){
            return getSync().hget(getKey(identity), SMS_MOBILE);
        }else{
            return null;
        }
    }

    public Date getDate(String identity){
        if(getSync().hexists( getKey( identity ) , SMS_DATE )){
            String dateStr = getSync().hget(getKey(identity), SMS_CODE);
            return new Date( Long.parseLong( dateStr ) );
        }else{
            return null;
        }
    }


    public void deleteCode(String identity, String code) {
        getSync().hdel( getKey( identity )  , SMS_CODE);
    }



    private String getKey(String identity){
        return String.format( "skm-%s:%s", getPrefixKey() , identity );
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
