package com.behrouz.server.api.provider;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionSecurityException;
import com.behrouz.server.redis.RedisToken;
import org.springframework.beans.factory.annotation.Autowired;

public class ProviderBaseApi extends BaseApi {

    @Autowired
    protected RedisToken redisToken;

    protected int getProviderId(ApiActionRequest request) throws ApiActionException {

        return getProviderId(request, false);
    }

    protected int getProviderId(ApiActionRequest request, boolean forceToSuperVisor) throws ApiActionException {
        int providerId = redisToken.getProviderId(request);

        if(providerId == -1 || (forceToSuperVisor && providerId != 0)){
            throw new ApiActionSecurityException();
        }

        return providerId;
    }
}
