package com.behrouz.server.api.provider;


import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.component.ProviderComponent;
import com.behrouz.server.rest.request.IdName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProviderProviderOffApi extends ProviderBaseApi {

    @Autowired
    private ProviderComponent providerComponent;

    @ApiAction(value = "app.provider.provider.off.add")
    public ApiResponseBody<List<IdName>> all(ApiActionRequest apiActionRequest) throws ApiActionException {

        // TODO: 9/12/20

        return new ApiResponseBody<>().ok();
    }


    @ApiAction(value = "app.provider.provider.off.delete")
    public ApiResponseBody<Void> delete(@ApiActionParam(nullable = false) IdName request, ApiActionRequest apiActionRequest) throws ApiActionException {

        // TODO: 9/12/20

        return new ApiResponseBody<>().ok();
    }



}
