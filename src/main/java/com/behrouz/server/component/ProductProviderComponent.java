package com.behrouz.server.component;

import com.behrouz.server.api.customer.data.LatLngData;
import com.behrouz.server.api.customer.request.ProductProviderCategoryRequest;
import com.behrouz.server.api.customer.response.ProductResponse;
import com.behrouz.server.api.provider.request.IdActiveRequest;
import com.behrouz.server.api.provider.request.ProviderProductRestRequest;
import com.behrouz.server.api.provider.response.ProductProviderDigestResponse;
import com.behrouz.server.api.provider.response.ProviderProductResponse;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.model.global.CategoryEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.model.product.ProductEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.model.product.ProductProviderPriceEntity;
import com.behrouz.server.repository.*;
import com.behrouz.server.rest.constant.DataTableRequest;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.constant.RequestResponseList;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.rest.request.IdNameType;
import com.behrouz.server.rest.request.PanelSearchRequest;
import com.behrouz.server.rest.response.ProductProviderImageResponse;
import com.behrouz.server.rest.response.digestList.ProductProviderListDigestResponse;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.api.provider.response.*;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.product.*;
import com.behrouz.server.modelOption.ProductOrderOption;
import com.behrouz.server.modelOption.ProductProviderOption;
import com.behrouz.server.repository.*;
import com.behrouz.server.specification.ProductProviderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.component
 * Project server
 * 24 September 2018 15:42
 **/
@Component
public class ProductProviderComponent {


    @Autowired
    private ProductProviderRepository productProviderRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductTagRepository productTagRepository;

    @Autowired
    private TagRepository tagRepository;

//    @Autowired
//    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private UnitRepository unitRepository;

//    @Autowired
//    private ProductProviderRepository productProviderRepository;

//    @Autowired
//    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProviderComponent providerComponent;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

//    @Autowired
//    private ProductImageRepository productImageRepository;


    public RequestResponseList getList(DataTableRequest request, int providerId ) throws BehtaShopException {

        Page <ProductProviderEntity> productProviderList;

        if ( request.getStatus() == ProductProviderOption.EXIST.getId() ) {

            productProviderList =
                    productProviderRepository.findAllByProvider_IdAndDeletedIsFalseAndProductProviderExistenceIsTrue(
                            providerId,
                            PageRequest.of( request.getPage(), request.getLimit() )
                    );

        } else {

            //todo serach for customized
            productProviderList =
                    productProviderRepository.findAllByProvider_IdAndDeletedIsFalseAndProductProviderExistenceIsTrue(
                            providerId,
                            PageRequest.of( request.getPage(), request.getLimit() )
                    );

        }


        return
                new RequestResponseList <>( productProviderList
                        .stream()
                        .map( e ->
                                new ProductProviderListDigestResponse(
                                        e,
                                        productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc( e.getId() ),
                                        productImageRepository.findAllByProduct_IdAndProduct_DeletedIsFalseAndDeletedIsFalse( e.getProduct().getId() )
                                )
                        )
                        .collect( Collectors.toList() ),
                        productProviderList.getTotalElements()
                );
    }

    public RequestResponseList search(PanelSearchRequest searchRequest, long providerId ) throws BehtaShopException {

        // TODO jdbcTemplate
        Page < ProductProviderEntity > productProviderList =
                productProviderRepository.findAll(
                        ProductProviderSpecification.searchAllBy(
                                searchRequest.getpName(),
                                (int) providerId,
                                searchRequest.getStatus()
                        ),
                        PageRequest.of( searchRequest.getPage(), searchRequest.getLimit() )
                );


        return new RequestResponseList <>( productProviderList
                .stream()
                .map( e ->
                        new ProductProviderListDigestResponse(
                                e,
                                productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc( e.getId() ),
                                productImageRepository.findAllByProduct_IdAndProduct_DeletedIsFalseAndDeletedIsFalse( e.getProduct().getId() )
                        )
                )
                .collect( Collectors.toList() ),
                productProviderList.getTotalElements()
        );
    }

    public DataTableResponse<ProductResponse> searchProduct(ProductProviderCategoryRequest request, int customerId){

        AddressEntity currentAddress =
                addressRepository.findFirstByAccount_IdAndIdAndDeletedIsFalse(customerId,request.getAddressId());

        LatLngData currentLatLng = currentAddress == null ? new LatLngData() : new LatLngData(currentAddress);

        List<Long> currentAreaProviders = providerComponent.getAllProviderInRegional(currentLatLng)
                .stream().map(BaseEntity::getId).collect(Collectors.toList());

        // TODO CHANGE PROVIDER ID TO LIST LATER
        if (request.getAddressId() != -1) {
            request.setProviderId(ArraysUtil.isNullOrEmpty(currentAreaProviders) ? 0 : currentAreaProviders.get(0));
        }
        if(request.getOrderId() == ProductOrderOption.SAIL.getId()){
            request.setOrderId( 0 );
        }
        if(!ArraysUtil.isNullOrEmpty(request.getProductCategoryId()) && request.getProductCategoryId().get(0) == 0){
            request.getProductCategoryId().remove(0);
        }
        if(!ArraysUtil.isNullOrEmpty(request.getProductCategoryId()) && request.getProductCategoryId().get(0) == 0){
            request.getProductCategoryId().remove(0);
        }
        if(!ArraysUtil.isNullOrEmpty(request.getBrands()) && request.getBrands().get(0) == 0){
            request.getBrands().remove(0);
        }
        if(!ArraysUtil.isNullOrEmpty(request.getTag()) && request.getTag().get(0) == 0){
            request.getTag().remove(0);
        }

        PageRequest pageRequest = PageRequest.of( request.getPage(), request.getLength() );

        List<Long> allCats = ArraysUtil.isNullOrEmpty(request.getProductCategoryId()) ? null :
                findAllParentForSelectedCategory(request.getProductCategoryId());


        HashMap<String, Object> params = new HashMap<>();
        params.put("productIds", request.getProductIds());
        params.put("region", request.getRegion());
        params.put("providerId", request.getProviderId());
        params.put("searchStr", "%" + request.getSearch() + "%" );
        params.put("toPrice", (long)request.getUpPrice());
        params.put("fromPrice", (long)request.getDownPrice());
        params.put("prdCtg", allCats);
        params.put("exist", request.isExistence());
        params.put("brIds", request.getBrands());
        params.put("orderId", request.getOrderId());
        params.put("tgIds", request.getTag());
        params.put("pageLimit", pageRequest.getPageSize());
        params.put("pageOffset", pageRequest.getOffset());
        String queryPart = "" +
                "FROM product_provider pp " +
                "INNER JOIN product_price_last prc ON pp.id = prc.product_provider_id AND pp.deleted = FALSE " + (ArraysUtil.isNullOrEmpty(request.getProductIds()) ? "" : "AND pp.id IN (:productIds) " ) +
                "INNER JOIN account prv ON pp.provider_id = prv.id " + (request.getAddressId() == -1 ? "" : "AND prv.id = (:providerId) ") +
                (request.getRegion() == 0 ? "" : "AND prv.region_id = (:region) ") +
                "INNER JOIN product p ON pp.product_id = p.id " +
                "INNER JOIN category ctg ON p.category_id = ctg.id and ctg.deleted = false " +
                "LEFT JOIN category ctgp ON ctgp.id = ctg.parent_id and ctgp.deleted = false " +
                "INNER JOIN brand b ON p.brand_id = b.id  and b.deleted = false " +
                "INNER JOIN unit unt ON p.unit_id = unt.id " +
//                "LEFT  JOIN regional rgp ON pr.region_id = rgp.id " +
                (ArraysUtil.isNullOrEmpty(request.getTag()) ? "" : "INNER JOIN product_tag ptg ON p.id = ptg.product_id AND ptg.tag_id IN (:tgIds) AND ptg.deleted = FALSE ") +
                "WHERE prv.deleted = FALSE " +
                "AND ctg.deleted = FALSE " +
                "AND unt.deleted = FALSE " +
                "AND pp.deleted = FALSE " +
                (!request.isExistence()  ? "" : "AND pp.product_provider_existence = :exist " )+
//                "AND pp.product_provider_existence = TRUE " +
                (StringUtil.isNullOrEmpty(request.getSearch()) ? "" : "AND (p.name LIKE :searchStr OR p.short_description LIKE :searchStr )OR ( p.full_description LIKE :searchStr ) ") +
                (request.getDownPrice() == 0 ? "" : "AND prc.final_amount >= :fromPrice ") +
                (request.getUpPrice() == 0 ? "" : "AND prc.final_amount <= :toPrice " ) +
                (ArraysUtil.isNullOrEmpty(request.getProductCategoryId()) ? "" : "AND (ctg.id in (:prdCtg) OR ctg.parent_id in (:prdCtg)  )  ") +
                (ArraysUtil.isNullOrEmpty(request.getBrands()) ? "" : "AND b.id in (:brIds) ") +
                "GROUP BY " +
                "pp.id , " +
                "p.id , " +
                "p.name , " +
                "pp.rate , " +
                "pp.product_provider_existence , " +
                "pp.insert_date, " +
                "p.short_description , " +
                "p.full_description , " +
                "prv.id , " +
                "prv.name , " +
//                "rgp.id, " +
//                "rgp.name , " +
                "unt.id , " +
                "unt.name , " +
                "ctg.id , " +
                "ctg.name , " +
                "ctgp.id , " +
                "ctgp.name , " +
                "pp.min_allow , " +
                "pp.max_allow , " +
                "p.unit_step , " +
                "pp.prepare_hour , " +
                "prc.primitive_amount , " +
                "prc.off_percent , " +
                "prc.off_price , " +
                "prc.final_amount, " +
                "pp.product_count, " +
                "b.name," +
                "b.id ";


        String queryOrder = getOrderQuery(request.getOrderId());

        String query = "" +
                "SELECT " +
                "pp.id ppId, " +
                "p.name pName, " +
                "p.id pId, " +
                "pp.product_count productCount, " +
                "pp.rate pRate, " +
                "pp.product_provider_existence pExistence, " +
                "p.short_description pShortDesc, " +
                "p.full_description pFullDesc, " +
                "prv.id prvId, " +
                "prv.name prvName, " +
                "pp.insert_date, " +

//                "rgp.id prvRegId, " +
//                "rgp.name prvRegName, " +
                "unt.id untId, " +
                "unt.name untName, " +
                "ctg.id ctgId, " +
                "ctg.name ctgName, " +
                "ctgp.id ctgpId, " +
                "ctgp.name ctgpName, " +
                "pp.min_allow pMinAllow, " +
                "pp.max_allow pMaxAllow, " +
                "p.unit_step pUnitStep, " +
                "pp.prepare_hour pPrepareHour, " +
                "prc.primitive_amount prcPrimitive, " +
                "prc.off_percent prcOffPercent, " +
                "prc.off_price prcOffPrice, " +
                "prc.final_amount prcFinalAmount, " +
                "b.name bName, " +
                "b.id bId " +
                 queryPart +
                 queryOrder +
                " LIMIT  :pageLimit OFFSET :pageOffset ";


        String queryCount = "SELECT count(*) FROM (SELECT 1 " + queryPart + ") mmg ";



        List<ProductResponse> result =
                jdbcTemplate.query(
                        query,
                        params,
                        (res, rowId) ->
                                new ProductResponse(
                                        res.getLong("ppId"),
                                        res.getLong("pId"),
                                        res.getString("pName"),
                                        res.getFloat("pRate"),
                                        res.getBoolean("pExistence"),
                                        res.getString("pShortDesc"),
                                        res.getString("pFullDesc"),
                                        new IdName(
                                                res.getLong("prvId"),
                                                res.getString("prvName")
                                        ),
//                                        new IdName(
//                                                res.getLong("prvRegId"),
//                                                res.getString("prvRegName")
//                                        ),
                                        new IdName(0,""),
                                        new IdName(
                                                res.getLong("untId"),
                                                res.getString("untName")
                                        ),
                                        new IdName(
                                                res.getLong("ctgId"),
                                                res.getString("ctgName")
                                        ),
                                        new IdName(
                                                res.getLong("ctgpId"),
                                                res.getString("ctgpName")
                                        ),
                                        res.getDouble("pMinAllow"),
                                        res.getDouble("pMaxAllow"),
                                        res.getDouble("pUnitStep"),
                                        res.getLong("pPrepareHour"),
                                        res.getLong("prcPrimitive"),
                                        res.getFloat("prcOffPercent"),
                                        res.getLong("prcOffPrice"),
                                        res.getLong("prcFinalAmount"),
                                        null,
                                        null,
                                        res.getObject("bName") == null ? new IdName() : new IdName(res.getLong("bId"), res.getString("bName")),
                                        res.getLong("productCount")
                                )
                );


        if(!ArraysUtil.isNullOrEmpty(result)){
            result = fillImageProduct(result);
            result = fillImageTag(result);
//            result = fillSubFeature(result);
        }




        List<Long> totals =
                jdbcTemplate.query(
                        queryCount,
                        params,
                        (res, rowId) -> res.getLong(1)
                );

        long total = ArraysUtil.isNullOrEmpty(totals) ? 0 : totals.get(0);

        DataTableResponse<ProductResponse> response = new DataTableResponse<>(result, total);

        return response;
    }

    public List<ProductResponse> searchPromoteProduct(long promoteId, int customerId, long addressId) {

        AddressEntity currentAddress =
                addressRepository.findFirstByAccount_IdAndIdAndDeletedIsFalse(customerId, addressId);

        LatLngData currentLatLng = currentAddress == null ? new LatLngData() : new LatLngData(currentAddress);

        List<Long> currentAreaProviders = providerComponent.getAllProviderInRegional(currentLatLng)
                .stream().map(BaseEntity::getId).collect(Collectors.toList());

        HashMap<String, Object> params = new HashMap<>();
        params.put("promoteId", promoteId);
        params.put("providerId", ArraysUtil.isNullOrEmpty(currentAreaProviders) ? 0 : currentAreaProviders.get(0));

        String queryPart = "" +
                "FROM product_promote prp " +
                "INNER JOIN promote pro ON prp.promote_id = pro.id AND pro.id =:promoteId AND  pro.deleted = FALSE " +
                "INNER JOIN product_provider pp ON prp.product_provider_id = pp.id AND pp.deleted = FALSE " +
                "INNER JOIN product_price_last prc ON pp.id = prc.product_provider_id AND pp.deleted = FALSE " +
                "INNER JOIN account prv ON pp.provider_id = prv.id " + (addressId == -1 ? "" : " AND prv.id = (:providerId) ") +
                "INNER JOIN product p ON pp.product_id = p.id " +
                "INNER JOIN category ctg ON p.category_id = ctg.id and ctg.deleted = false " +
                "LEFT JOIN category ctgp ON ctgp.id = ctg.parent_id and ctgp.deleted = false " +
                "INNER JOIN brand b ON p.brand_id = b.id  and b.deleted = false " +
                "INNER JOIN unit unt ON p.unit_id = unt.id " +
                "INNER JOIN account pr ON pr.id = pp.provider_id " +
                "LEFT  JOIN regional rgp ON pr.region_id = rgp.id " +
                "WHERE prv.deleted = FALSE " +
                "AND ctg.deleted = FALSE " +
                "AND unt.deleted = FALSE " +
                "AND prp.deleted = FALSE " +
//                "AND pp.product_provider_existence = TRUE " +
                "GROUP BY  " +
                "pp.id , " +
                "p.id , " +
                "p.name , " +
                "pp.rate , " +
                "pp.product_provider_existence , " +
                "p.short_description , " +
                "p.full_description , " +
                "prv.id , " +
                "prv.name , " +
                "rgp.id, " +
                "rgp.name , " +
                "unt.id , " +
                "unt.name , " +
                "ctg.id , " +
                "ctg.name , " +
                "ctgp.id , " +
                "ctgp.name , " +
                "pp.min_allow , " +
                "pp.max_allow , " +
                "p.unit_step , " +
                "pp.prepare_hour , " +
                "prc.primitive_amount , " +
                "prc.off_percent , " +
                "prc.off_price , " +
                "prc.final_amount, " +
                "pp.product_count, " +
                "b.name, " +
                "b.id " +
                "ORDER BY pp.product_provider_existence DESC ";


        String query = "" +
                "SELECT " +
                "pp.id productProviderId, " +
                "p.name pName, " +
                "p.id productId, " +
                "pp.rate pRate, " +
                "pp.product_provider_existence pExistence, " +
                "p.short_description pShortDesc, " +
                "p.full_description pFullDesc, " +
                "prv.id prvId, " +
                "prv.name prvName, " +
                "rgp.id prvRegId, " +
                "rgp.name prvRegName, " +
                "unt.id untId, " +
                "unt.name untName, " +
                "ctg.id ctgId, " +
                "ctg.name ctgName, " +
                "ctgp.id ctgpId, " +
                "ctgp.name ctgpName, " +
                "pp.min_allow pMinAllow, " +
                " pp.product_count productCount, " +
                "pp.max_allow pMaxAllow, " +
                "p.unit_step pUnitStep, " +
                "pp.prepare_hour pPrepareHour, " +
                "prc.primitive_amount prcPrimitive, " +
                "prc.off_percent prcOffPercent, " +
                "prc.off_price prcOffPrice, " +
                "prc.final_amount prcFinalAmount, " +
                "b.name bName, " +
                "b.id bId " +
                queryPart ;

        List<ProductResponse> result =
                jdbcTemplate.query(
                query,
                params,
                (res, rowId) ->
                        new ProductResponse(
                                res.getLong("productProviderId"),
                                res.getLong("productId"),
                                res.getString("pName"),
                                res.getFloat("pRate"),
                                res.getBoolean("pExistence"),
                                res.getString("pShortDesc"),
                                res.getString("pFullDesc"),
                                new IdName(
                                        res.getLong("prvId"),
                                        res.getString("prvName")
                                ),
                                new IdName(
                                        res.getLong("prvRegId"),
                                        res.getString("prvRegName")
                                ),
                                new IdName(
                                        res.getLong("untId"),
                                        res.getString("untName")
                                ),
                                new IdName(
                                        res.getLong("ctgId"),
                                        res.getString("ctgName")
                                ),
                                new IdName(
                                        res.getLong("ctgpId"),
                                        res.getString("ctgpName")
                                ),
                                res.getDouble("pMinAllow"),
                                res.getDouble("pMaxAllow"),
                                res.getDouble("pUnitStep"),
                                res.getLong("pPrepareHour"),
                                res.getLong("prcPrimitive"),
                                res.getFloat("prcOffPercent"),
                                res.getLong("prcOffPrice"),
                                res.getLong("prcFinalAmount"),
                                null,
                                null,
                                res.getObject("bName") == null ? new IdName() : new IdName(res.getLong("bId"), res.getString("bName")),
                                res.getLong("productCount")
                                )
        );
        result = fillImageProduct(result);

        return result;

    }

    private List<ProductResponse> fillImageProduct(List<ProductResponse> result) {
        List<Long> ids =
                        result.stream().map(ProductResponse::getProductId).collect(Collectors.toList());
        String query =
                "SELECT " +
                        "ppi.product_id pid, " +
                        "ppi.image_id img " +
                        "FROM product_image ppi " +
                        "WHERE ppi.deleted = FALSE AND ppi.product_id in (:ids) ";
        List<IdNameType> img = ArraysUtil.isNullOrEmpty(ids) ? new ArrayList<>() :
                jdbcTemplate.query(
                query,
                new MapSqlParameterSource("ids", ids),
                (res, rowId) -> new IdNameType(
                        res.getLong("pid"),
                        "",
                        res.getLong("img")
                )
        );

        Map<Long, List<Long>> imgMap = img.stream()
                .collect(
                        Collectors.groupingBy(
                                IdNameType::getId
                        )
                ).entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                v -> v.getValue().stream().map(IdNameType::getType).collect(Collectors.toList())
                        )
                );

        result = result.stream()
                .peek(
                        p -> p.setImages(
                                imgMap.getOrDefault(
                                        p.getProductId(),
                                        new ArrayList<>()
                                )
                        )
                ).collect(
                        Collectors.toList()
                );


        return result;


    }

    private List<ProductResponse> fillImageTag(List<ProductResponse> result) {
        List<Long> ids =
                result.stream().map(ProductResponse::getProductId).collect(Collectors.toList());

        String query =
                "SELECT " +
                        "ptp.product_id pid, " +
                        "pt.id tid, " +
                        "pt.name tnm " +
                        "FROM product_tag ptp " +
                        "INNER JOIN tag pt on ptp.tag_id = pt.id " +
                        "WHERE ptp.deleted = FALSE AND pt.deleted = FALSE AND ptp.product_id in (:ids) ";

        List<IdNameType> img = jdbcTemplate.query(
                query,
                new MapSqlParameterSource("ids", ids),
                (res, rowId) -> new IdNameType(
                        res.getLong("pid"),
                        res.getString("tnm"),
                        res.getLong("tid")
                )
        );

        Map<Long, List<IdName>> imgMap = img.stream()
                .collect(
                        Collectors.groupingBy(
                                IdNameType::getId
                        )
                ).entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                v -> v.getValue().stream().map(m2 -> new IdName(m2.getType(), m2.getName())).collect(Collectors.toList())
                        )
                );

        result = result.stream()
                .peek(
                        p -> p.setTags(
                                imgMap.getOrDefault(
                                        p.getProductId(),
                                        new ArrayList<>()
                                )
                        )
                ).collect(
                        Collectors.toList()
                );

        return result;


    }


//    private List<ProductResponse> fillSubFeature(List<ProductResponse> result) {
//        List<Integer> ids =
//                result.stream().map(ProductResponse::getId).collect(Collectors.toList());
//
//        String query =
//                "SELECT " +
//                        "pps.product_provider_id pid, " +
//                        "pf.id fid, " +
//                        "pf.category_id cid, " +
//                        "pf.name fnm, " +
//                        "psf.id sid, " +
//                        "psf.name snm, " +
//                        "pps.amount sAmount, " +
//                        "pps.changed_by_unit sUnt " +
//                        "FROM product_product_sub_feature pps " +
//                        "INNER JOIN product_category_sub_feature psf ON pps.product_sub_feature_id = psf.id " +
//                        "INNER JOIN product_category_feature pf ON psf.feature_id = pf.id " +
//                        "WHERE pps.deleted = FALSE AND pps.product_provider_id in (:ids) " +
//                        "ORDER BY pps.amount ASC";
//
//        List<ProductFeatureResponseQuery> feature = jdbcTemplate.query(
//                query,
//                new MapSqlParameterSource("ids", ids),
//                (res, rowId) -> new ProductFeatureResponseQuery(
//                        res.getLong("pid"),
//                        res.getLong("fid"),
//                        res.getLong("cid"),
//                        res.getString("fnm"),
//                        new ProductSubFeatureResponse(
//                                res.getLong("sid"),
//                                res.getString("snm"),
//                                res.getLong("sAmount"),
//                                res.getBoolean("sUnt")
//                        )
//                )
//        );
//
//        Map<Integer, List<ProductFeatureResponse>> featureMap = feature.stream()
//                .collect(
//                        Collectors.groupingBy(
//                                ProductFeatureResponseQuery::getProductId
//                        )
//                ).entrySet()
//                .stream()
//                .collect(
//                        Collectors.toMap(
//                                Map.Entry::getKey,
//                                v -> v.getValue().stream().map(ProductFeatureResponse::new).collect(Collectors.toList())
//                        )
//                );
//
//
//        result = result.stream().peek( p -> {
//
//            Map<Integer, List<ProductFeatureResponse>> mFeature = featureMap.getOrDefault(
//                    p.getId(),
//                    new ArrayList<>()
//            ).stream()
//                    .collect(Collectors.groupingBy(
//                            ProductFeatureResponse::getId
//                    ));
//
//            List<ProductFeatureResponse> res = new ArrayList<>();
//            for (Map.Entry<Integer, List<ProductFeatureResponse>> kv : mFeature.entrySet()) {
//                ProductFeatureResponse fet = kv.getValue().get(0);
//                List<ProductSubFeatureResponse> val = new ArrayList<>();
//                for (ProductFeatureResponse pkv : kv.getValue()) {
//                    val.addAll(pkv.getSubFeature());
//                }
//
//                ProductFeatureResponse cur = new ProductFeatureResponse(
//                        fet.getId(),
//                        fet.getCategory(),
//                        fet.getName(),
//                        val
//                );
//                res.add(cur);
//            }
//            p.setSubFeatureResponse(res);
//
//        }).collect(Collectors.toList());
//
//
//        return result;
//
//
//    }


    private String getOrderQuery(long orderId) {

        switch (ProductOrderOption.getById(orderId)){
            case PRICE_UP:
                return "ORDER BY pp.product_provider_existence DESC, prc.final_amount DESC ";

            case PRICE_DOWN:
                return "ORDER BY pp.product_provider_existence DESC, prc.final_amount ASC ";

            case VIEW:
                return "ORDER BY pp.product_provider_existence DESC, pp.product_provider_order ASC ";

            case OFF:
                return "ORDER BY pp.product_provider_existence DESC, prc.off_price DESC ";

            case NEW:
                return "ORDER BY pp.product_provider_existence DESC, pp.insert_date DESC ";

            case SAIL:
            default:
                return "ORDER BY pp.product_provider_existence DESC";
        }
    }



///////                                                      PANNNEL


    public DataTableResponse<ProductProviderDigestResponse> getProductProviderPanelList(int page, int limit, String search, long providerId, long brand, long category) {
        HashMap<String, Object> params = new HashMap<>();

        if ( limit != 0) {
            PageRequest pageRequest = PageRequest.of(page, limit);
            params.put("pageLimit", pageRequest.getPageSize());
            params.put("pageOffset", pageRequest.getOffset());
        }else{
            params.put("pageLimit", 10000);
            params.put("pageOffset", 0);
        }

        params.put("ct", category);
        params.put("brand", brand);
        params.put("proId", providerId);
        params.put("searchStr", "%" + search + "%");

        String queryPart = "FROM product_provider pp " +
                "INNER JOIN product_price_last ppl ON ppl.product_provider_id = pp.id AND ppl.deleted = FALSE " +
                "INNER JOIN product p ON pp.product_id = p.id AND p.deleted = FALSE " +
                "INNER JOIN account pro ON pp.provider_id = pro.id AND pro.dtype = 'ProviderEntity' AND pro.deleted = FALSE " +
                (providerId == 0 ? " " :
                        "AND pro.id = :proId "
                ) +
                "INNER JOIN unit un ON un.id = p.unit_id  " +
                "INNER JOIN category ctg ON ctg.id = p.category_id  And ctg.deleted = FALSE  " +
                "INNER JOIN brand b ON b.id = p.brand_id And b.deleted = FALSE  " +
                "WHERE  pp.deleted = FALSE " +
                (brand == 0 ? " ": " AND b.id = :brand " )+
                (category == 0 ? " ": "AND (ctg.id = (:ct) OR ctg.parent_id = (:ct)  )  " )+
                (StringUtil.isNullOrEmpty(search) ? "" :
                        "AND ( p.name LIKE :searchStr " +
                                "OR p.short_description LIKE :searchStr) ");

        String query =
                "SELECT " +
                        "pp.id ppId," +
                        "pp.product_provider_order ordSh, " +
                        "pp.rate rt, " +
                        "pp.product_provider_existence exist, " +
                        "ppl.primitive_amount primitive_amount , " +
                        "ppl.off_percent off_percent , " +
                        "ppl.off_price off_price , " +
                        "ppl.final_amount final_amount, " +
                        "p.id  pId, " +
                        "p.name pNm, " +
                        "pro.id proId, " +
                        "pro.name proNm, " +
                        "p.name productName, " +
                        "p.short_description dsc, " +
                        "ctg.name category, " +
                        "b.name brand, " +
                        "un.name unit " +
                        queryPart +
                        "ORDER BY pp.product_provider_order , pp.product_provider_existence " +
                        " LIMIT :pageLimit OFFSET :pageOffset";


        List<ProductProviderDigestResponse> result = jdbcTemplate.query(
                query,
                params,
                (res, rowId) ->
                        new ProductProviderDigestResponse(
                                res.getLong("ppId"),
                                new IdName(res.getString("proNm"), res.getLong("proId")),
                                new IdName(res.getString("pNm"), res.getLong("pId")),
                                res.getString("brand"),
                                res.getString("category"),
                                res.getString("unit"),
                                res.getLong("primitive_amount"),
                                res.getFloat("off_percent"),
                                res.getLong("off_price"),
                                res.getLong("final_amount"),
                                res.getFloat("rt"),
                                res.getBoolean("exist"),
                                res.getFloat("ordSh")
                        )
        );
        String queryCount = "SELECT count(*) FROM (SELECT 1 " + queryPart + ") mmg ";
        List<Long> totals =
                jdbcTemplate.query(
                        queryCount,
                        params,
                        (res, rowId) -> res.getLong(1)
                );

        long total = ArraysUtil.isNullOrEmpty(totals) ? 0L : totals.get(0);


        //providerCategory
        List<Long> productIds =
                result.stream().map(m -> m.getProduct().getId()).collect(Collectors.toList());

        if (!ArraysUtil.isNullOrEmpty(productIds)) {
            result = fillProductProviderPanelImagesProduct(result, productIds);
        }

        DataTableResponse<ProductProviderDigestResponse> dateTable = new DataTableResponse<>();
        dateTable.setData(result);
        dateTable.setRecordsTotal(total);
        dateTable.setRecordsFiltered(total);


        return dateTable;
    }

    private List<Long> findAllParentForSelectedCategory(List<Long> category) {
        List<CategoryEntity> cat =
                categoryRepository.findAllByDeletedIsFalse();

        List<Long> tempCat = new ArrayList<>();
        tempCat.addAll(category);

        for (CategoryEntity sub : cat) {
            if (tempCat.contains(sub.getId()) || (sub.getParent() != null && tempCat.contains(sub.getParent().getId())) ) {
                tempCat.add(sub.getId());
            }
        }
        return tempCat.stream().distinct().collect(Collectors.toList());
    }


    private List<ProductProviderDigestResponse> fillProductProviderPanelImagesProduct(List<ProductProviderDigestResponse> result, List<Long> productIds) {
        String queryProvider =
                "SELECT " +
                        "pi.product_id pid, " +
                        "pi.image_id img, " +
                        "pi.image_order ord " +
                        "FROM product_image pi " +
                        "WHERE pi.product_id in (:ids) " +
                        "AND pi.deleted = FALSE ";

        Map<Long, List<ProductProviderImageResponse>> productProvidersImageMap = jdbcTemplate.query(
                queryProvider,
                new MapSqlParameterSource("ids", productIds),
                (res, rowId) ->
                        new ProductProviderImageResponse(
                                res.getLong("pid"),
                                res.getLong("img"),
                                res.getLong("ord")

                        )
        ).stream().collect(Collectors.groupingBy(ProductProviderImageResponse::getProduct));

        return result.stream().peek(e -> {
            List<Long> cur = productProvidersImageMap.getOrDefault(
                    e.getProduct().getId(), new ArrayList<>()
            ).stream().map(
                    ProductProviderImageResponse::getImage
            ).collect(Collectors.toList());

            if (ArraysUtil.isNullOrEmpty(cur)) {
                e.setImage(0);
            } else {
                e.setImage(cur.get(0));
            }
        }).collect(Collectors.toList());
    }




    public ProviderProductResponse detail(IdName request, long providerId) throws BehtaShopException {

        if(request == null || request.getId() == 0){
            throw new BehtaShopException("محصول مورد نظر پیدا نشد.");
        }

        ProductProviderEntity productProvider =
                productProviderRepository.findFirstByIdAndDeletedIsFalse(request.getId());
        if (productProvider == null) {
            throw new BehtaShopException("محصول مورد نظر پیدا نشد.");
        }
        if(providerId != 0 && productProvider.getProvider().getId() != providerId){
            throw new BehtaShopException("عدم اجازه.");
        }

        ProductProviderPriceEntity price =
                productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdOrderByIdDesc(request.getId());

        ProviderProductResponse response =
                new ProviderProductResponse(productProvider, price);

        return response;
    }






    public IdName add(ProviderProductRestRequest request) throws BehtaShopException {

        checkProductProviderAddOrEdit(request);

        ProviderEntity provider = request.getProviderId() == 0 ? null :
                providerRepository.findFirstById(request.getProviderId());
        if (provider == null) {
            throw new BehtaShopException("تامین کننده انتخاب نشده است.");
        }

        ProductProviderEntity oldProductProvider =
                productProviderRepository.findFistByProduct_IdAndProviderIdAndDeletedIsFalse(request.getProductId(), request.getProviderId());
        if (oldProductProvider != null){
            throw new BehtaShopException("قبلا برای این تامیین کننده این محصول به ثبت رسیده است.");
        }

        ProductEntity product = request.getProductId() == 0 ? null :
                productRepository.findFirstById(request.getProductId());
        if (provider == null) {
            throw new BehtaShopException("محصول انتخاب نشده است.");
        }




        ProductProviderEntity productProvider =
                new ProductProviderEntity(
                        product,
                        provider,
                        request.getShowOrder(),
                       true,
                        request.getMinAllow(),
                        request.getMaxAllow(),
                        request.getPrepareHour(),
                        request.getProductCount()
                        );


        productProviderRepository.save(productProvider);



        ProductProviderPriceEntity productProviderPrice = saveProductProviderPrice( productProvider, request );

        return new IdName();
    }

    public IdName edit(ProviderProductRestRequest request) throws BehtaShopException {
//
        if(request == null || request.getProductProviderId() == 0){
            throw new BehtaShopException("محصول مورد نظر پیدا نشد.");
        }

        ProductProviderEntity productProvider =
                productProviderRepository.findFirstByIdAndDeletedIsFalse(request.getProductProviderId());
        if(productProvider == null){
            throw new BehtaShopException("محصول مورد نظر پیدا نشد.");
        }

        checkProductProviderAddOrEdit(request);

        updateProductProvider(productProvider, request);

        productProviderRepository.save(productProvider);


        ProductProviderPriceEntity productProviderPrice = updateProductProviderPrice( productProvider, request );

        return null;

    }

    private void checkProductProviderAddOrEdit(ProviderProductRestRequest p) throws BehtaShopException {

        if(p.getProviderId() == 0){
            throw new BehtaShopException("خطا در ثبت!! تامین‌کننده نامعتبر می‌باشد!!");
        }


        if (p.getProviderId() == 0) {
            throw new BehtaShopException("لطفا تامین کننده را مشخص کنید");

        }
        if (p.getProductId() == 0) {
            throw new BehtaShopException("لطفا محصول را مشخص کنید");
        }

        if(p.getPrimitiveAmount() < 0 ){
            throw new BehtaShopException("قیمت انتخابی برای محصول نامعتبر می‌باشد!");
        }

        if(p.getMinAllow() < 1){
            throw new BehtaShopException(" حداقل میزان سفارش نامعتبر می‌باشد.");
        }

        if(p.getMaxAllow() > 500){
            throw new BehtaShopException("حداکثر میزان سفارش نامعتبر می‌باشد.");
        }

        if (p.getMinAllow() >= p.getMaxAllow()){
            throw new BehtaShopException("مقادیر حداقلی و حداکثری را به درستی پر نمایید.");
        }


        if(p.getPrepareHour() < 1){
            throw new BehtaShopException("حداقل زمان آماده سازی یک ساعت می‌باشد.");
        }

        if(p.getPrepareHour() > 96){
            throw new BehtaShopException("حداکثر زمان آماده سازی چهار روز (۹۶ ساعت) می‌باشد.");
        }
    }




    private void updateProductProvider(ProductProviderEntity productProvider, ProviderProductRestRequest request){

        ProviderEntity provider =
                providerRepository.findFirstById(request.getProviderId());
        productProvider.setProvider(provider);

        productProvider.setProductProviderOrder(request.getShowOrder());
        productProvider.setMinAllow(request.getMinAllow());
        productProvider.setMaxAllow(request.getMaxAllow());
        productProvider.setPrepareHour(request.getPrepareHour());
        productProvider.setStorageCount(request.getStorageCount());
        productProvider.setProductCount(request.isChangeExistCount() ? request.getStorageCount() : request.getProductCount());
    }



    private ProductProviderPriceEntity saveProductProviderPrice(final ProductProviderEntity newProductProvider, ProviderProductRestRequest request ) {

        long primitive = request.getPrimitiveAmount();
        long offPrice = (long) (request.getPrimitiveAmount() * request.getDiscountPercent() / 100.0);
        long finalAmount = primitive - offPrice;

        ProductProviderPriceEntity newPrice = new ProductProviderPriceEntity(
                primitive,
                offPrice,
                request.getDiscountPercent(),
                finalAmount,
                newProductProvider
        );

        productPriceRepository.save( newPrice );

        return newPrice;

    }




    private ProductProviderPriceEntity updateProductProviderPrice(final ProductProviderEntity productProvider, ProviderProductRestRequest request ) throws BehtaShopException {

        ProductProviderPriceEntity productPrice =
                productPriceRepository.findFirstByDeletedIsFalseAndProductProvider_IdAndProductProvider_DeletedIsFalseOrderByIdDesc(productProvider.getId());
        if(productPrice == null){
            throw new BehtaShopException("خطا در اطلاعات محصول! قیمت یافت نشد!");
        }
        productPrice.setDeleted(true);
        productPriceRepository.save( productPrice );

        return saveProductProviderPrice(productProvider, request);

    }







    public void changeActive(IdActiveRequest request, int providerId) throws BehtaShopException {

        ProductProviderEntity product = productProviderRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        if (product == null) {
            throw new BehtaShopException("پیدا نشد.");
        }

        if(providerId != 0 && providerId != product.getProvider().getId()){
            throw new BehtaShopException("عدم اجازه");
        }

        if(product.isProductProviderExistence() != request.isActive()){
            product.setProductProviderExistence(request.isActive());
            productProviderRepository.save(product);
        }
    }

    public void delete(IdRequest request, int providerId) throws BehtaShopException {

        ProductProviderEntity productProvider =
                productProviderRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        if(productProvider == null){
            throw new BehtaShopException("پیدا نشد.");
        }

        productProvider.setDeleted(true);
        productProviderRepository.save(productProvider);

    }


    public List<IdName> getAll() throws BehtaShopException {

      return productRepository.findAllByDeletedIsFalse()
                .stream().map( m-> new IdName(m.getId(), m.getName())).collect(Collectors.toList());
    }

    public void clearInCartProduct(long customerId) {
        List<CustomerOrderEntity> customerOrderList =
                customerOrderRepository.findAllByCustomer_IdAndDeletedIsFalse( customerId );

        if (!ArraysUtil.isNullOrEmpty(customerOrderList)) {
            customerOrderList = customerOrderList.stream().peek(p -> p.setDeleted(true)).collect(Collectors.toList());
            customerOrderRepository.saveAll(customerOrderList);
        }
    }

}
