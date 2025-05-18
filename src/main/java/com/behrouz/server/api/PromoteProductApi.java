package com.behrouz.server.api;

import com.behrouz.server.api.provider.ProviderBaseApi;
import com.behrouz.server.api.provider.request.PromoteSaveRequest;
import com.behrouz.server.api.provider.response.ListResponse;
import com.behrouz.server.api.provider.response.PromoteDigestResponse;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.rest.request.ProviderSearchRequest;
import com.behrouz.server.api.provider.response.*;
import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.component.ProductProviderComponent;
import com.behrouz.server.component.PromoteProductComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.repository.ProductRepository;
import com.behrouz.server.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class PromoteProductApi extends ProviderBaseApi {


//        TODO ProductProvider   Fix This


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Autowired
    private ProductProviderComponent productProviderComponent;


    @Autowired
    private PromoteProductComponent promoteProductComponent;


    @Autowired
    private ProviderRepository providerRepository;


    @Autowired
    private ProductRepository productRepository;



    @ApiAction(value = "app.provider.promote.product.list")
    public ApiResponseBody<ListResponse<PromoteDigestResponse>> getAllPromotes(@ApiActionParam(nullable = false) ProviderSearchRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest, true);

        DataTableResponse<PromoteDigestResponse> response =
                null;
        try {
            response = promoteProductComponent.getList(
                    request.getPage(),
                    request.getLength(),
                    request.getSearch(),
                    request.getProviderId()
            );
        } catch (BehtaShopException e) {


        }

        ListResponse<PromoteDigestResponse> result =
                new ListResponse<>(
                        response.getData(),
                        response.getRecordsTotal()
                );


        return new ApiResponseBody<>().ok(result);
    }

    @ApiAction(value = "app.provider.promote.detail")
    public ApiResponseBody<PromoteDigestResponse> promoteDetail(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {


        PromoteDigestResponse response =
                null;
        try {
            response = promoteProductComponent.getPromoteDetail(request.getId());
        } catch (BehtaShopException e) {


        }
        return new ApiResponseBody<>().ok(response);
    }

    @ApiAction(value = "app.provider.promote.product.add")
    public ApiResponseBody<IdName> savePromoteProduct(@ApiActionParam(nullable = false) PromoteSaveRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {


        IdName response =
                null;
        try {
            response = promoteProductComponent.savePromoteDetail(request);
        } catch (BehtaShopException e) {


        }
        return new ApiResponseBody<>().ok(response);
    }

   @ApiAction(value = "app.provider.promote.product.delete")
    public ApiResponseBody<Void> deletePromoteProduct(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        try {
             promoteProductComponent.deletePromoteProduct(request);
        } catch (BehtaShopException e) {


        }
        return new ApiResponseBody<>().ok();
    }


}
