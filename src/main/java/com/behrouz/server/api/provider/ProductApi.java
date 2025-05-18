package com.behrouz.server.api.provider;

import com.behrouz.server.api.provider.response.ListResponse;
import com.behrouz.server.api.provider.response.ProductDigestResponse;
import com.behrouz.server.api.provider.response.ProductResponse;
import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionFailureException;
import com.behrouz.server.component.ProductComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.repository.BrandRepository;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.rest.request.InformationRestRequest;
import com.behrouz.server.rest.request.ProductCategoryInfoRequest;
import com.behrouz.server.rest.request.ProviderSearchRequest;
import com.behrouz.server.rest.response.InformationCategoryRestResponse;
import com.behrouz.server.rest.response.InformationRestResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class ProductApi extends ProviderBaseApi {


    @Autowired
    private ProductComponent productComponent;

    @Autowired
    private BrandRepository brandRepository;


    @ApiAction(value = "app.provider.product.list")
    public ApiResponseBody<ListResponse<ProductDigestResponse>> list(@ApiActionParam(nullable = false) ProviderSearchRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {
        DataTableResponse<ProductDigestResponse> response =
                productComponent.getList(
                        request.getPage(),
                        request.getLength(),
                        request.getSearch(),
                        request.getBrand(),
                        request.getCategory()
                );

        ListResponse<ProductDigestResponse> result =
                new ListResponse<>(
                        response.getData(),
                        response.getRecordsTotal()
                );

        return new ApiResponseBody<>().ok(result);

    }


    @ApiAction(value = "app.provider.best.sell.product.list")
    public ApiResponseBody<ListResponse<ProductDigestResponse>> getBestSellsProduct(@ApiActionParam(nullable = false) ProviderSearchRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {
        DataTableResponse<ProductDigestResponse> response =
                productComponent.getBestSellsList(
                        request.getPage(),
                        request.getLength(),
                        request.getSearch(),
                        request.getBrand(),
                        request.getCategory()
                );

        ListResponse<ProductDigestResponse> result =
                new ListResponse<>(
                        response.getData(),
                        response.getRecordsTotal()
                );

        return new ApiResponseBody<>().ok(result);

    }


    @ApiAction(value = "app.provider.product.detail")
    public ApiResponseBody<ProductResponse> detail(@ApiActionParam(nullable = false) IdName request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId =
                getProviderId(apiActionRequest);

        try {

            ProductResponse response =
                    productComponent.detail(request, providerId);

            return new ApiResponseBody<>().ok(response);

        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }


    }

    @ApiAction(value = "app.provider.product.add")
    public ApiResponseBody<IdName> addOrEdit(@ApiActionParam(nullable = false) ProductResponse productRequest, ApiActionRequest apiActionRequest) throws ApiActionException {
//
//        int providerId =
//                getProviderId(apiActionRequest);

//        if(providerId != 0){
//            productRequest.setProviderId(providerId);
//        }
//
        try {

            IdName response;
            if(productRequest.getId() == 0 ){
                response = productComponent.add(productRequest);
            }else{
                response = productComponent.edit(productRequest);
            }
            return new ApiResponseBody<>().ok(response);
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
    }

    @ApiAction(value = "app.provider.product.delete")
    public ApiResponseBody<Void> delete(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId = getProviderId(apiActionRequest);

        try {
            productComponent.delete(request, providerId);
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
        return new ApiResponseBody<>().ok();



    }



    @ApiAction(value = "app.provider.brand.list")
    public ApiResponseBody<List<IdName>> brands(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        int providerId = getProviderId(apiActionRequest);
            List<IdName> brands =
                    brandRepository.findAllByDeletedIsFalse().stream().map( m -> new IdName(m.getId(), m.getName()))
                    .collect(Collectors.toList());
        return new ApiResponseBody<>().ok(brands);


    }


    @ApiAction(value = "app.provider.information.category.list")
    public ApiResponseBody<ListResponse<InformationCategoryRestResponse>> informationCategoryList(@ApiActionParam(nullable = false) ProviderSearchRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        DataTableResponse<InformationCategoryRestResponse> response =
                productComponent.getInformationCategoryList(
                        request.getPage(),
                        request.getLength(),
                        request.getSearch()
                );

        ListResponse<InformationCategoryRestResponse> result =
                new ListResponse<>(
                        response.getData(),
                        response.getRecordsTotal()
                );

        return new ApiResponseBody<>().ok(result);

    }

    @ApiAction(value = "app.provider.product.information.category.list")
    public ApiResponseBody<ListResponse<InformationRestResponse>> productInformationCategoryList(@ApiActionParam(nullable = false) ProductCategoryInfoRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        DataTableResponse<InformationRestResponse> response =
                productComponent.getProductInformationList(
                        request.getPage(),
                        request.getLength(),
                        request.getSearch(),
                        request.getInformationCategoryId(),
                        request.getProductId()
                );

        ListResponse<InformationRestResponse> result =
                new ListResponse<>(
                        response.getData(),
                        response.getRecordsTotal()
                );

        return new ApiResponseBody<>().ok(result);

    }

    @ApiAction(value = "app.provider.save.product.information.category")
    public ApiResponseBody<Void> savProductInfoCategory(@ApiActionParam(nullable = false) InformationRestRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {
        try {
            productComponent.saveProductInformation(request);
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
        return new ApiResponseBody<>().ok();
    }

    @ApiAction(value = "app.provider.detail.product.information.category")
    public ApiResponseBody<InformationRestResponse> detailProductInfoCategory(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        InformationRestResponse res = new InformationRestResponse();
        try {
            res = productComponent.productInformationDetail(request);
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
        return new ApiResponseBody<>().ok(res);
    }

    @ApiAction(value = "app.provider.delete.product.information.category")
    public ApiResponseBody<Void> deleteProductInfoCategory(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {
        try {
            productComponent.deleteProductInformation(request);
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
        return new ApiResponseBody<>().ok();
    }


}
