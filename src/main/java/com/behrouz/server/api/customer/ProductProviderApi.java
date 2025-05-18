package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.request.ProductProviderCategoryRequest;
import com.behrouz.server.api.customer.response.ProductDetailResponse;
import com.behrouz.server.api.customer.response.ProductResponse;
import com.behrouz.server.api.customer.response.PromotePromoteProductResponse;
import com.behrouz.server.api.customer.response.ProviderResponse;
import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.repository.*;
import com.behrouz.server.api.customer.response.*;
import com.behrouz.server.api.provider.query.QueryPairObject;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionNotFoundException;
import com.behrouz.server.component.ProductComponent;
import com.behrouz.server.component.ProductProviderComponent;
import com.behrouz.server.model.*;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.model.product.PromoteEntity;
import com.behrouz.server.modelOption.ProviderStatusOption;
import com.behrouz.server.repository.*;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.IdLong;
import com.behrouz.server.rest.response.InformationRestResponse;
import com.behrouz.server.utils.ArraysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer
 * Project server
 * 30 September 2018 10:40
 **/

/**
 *  1 ) Provider List -> show all provider that have selected product
 *  {@link #providerList(ProductProviderCategoryRequest)}
 */
public class ProductProviderApi extends BaseApi{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private ProviderRepository providerRepository;

//    @Autowired
//    private ProviderImageRepository providerImageRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Autowired
    private OffCodeRepository offCodeRepository;

//    @Autowired
//    private ProviderCategoryRepository providerCategoryRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProductProviderComponent productProviderComponent;


    @Autowired
    private ProductComponent productComponent;

    @Autowired
    private NamedParameterJdbcTemplate jdcTemplate;

    @Autowired
    private PromoteRepository promoteRepository;

//    @Autowired
//    private SpecialProductProviderRepository specialProductProviderRepository;
//
//    @Autowired
//    private ProviderSpecialProductSuggestionRepository providerSpecialProductSuggestionRepository;
//

    /**
     * Get all providers in specific category
     * @param request {@link ProductProviderCategoryRequest} providerCategoryId, productCategoryId
     * @return {@link ProviderResponse}
     * @throws ApiActionException
     */
    @ApiAction( value = "app.customer.provider.list" , tokenRequired = false)
    public ApiResponseBody providerList(ProductProviderCategoryRequest request ) throws ApiActionException {

        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getLength());
        Map<String, Object> params = new HashMap<>();
        params.put("pageLimit", pageRequest.getPageSize());
        params.put("pageOffset", pageRequest.getOffset());
        params.put("reg", request.getRegion());

        String queryPart =  "FROM account prv " +
//                (ArraysUtil.isNullOrEmpty(request.getProviderCategoryId()) ? "" : "INNER JOIN provider_category pc on prv.id = pc.provider_id AND pc.deleted = FALSE AND pc.category_id in (:prvCtg) ") +
                "LEFT JOIN provider_comment_rate_view pcm ON pcm.provider_id = prv.id " +
                "WHERE prv.deleted = FALSE " +
                (request.getRegion() == 0 ? "" : "AND prv.region_id = (:reg) ") +
                "GROUP BY prv.id, prv.name, prv.rate, pcm.comment_count, prv.image_id, prv.short_description, prv.full_description, prv.phone, prv.address, prv.address " ;

        String query =
                "SELECT " +
                        "prv.id id, " +
                        "prv.name nm, " +
                        "prv.rate rt, " +
                        "pcm.comment_count pcm, " +
                        "prv.image_id img, " +
                        "prv.short_description sDsc, " +
                        "prv.full_description fDsc, " +
                        "prv.phone phn, " +
                        "prv.address adr, " +
                        "prv.active st " +
                       queryPart + " " +
                "ORDER BY prv.rate DESC " +
                "LIMIT :pageLimit OFFSET :pageOffset ";

        String queryCount = "SELECT count(*) FROM (SELECT 1 " + queryPart + " ) mmg";



        List<ProviderResponse> result =
                jdcTemplate.query(
                        query,
                        params,
                        (res, rowId) -> new ProviderResponse(
                                res.getLong("id"),
                                res.getString("nm"),
                                res.getFloat("rt"),
                                res.getLong("pcm"),
                                res.getLong("img"),
                                0,
                                res.getString("sDsc"),
                                res.getString("fDsc"),
                                new ArrayList<String>(){{add(res.getString("phn"));}},
                                res.getString("adr"),
                                res.getBoolean("st") ? ProviderStatusOption.ACTIVE.getId() : ProviderStatusOption.NOT_ACTIVE.getId()
                        )
                );

        List<Long> totals =
                jdcTemplate.query(
                        queryCount,
                        params,
                        (res, rowId) -> res.getLong(1)
                );

        long total = ArraysUtil.isNullOrEmpty(totals) ? 0 : totals.get(0);


        List<Long> prvIds =
                result.stream().map(ProviderResponse::getId).collect(Collectors.toList());

        if(!ArraysUtil.isNullOrEmpty(prvIds)){
            result = fillDiscount(result, prvIds);
//            result = fillCategory(result, prvIds);
        }


        return new ApiResponseBody <>().ok(result, total);

    }

    @ApiAction( value = "web.provider.popular", tokenRequired = false )
    public ApiResponseBody providerLast() throws ApiActionException {

        List<ProviderEntity> providers =
                providerRepository.findFirst10ByDeletedIsFalseAndBannedIsFalseAndActiveIsTrueOrderByIdDesc();

        if ( providers == null ) {
            throw new ApiActionNotFoundException(
                    "هیچ تامین کننده ای یافت نشد!"
            );
        }


        return new ApiResponseBody<>().ok(
                providers
                        .stream()
                        .map( e ->
                                new ProviderResponse(
                                        e,
//                                        providerCategoryRepository.findAllByProvider_IdAndProvider_ActiveIsTrueAndDeletedIsFalse(e.getId()),
                                        addressRepository.findFirstByAccount_IdAndDeletedIsFalseOrderByIdDesc(e.getId()),
                                        getProviderDiscount( e.getId() )
                                )
                        )
                        .collect( Collectors.toList() )
        );
    }

    @ApiAction( value = "web.provider.detail", tokenRequired = false )
    public ApiResponseBody providerDetail(@ApiActionParam(nullable = false) IdRequest request ) throws ApiActionException {

        ProviderEntity provider =
                providerRepository.findFirstByIdAndDeletedIsFalseAndBannedIsFalseAndActiveIsTrue(
                        request.getId()
                );

        if ( provider == null ) {
            throw new ApiActionNotFoundException(
                    "تامین کننده مورد نظر یافت نشد!"
            );
        }


        return new ApiResponseBody <>().ok(
                new ProviderResponse(
                        provider,
//                        providerCategoryRepository.findAllByProvider_IdAndProvider_ActiveIsTrueAndDeletedIsFalse( provider.getId() ),
                        addressRepository.findFirstByAccount_IdAndDeletedIsFalseOrderByIdDesc( provider.getId() ),
                        getProviderDiscount( provider.getId() )
                )
        );
    }

    @ApiAction( value = "app.customer.product.list" , tokenRequired = false)
    public ApiResponseBody<List<ProductResponse>> productList(@ApiActionParam(nullable = false) ProductProviderCategoryRequest request, int customerId ) throws ApiActionException {


        request.setUpPrice( request.getUpPrice()*10 );
        request.setDownPrice( request.getDownPrice()*10 );

//        if(request.getOrderId() == ProductOrderOption.PROMOTE.getId()) {
//            return productProviderComponent.searchProductPromote(request, customerId);
//        }

        DataTableResponse<ProductResponse> response =
                productProviderComponent.searchProduct(request, customerId);

        List<ProductResponse> result =
                response.getData().stream().map(ProductResponse::toToman).collect(Collectors.toList());

        return new ApiResponseBody<List<ProductResponse>>().ok(result, response.getRecordsTotal());
    }

  @ApiAction( value = "web.special.promote.product.list" , tokenRequired = false)
    public ApiResponseBody<PromotePromoteProductResponse> specialPromoteProductList(@ApiActionParam(nullable = false) IdRequest addressId, int customerId ) throws ApiActionException {

      PromoteEntity specialPromoteId =
              promoteRepository.findFirstByDeletedIsFalseOrderById();

        List<ProductResponse> response =
                productProviderComponent.searchPromoteProduct(specialPromoteId == null ? 0 : specialPromoteId.getId() , customerId, addressId.getId());

        List<ProductResponse> result =
                response.stream().map(ProductResponse::toToman).collect(Collectors.toList());



      PromotePromoteProductResponse res =
              new PromotePromoteProductResponse(
                      specialPromoteId == null ? new PromoteEntity() : specialPromoteId,
                      result
              );
        return new ApiResponseBody<PromotePromoteProductResponse>().ok(res, 0);
    }

  @ApiAction( value = "web.last.promote.product.list" , tokenRequired = false)
    public ApiResponseBody<PromotePromoteProductResponse> lastPromoteProductList(@ApiActionParam(nullable = false) IdRequest addressId, int customerId ) throws ApiActionException {




      List<PromoteEntity> allPromoteId =
              promoteRepository.findAllByDeletedIsFalseOrderByIdDesc();

        List<ProductResponse> response =
                productProviderComponent.searchPromoteProduct(ArraysUtil.isNullOrEmpty(allPromoteId) ? 0 : allPromoteId.get(0).getId() , customerId, addressId.getId());

        List<ProductResponse> result =
                response.stream().map(ProductResponse::toToman).collect(Collectors.toList());

      PromotePromoteProductResponse res =
              new PromotePromoteProductResponse(
                      ArraysUtil.isNullOrEmpty(allPromoteId) ? new PromoteEntity()  : allPromoteId.get(0),
                      result
              );
        return new ApiResponseBody<PromotePromoteProductResponse>().ok(res, 0);
    }

    @ApiAction( value = "app.customer.product.detail", tokenRequired = false)
    public ApiResponseBody productDetailApp(IdLong idRequest , int customerId ) throws ApiActionException {

        return productDetail(idRequest, customerId);

    }

    @ApiAction( value = "web.product.detail", tokenRequired = false)
    public ApiResponseBody productDetail(IdLong idRequest, int userId ) throws ApiActionException {

//        idRequest = > {productProviderId, addressId}
        ProductProviderCategoryRequest request = new ProductProviderCategoryRequest();
        request.setProductIds(Collections.singletonList(idRequest.getId())); // productProviderIds
        request.setLength(1);
        request.setAddressId(idRequest.getValue());

        DataTableResponse<ProductResponse> result = productProviderComponent.searchProduct(
                request,
                userId
        );


        CustomerOrderEntity order = userId == 0 ? null :
                customerOrderRepository.findFirstByCustomer_IdAndProductProvider_IdAndDeletedIsFalse(userId, idRequest.getId());



        ProductDetailResponse response = new ProductDetailResponse(
                result.getData().get(0),
                "",
                order != null ? order.getOrderCount() : 0
        ).toToman();





        return new ApiResponseBody <>().ok(response);

    }

    @ApiAction( value = "web.product.checkIsAnyProductInArea", tokenRequired = false)
    public ApiResponseBody checkIsAnyProductInArea(IdLong idRequest, int userId ) throws ApiActionException {
        ProductProviderCategoryRequest request = new ProductProviderCategoryRequest();
        request.setAddressId(idRequest.getValue());
        request.setLength(1);

        DataTableResponse<ProductResponse> result = productProviderComponent.searchProduct(
                request,
                userId
        );
        return new ApiResponseBody <>().ok(result.getRecordsTotal() != 0);

    }

    private List<ProviderResponse> fillDiscount(List<ProviderResponse> result, List<Long> prvIds) {

        String query =
                "SELECT " +
                        "pp.provider_id id, " +
                        "max(ppl.off_percent) ofp " +
                        "FROM product_price_last ppl " +
                        "INNER JOIN product_provider pp ON pp.id = ppl.product_provider_id " +
                        "WHERE pp.provider_id IN (:ids) " +
                        "AND pp.deleted = FALSE " +
                        "GROUP BY pp.provider_id ";

        List<QueryPairObject<Long, Float>> mResult = jdcTemplate.query(
                query,
                new MapSqlParameterSource("ids", prvIds),
                (res, rowId) -> new QueryPairObject<>(res.getLong("id"), res.getFloat("ofp"))
        );
        Map<Long, Float> mResultMap =
                mResult.stream().collect(Collectors.toMap(QueryPairObject::getFirst, QueryPairObject::getSecond));

        return result.stream().peek( p -> p.setDiscount(mResultMap.getOrDefault( p.getId(), 0f))).collect(Collectors.toList());

    }

//    private List<ProviderResponse> fillCategory(List<ProviderResponse> result, List<Long> prvIds) {
//
//        String query =
//                "SELECT " +
//                        "pc.provider_id id, " +
//                        "c.id ctgId, " +
//                        "c.name ctgNm " +
//                        "FROM provider_category pc " +
//                        "INNER JOIN category c on pc.category_id = c.id " +
//                        "WHERE pc.provider_id IN (:ids) " +
//                        "AND pc.deleted = FALSE " +
//                        "AND c.deleted = FALSE ";
//
//        List<QueryPairObject<Long, RequestDetailResponse>> mResult = jdcTemplate.query(
//                query,
//                new MapSqlParameterSource("ids", prvIds),
//                (res, rowId) -> new QueryPairObject<>(
//                        res.getLong("id"),
//                        new RequestDetailResponse(
//                                res.getLong("ctgId"),
//                                res.getString("ctgNm")
//                        )
//                )
//        );
////        Map<Long, List<RequestDetailResponse>> mResultMap =
////                mResult.stream().collect(Collectors.groupingBy(QueryPairObject::getFirst)).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().stream().map(QueryPairObject::getSecond).collect(Collectors.toList())));
//
////        return result.stream().peek( p -> p.setCategories(mResultMap.getOrDefault( p.getId(), null))).collect(Collectors.toList());
//        return result;
//    }

    private float getProviderDiscount ( long providerId ) {

        List <OffCodeEntity> discounts =
                offCodeRepository.findAllByProvider_IdAndProductProvider_DeletedIsFalseAndDeletedIsFalse( providerId );

        if ( discounts == null || discounts.isEmpty() ){
            return 0;
        }

        return (float) discounts.stream().mapToDouble( OffCodeEntity::getPercent ).max().getAsDouble();

    }

    private float getProductProviderDiscount ( int productProviderId ) {

        OffCodeEntity discount =
                offCodeRepository.findFirstByProductProvider_IdAndDeletedIsFalseAndProductProvider_DeletedIsFalseOrderByIdDesc( productProviderId );

        if ( discount == null ){
            return 0;
        }

        return discount.getPercent();

    }

//    private List<ImageResponse> getProviderImages(int providerId ) {
//
//        List<ProviderImageEntity> providerImages =
//                providerImageRepository.findAllByProvider_IdAndProvider_ActiveIsTrueAndDeletedIsFalse( providerId );
//
//        if ( providerImages == null ) {
//            return new ArrayList<>(  );
//        }
//
//
//        return providerImages
//                .stream()
//                .filter( e -> e.getImage() != null && e.getImage().getImage() != null )
//                .map( e ->
//                        new ImageResponse(
//                                e.getImage().getImage()
//                        )
//                )
//                .collect( Collectors.toList());
//    }




    @ApiAction( value = "app.customer.product.information.category" , tokenRequired = false)
    public ApiResponseBody<List<InformationRestResponse>> productInformationCategoryList(@ApiActionParam(nullable = false) IdRequest request, int customerId ) throws ApiActionException {

        List<InformationRestResponse> response =
                productComponent.getProductInformationCategoryList(request, customerId);

        return new ApiResponseBody<List<InformationRestResponse>>().ok(response);
    }

}
