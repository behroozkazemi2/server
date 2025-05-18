package com.behrouz.server.api.provider;

import com.behrouz.server.api.provider.request.IdActiveRequest;
import com.behrouz.server.api.provider.response.ListResponse;
import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionFailureException;
import com.behrouz.server.component.ProviderComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.rest.request.ProviderRequest;
import com.behrouz.server.rest.request.ProviderSearchRequest;
import com.behrouz.server.rest.response.digestList.ProviderListDigestResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProviderProviderApi extends ProviderBaseApi {

    @Autowired
    private ProviderComponent providerComponent;

    @ApiAction(value = "app.provider.provider.all")
    public ApiResponseBody<List<IdName>> all(ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest, true);


        List<IdName> response =
                providerComponent.getAll();

        return new ApiResponseBody<>().ok(response);
    }

    @ApiAction(value = "app.provider.provider.list")
    public ApiResponseBody<ListResponse<ProviderListDigestResponse>> list(@ApiActionParam(nullable = false) ProviderSearchRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest, true);


        DataTableResponse<ProviderListDigestResponse> response =
                providerComponent.getList(
                        request.getPage(),
                        request.getLength(),
                        request.getSearch()
                );



        ListResponse<ProviderListDigestResponse> result =
                new ListResponse<>(
                        response.getData(),
                        response.getRecordsTotal()
                );


        return new ApiResponseBody<>().ok(result);


    }

    @ApiAction(value = "app.provider.provider.add")
    public ApiResponseBody<ProviderListDigestResponse> add(@ApiActionParam(nullable = false) ProviderRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest, true);

        try {

            ProviderListDigestResponse response = providerComponent.add(request);

            return new ApiResponseBody<>().ok(response);

        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }


    }


    @ApiAction(value = "app.provider.provider.detail")
    public ApiResponseBody<ProviderListDigestResponse> detail(@ApiActionParam(nullable = false) IdName request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest, true);

        try {

            ProviderListDigestResponse response = providerComponent.detail(request);

            return new ApiResponseBody<>().ok(response);

        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }


    }

    @ApiAction(value = "app.provider.provider.delete")
    public ApiResponseBody<Void> delete(@ApiActionParam(nullable = false) IdName request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest, true);

        try {

            providerComponent.delete(request);

            return new ApiResponseBody<>().ok();

        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }


    }

    @ApiAction(value = "app.provider.provider.status")
    public ApiResponseBody<Void> status(@ApiActionParam(nullable = false) IdActiveRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest, true);

        try {

            providerComponent.activeDeactivated(request);

            return new ApiResponseBody<>().ok();

        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }


    }




    @ApiAction(value = "app.provider.provider.category")
    public ApiResponseBody<Void> category(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

//        int providerId =
//                getProviderId(apiActionRequest);
//
//        if(providerId != 0 && request.getId() != providerId){
//            request.setId(providerId);
//        }
        try {

            List<IdName> response = providerComponent.category();

            return new ApiResponseBody<>().ok(response);

        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }


    }




}
