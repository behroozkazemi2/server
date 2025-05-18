package com.behrouz.server.api.provider;

import com.behrouz.server.api.provider.request.IdActiveRequest;
import com.behrouz.server.api.provider.request.ProviderProductRestRequest;
import com.behrouz.server.api.provider.response.ListResponse;
import com.behrouz.server.api.provider.response.ProductProviderDigestResponse;
import com.behrouz.server.api.provider.response.ProviderProductResponse;
import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionFailureException;
import com.behrouz.server.component.ProductProviderComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.repository.ProductRepository;
import com.behrouz.server.repository.ProviderRepository;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.rest.request.ProviderSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;


public class ProviderProductApi extends ProviderBaseApi {


//        TODO ProductProvider   Fix This


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Autowired
    private ProductProviderComponent productProviderComponent;


    @Autowired
    private ProviderRepository providerRepository;


    @Autowired
    private ProductRepository productRepository;



    @ApiAction(value = "app.provider.product.all")
    public ApiResponseBody<List<IdName>> all(ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest, true);


        List<IdName> response =
                new ArrayList<>();
        try {
            response = productProviderComponent.getAll();
        } catch (BehtaShopException e) {
            e.printStackTrace();
        }

        return new ApiResponseBody<>().ok(response);
    }



    @ApiAction(value = "app.provider.product.provider.list")
    public ApiResponseBody<ListResponse<ProductProviderDigestResponse>> list(@ApiActionParam(nullable = false) ProviderSearchRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {
//
        int providerId =
                getProviderId(apiActionRequest);

        if (providerId != 0 && request.getProviderId() != 0 && request.getProviderId() != providerId) {
            throw new ApiActionFailureException("عدم اجازه");
        }


        DataTableResponse<ProductProviderDigestResponse> response =
                productProviderComponent.getProductProviderPanelList(
                        request.getPage(),
                        request.getLength(),
                        request.getSearch(),
                        request.getProviderId(),
                        request.getBrand(),
                        request.getCategory()
                );

        ListResponse<ProductProviderDigestResponse> result =
                new ListResponse<>(
                        response.getData(),
                        response.getRecordsTotal()
                );

        return new ApiResponseBody<>().ok(result);

    }


    @ApiAction(value = "app.provider.product.provider.detail")
    public ApiResponseBody<ProviderProductResponse> detail(@ApiActionParam(nullable = false) IdName request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest);

        try {

            ProviderProductResponse response = productProviderComponent.detail(request, providerId);

            return new ApiResponseBody<>().ok(response);

        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }


    }

    @ApiAction(value = "app.provider.product.provider.add")
    public ApiResponseBody<IdName> addOrEdit(@ApiActionParam(nullable = false) ProviderProductRestRequest productRequest, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest);

        if(providerId != 0){
            productRequest.setProviderId(providerId);
        }
//
        try {

            IdName response;
            if(productRequest.getProductProviderId() == 0 ){
                response = productProviderComponent.add(productRequest);
            }else{
                response = productProviderComponent.edit(productRequest);
            }
            return new ApiResponseBody<>().ok(response);
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
    }



    @ApiAction(value = "app.provider.product.provider.delete")
    public ApiResponseBody<Void> delete(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId = getProviderId(apiActionRequest);

        try {
            productProviderComponent.delete(request, providerId);
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
        return new ApiResponseBody<>().ok();
    }

    @ApiAction(value = "app.provider.product.provider.exist")
    public ApiResponseBody<Void> exist(@ApiActionParam(nullable = false) IdActiveRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest);

        try {


            productProviderComponent.changeActive(request, providerId);

            return new ApiResponseBody<>().ok();

        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }


    }
}
