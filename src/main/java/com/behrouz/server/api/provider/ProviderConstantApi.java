package com.behrouz.server.api.provider;


import com.behrouz.server.api.provider.request.TagAddRequest;
import com.behrouz.server.api.provider.response.TagResponse;
import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionFailureException;
import com.behrouz.server.base.exception.ApiActionNotFoundException;
import com.behrouz.server.component.ProductSettingComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.brand.BrandEntity;
import com.behrouz.server.model.global.CategoryEntity;
import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.repository.BrandRepository;
import com.behrouz.server.repository.CategoryRepository;
import com.behrouz.server.repository.ImageRepository;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.CategorySaveRestRequest;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.rest.response.CategoryRestResponse;
import com.behrouz.server.rest.response.CategorySaveRestResponse;
import com.behrouz.server.rest.response.digestList.UnitListDigestResponse;
import com.behrouz.server.utils.ArraysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProviderConstantApi extends ProviderBaseApi {


    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ProductSettingComponent productSettingComponent;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ImageRepository imageRepository;


    @ApiAction("app.provider.constant.category")
    public ApiResponseBody<List<IdName>> category(){

        String query =
                "SELECT cg.id cid, " +
                        "cg.name cname, " +
                        "cg.parent_id cgparId, " +
                        "cgp.name cgparNm " +
                        " FROM category cg " +
                        " LEFT JOIN category cgp ON cg.parent_id = cgp.id AND cgp.deleted = FALSE " +
                        " WHERE cg.deleted = FALSE  ORDER BY cg.id ";

        List<CategoryRestResponse> result = jdbcTemplate.query(
                query,
                (res, rowId) -> new CategoryRestResponse(
                        res.getLong("cid"),
                        res.getString("cname"),
                        new IdName(res.getLong("cgparId"), res.getString("cgparNm"))
                )
        );

        return new ApiResponseBody<>().ok(result);

    }



    @ApiAction("app.provider.constant.category.detail")
    public ApiResponseBody<CategorySaveRestResponse> categoryDetail(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws BehtaShopException {

        CategoryEntity category = request.getId() == 0 ? null :
                categoryRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        if (category == null && request.getId() != 0){
            throw new BehtaShopException( " مورد نظر یافت نشد!" );

        }

        if (category == null){
            category = new CategoryEntity();
        }
        return  new ApiResponseBody<>().ok(new CategorySaveRestResponse(category));
    }

    @ApiAction("app.provider.constant.category.add")
    public ApiResponseBody<Void> addCategory(@ApiActionParam(nullable = false) CategorySaveRestRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {
        int providerId =
                getProviderId(apiActionRequest, true);
        CategoryEntity category = request.getId() == 0 ? null :
                categoryRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        if(category == null && request.getId() != 0){
            throw new ApiActionNotFoundException("پیدا نشد");
        }
        if(category == null){
            category = new CategoryEntity();
        }
        category.setName(request.getName());
        ImageEntity image = imageRepository.findFirstByIdAndDeletedIsFalse(request.getImageId());
        category.setImage(image);
        category.setShowOrder(request.getShowOrder());
        category.setDescription(request.getDescription());
        CategoryEntity parent = request.getParent() == 0 ? null :
                categoryRepository.findFirstByIdAndDeletedIsFalse(request.getParent());
        category.setParent(parent);
        categoryRepository.save(category);

        return new ApiResponseBody().ok();
    }


    @ApiAction("app.provider.constant.category.delete")
    public ApiResponseBody<Void> categoryDelete(IdRequest idRequest, ApiActionRequest apiActionRequest) throws ApiActionException {

        CategoryEntity category =
                categoryRepository.findFirstByIdAndDeletedIsFalse(idRequest.getId());

        if (category == null) {
            throw new ApiActionNotFoundException("پیدا نشد.");
        }

        String query =
                "SELECT " +
                        "p.name " +
                        "FROM product p " +
                        "WHERE p.deleted = FALSE " +
                        "AND p.category_id = :cid ";

        List<String> result = jdbcTemplate.query(
                query,
                new MapSqlParameterSource("cid", idRequest.getId()),
                (res, rowId) -> res.getString(1)
        );

        if(!ArraysUtil.isNullOrEmpty(result)){
            throw new ApiActionNotFoundException(
                    "این دسته به محصول وصل می باشد. " + String.join("، ", result)
            );
        }

        category.setDeleted(true);

        categoryRepository.save(category);
        return new ApiResponseBody<>().ok(result);

    }



    @ApiAction("app.provider.constant.brand.add")
    public ApiResponseBody<Void> addBrand(@ApiActionParam(nullable = false) IdName request, ApiActionRequest apiActionRequest) throws ApiActionException {
        int providerId =
                getProviderId(apiActionRequest, true);



        BrandEntity brand = request.getId() == 0 ? null :
                brandRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        if(brand == null && request.getId() != 0){
            throw new ApiActionNotFoundException("پیدا نشد");
        }
        if(brand == null){
            brand = new BrandEntity();
        }
        brand.setName(request.getName());
        brandRepository.save(brand);

        return new ApiResponseBody().ok();
    }

    @ApiAction("app.provider.constant.brand.delete")
    public ApiResponseBody<Void> deleteBrand(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {
        int providerId =
                getProviderId(apiActionRequest, true);
        BrandEntity brand = request.getId() == 0 ? null :
                brandRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        brand.setDeleted(true);
        brandRepository.save(brand);

// TODO DELETE ALL PRODUCT WITH THIS BRAND ??

        return new ApiResponseBody().ok();
    }




    //<editor-fold desc="UNIT : GET , ADD , EDIT , DELETE">


    @ApiAction("app.provider.constant.tag")
    public ApiResponseBody<List<TagResponse>> tag(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {

        String query =
                "SELECT " +
                        "tg.id tid," +
                        "tg.name tnm " +
//                        "t.id tid , " +
//                        "t.name tnm " +
                        "FROM tag tg  " +
//                        "INNER JOIN product_tag tg ON tg.tag_id = tg.id " +
//                        "INNER JOIN tag t ON t.id = tg.tag_id AND t.deleted = false " +
                        "WHERE tg.deleted = FALSE " +
//                        (request.getId() == 0 ? "" : "AND tg.category_id = :tid ") +
                        "ORDER BY tg.name ";

        List<TagResponse> result = jdbcTemplate.query(
                query,
                new MapSqlParameterSource("tid", request.getId()),
                (res, rowId) -> new TagResponse(
                        res.getLong("tid"),
                        res.getString("tnm"),
                        new IdName(
                                res.getLong("tid"),
                                res.getString("tnm")
                        )
                )
        );

        Map<Long, List<TagResponse>> mCurrent = result.stream().collect(Collectors.groupingBy(IdName::getId));

        List<TagResponse> response = new ArrayList<>();
        for (Map.Entry<Long, List<TagResponse>> cur : mCurrent.entrySet()) {
            TagResponse c = cur.getValue().get(0);
            List<List<IdName>> cTag = cur.getValue().stream().map(TagResponse::getTags).collect(Collectors.toList());
            c.setTags(new ArrayList<>());
            for (List<IdName> idNames : cTag) {
                c.getTags().addAll(idNames);
            }

            response.add(c);
        }

        return new ApiResponseBody<>().ok(response);

    }

    @ApiAction("app.provider.constant.tag.add")
    public ApiResponseBody<IdName> addTag(@ApiActionParam(nullable = false) TagAddRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {
        int providerId =
                getProviderId(apiActionRequest, true);
        try {
            productSettingComponent.saveTag(request);
            return new ApiResponseBody().ok();
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
    }


    @ApiAction("app.provider.constant.tag.delete")
    public ApiResponseBody<IdName> deleteTag(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {
        int providerId =
                getProviderId(apiActionRequest, true);
        try {
            productSettingComponent.deleteTag(request);
            return new ApiResponseBody().ok();
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
    }

    //</editor-fold>


    //<editor-fold desc="UNIT : GET , ADD , EDIT , DELETE">

    @ApiAction("app.provider.constant.unit")
    public ApiResponseBody<List<UnitListDigestResponse>> unit(@ApiActionParam(nullable = false) IdRequest request){

        List<UnitListDigestResponse> result = productSettingComponent.getUnitsList();

        return new ApiResponseBody<>().ok(result);

    }

    @ApiAction("app.provider.constant.unit.add")
    public ApiResponseBody<IdName> addUnit(@ApiActionParam(nullable = false) UnitListDigestResponse request, ApiActionRequest apiActionRequest) throws ApiActionException {
        int providerId =
                getProviderId(apiActionRequest, true);
        try {
            productSettingComponent.saveUnit(request);
            return new ApiResponseBody().ok();
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
    }

    @ApiAction("app.provider.constant.unit.edit")
    public ApiResponseBody<IdName> editUnit(@ApiActionParam(nullable = false) UnitListDigestResponse request, ApiActionRequest apiActionRequest) throws ApiActionException {
        int providerId =
                getProviderId(apiActionRequest, true);
        try {
            productSettingComponent.saveUnit(request);
            return new ApiResponseBody().ok();
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
    }

    @ApiAction("app.provider.constant.unit.delete")
    public ApiResponseBody<IdName> deleteUnit(@ApiActionParam(nullable = false) IdRequest request, ApiActionRequest apiActionRequest) throws ApiActionException {
        int providerId =
                getProviderId(apiActionRequest, true);
        try {
            productSettingComponent.deleteUnit(request);
            return new ApiResponseBody().ok();
        } catch (BehtaShopException e) {
            throw new ApiActionFailureException(e.getDescription());
        }
    }

    //</editor-fold>

}
