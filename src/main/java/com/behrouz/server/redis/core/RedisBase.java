package com.behrouz.server.redis.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.redis
 * project name:  ximaServer
 * 13 July 2018
 **/

@Component
public abstract class RedisBase {

    /**
     * redis connection bean
     * @see HttpSessionConfig#redisConnection()
     */
    @Autowired
    private StatefulRedisConnection<String, String> redisConnection;

    /**
     * redis connection sync
     */
    private RedisCommands<String, String> sync;


    /**
     * sync initialization
     * @return the synced
     */
    protected RedisCommands<String, String> getSync() {
        if (sync == null) {
            sync = redisConnection.sync();
        }
        return sync;
    }


    protected abstract String getPrefixKey();


    protected abstract long getExpireTime();



    protected String toJson(Object obj){
        String result = null;
        try {
            result = new ObjectMapper(  ).writeValueAsString( obj );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }


    protected<T> T fromJson(String val , JavaType type){
        T result = null;

        try {
            result = new ObjectMapper(  ).readValue(val , type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }


}
