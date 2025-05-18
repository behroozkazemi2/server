package com.behrouz.server.component;

import com.behrouz.server.api.provider.response.ProductDigestResponse;
import com.behrouz.server.api.provider.response.ProductResponse;
import com.behrouz.server.model.brand.BrandEntity;
import com.behrouz.server.model.global.CategoryEntity;
import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.model.global.UnitEntity;
import com.behrouz.server.model.product.*;
import com.behrouz.server.repository.*;
import com.behrouz.server.rest.constant.DataTableResponse;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.rest.request.InformationRestRequest;
import com.behrouz.server.rest.response.InformationCategoryRestResponse;
import com.behrouz.server.rest.response.InformationRestResponse;
import com.behrouz.server.rest.response.ProductProviderImageResponse;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.product.*;
import com.behrouz.server.modelOption.BillStatusOption;
import com.behrouz.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.component
 * Project Koala Server
 * 10 September 2018 10:56
 **/
@Component
public class ProductComponent {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductTagRepository productTagRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ProductProviderRepository productProviderRepository;

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private InformationCategoryRepository informationCategoryRepository;

    @Autowired
    private ProductInformationRepository productInformationRepository;


    public DataTableResponse<ProductDigestResponse> getList(int page, int limit, String search, long brand, long category) {

        PageRequest pageRequest = PageRequest.of(page, limit);

        HashMap<String, Object> params = new HashMap<>();
        params.put("ct", category);
        params.put("brand", brand);
        params.put("pageOffset", pageRequest.getOffset());
        params.put("pageLimit", pageRequest.getPageSize());
        params.put("searchStr", "%" + search + "%");

        String queryPart = "FROM product p " +
                "INNER JOIN unit un ON un.id = p.unit_id  " +
                "INNER JOIN category ctg ON ctg.id = p.category_id  And ctg.deleted = FALSE  " +
                "INNER JOIN brand b ON b.id = p.brand_id And b.deleted = FALSE  " +
                "WHERE  p.deleted = FALSE " +
                (brand == 0 ? " ": " AND b.id = :brand " )+
                (category == 0 ? " ": " AND ctg.id = :ct " )+
                (StringUtil.isNullOrEmpty(search) ? "" :
                        "AND ( p.name LIKE :searchStr " +
                                "OR p.short_description LIKE :searchStr) ");

        String query =
                "SELECT " +
                        "p.id id, " +
                        "p.name productName, " +
                        "p.short_description dsc, " +
                        "ctg.name category, " +
                        "b.name brand, " +
                        "un.name unit " +
                        queryPart +
                        "ORDER BY p.id desc " +
                        "LIMIT :pageLimit OFFSET :pageOffset";


        List<ProductDigestResponse> result = jdbcTemplate.query(
                query,
                params,
                (res, rowId) ->
                        new ProductDigestResponse(
                                res.getLong("id"),
                                0,
                                res.getString("productName"),
                                res.getString("dsc"),
                                res.getString("category"),
                                res.getString("brand"),
                                res.getString("unit")
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
                result.stream().map(ProductDigestResponse::getId).collect(Collectors.toList());

        if (!ArraysUtil.isNullOrEmpty(productIds)) {
            result = fillImagesProduct(result, productIds);
        }

        DataTableResponse<ProductDigestResponse> dateTable = new DataTableResponse<>();
        dateTable.setData(result);
        dateTable.setRecordsTotal(total);
        dateTable.setRecordsFiltered(total);


        return dateTable;
    }
    public DataTableResponse<ProductDigestResponse> getBestSellsList(int page, int limit, String search, long brand, long category) {

        PageRequest pageRequest = PageRequest.of(page, limit);

        HashMap<String, Object> params = new HashMap<>();
        params.put("ct", category);
        params.put("brand", brand);
        params.put("pageOffset", pageRequest.getOffset());
        params.put("pageLimited", pageRequest.getPageSize());
        params.put("searchStr", "%" + search + "%");

        String queryPart =
                        "FROM bill b " +
                        "INNER JOIN bill_product_provider bpp ON b.id = bpp.bill_id AND bpp.deleted = FALSE " +
                        "INNER JOIN product_provider pp ON pp.id = bpp.product_provider_id  " +
                        "INNER JOIN product p ON p.id = pp.product_id  " +
                        "INNER JOIN bill_bill_status_last bll ON bll.bill_id = b.id " +
                        "INNER JOIN bill_status bl ON bl.id = bll.bill_status_id " +
                        "WHERE b.deleted = FALSE " +
                        " AND bl.id IN ( " +
                                  BillStatusOption.SENDING.getId() + ", "
                                + BillStatusOption.DELIVERED.getId() +" " +
                                ") "+
                        "GROUP BY p.id, p.name " +
                        "";

        String query =
                " SELECT " +
                        "  p.id productId," +
                        "  p.name productName, " +
                        "  count(bpp.id) cnt " +
                queryPart +
                " " +
                "LIMIT :pageLimited " +
                "OFFSET :pageOffset ";



        List<ProductDigestResponse> result = jdbcTemplate.query(
                query,
                params,
                (res, rowId) ->
                        new ProductDigestResponse(
                                res.getLong("productId"),
                                0,
                                res.getString("productName"),
                                res.getLong("cnt")
                        )
        );


        String queryCount = " SELECT count(*) FROM (SELECT 1 " + queryPart + ") mmg ";
        List<Long> totals =
                jdbcTemplate.query(
                        queryCount,
                        params,
                        (res, rowId) -> res.getLong(1)
                );

        long total = ArraysUtil.isNullOrEmpty(totals) ? 0L : totals.get(0);


        //providerCategory
        List<Long> productIds =
                result.stream().map(ProductDigestResponse::getId).collect(Collectors.toList());

        if (!ArraysUtil.isNullOrEmpty(productIds)) {
            result = fillImagesProduct(result, productIds);
        }

        DataTableResponse<ProductDigestResponse> dateTable = new DataTableResponse<>();
        dateTable.setData(result);
        dateTable.setRecordsTotal(total);
        dateTable.setRecordsFiltered(total);


        return dateTable;
    }

    private List<ProductDigestResponse> fillImagesProduct(List<ProductDigestResponse> result, List<Long> productIds) {
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
                    e.getId(), new ArrayList<>()
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

    public ProductResponse detail(IdName request, long providerId) throws BehtaShopException {

        if (request == null || request.getId() == 0) {
            throw new BehtaShopException("محصول مورد نظر پیدا نشد.");
        }

        ProductEntity product =
                productRepository.findFirstByIdAndDeletedIsFalse(request.getId());
        if (product == null) {
            throw new BehtaShopException("محصول مورد نظر پیدا نشد.");
        }

        ProductResponse response =
                new ProductResponse(product);


        List<ProductImageEntity> images = productImageRepository.findAllByProduct_IdAndProduct_DeletedIsFalseAndDeletedIsFalse(product.getId());
        if (images == null) {
            throw new BehtaShopException("خطا در اطلاعات محصول! عکسی یافت نشد!");
        }
        response.setImages(images.stream().map(e -> e.getImage().getId()).collect(Collectors.toList()));


        List<ProductTagEntity> tags = productTagRepository.findAllByProductIdAndDeletedIsFalse(product.getId());
        if (tags == null) {
            throw new BehtaShopException("خطا در اطلاعات محصول! تگ یافت نشد!");
        }
        response.setTags(tags.stream().map(e -> new IdName(e.getTag().getId(), e.getTag().getName())).collect(Collectors.toList()));
        response.setTagsId(tags.stream().map(m -> m.getTag().getId()).collect(Collectors.toList()));
        return response;
    }

    public IdName add(ProductResponse request) throws BehtaShopException {

        checkProductAddOrEdit(request);

        UnitEntity unit = request.getUnitId() == 0 ? null :
                unitRepository.findFirstByIdAndDeletedIsFalse(request.getUnitId());
        if (unit == null) {
            throw new BehtaShopException("واحد انتخاب نشده است.");
        }


        CategoryEntity category = request.getCategoryId() == 0 ? null :
                categoryRepository.findFirstByIdAndDeletedIsFalse(request.getCategoryId());
        if (category == null) {
            throw new BehtaShopException("دسته انتخاب نشده است.");
        }


        BrandEntity brand = request.getBrandId() == 0 ? null :
                brandRepository.findFirstByIdAndDeletedIsFalse(request.getBrandId());

        if (brand == null) {
            throw new BehtaShopException("برند انتخاب نشده است.");
        }


        ProductEntity productEntity =
                new ProductEntity(
                        request.getName(),
                        request.getShortDescription(),
                        request.getFullDescription(),
                        unit,
                        request.getUnitStep(),
                        brand,
                        category
                );


        productRepository.save(productEntity);


//        saveProductProviderFeature(request , productProvider);


        List<ProductImageEntity> productProviderImages = saveProductImages(productEntity, request.getImages());

        List<ProductTagEntity> tags = saveProductTags(productEntity, request.getTagsId());


        return new IdName(productEntity.getId(), productEntity.getName());
    }


    public IdName edit(ProductResponse request) throws BehtaShopException {
//
        if (request == null || request.getId() == 0) {
            throw new BehtaShopException("محصول مورد نظر پیدا نشد.");
        }

        ProductEntity productEntity = productRepository.findFirstByIdAndDeletedIsFalse(request.getId());
        if (productEntity == null) {
            throw new BehtaShopException("محصول مورد نظر پیدا نشد.");
        }

        checkProductAddOrEdit(request);

        UnitEntity unit =
                request.getUnitId() == 0 ? null :
                        unitRepository.findFirstByIdAndDeletedIsFalse(request.getUnitId());
        if (unit == null) {
            throw new BehtaShopException("واحد انتخاب نشده است.");
        }
        productEntity.setProductUnit(unit);

        CategoryEntity category = request.getCategoryId() == 0 ? null :
                categoryRepository.findFirstByIdAndDeletedIsFalse(request.getCategoryId());
        if (category == null) {
            throw new BehtaShopException("دسته انتخاب نشده است.");
        }
        productEntity.setCategory(category);


        BrandEntity brand = request.getBrandId() == 0 ? null :
                brandRepository.findFirstByIdAndDeletedIsFalse(request.getBrandId());

        if (brand == null) {
            throw new BehtaShopException("دسته انتخاب نشده است.");
        }
        productEntity.setBrand(brand);


        updateProduct(productEntity, request);


        productRepository.save(productEntity);


//        updateProductProviderFeature(request , productProvider);


        updateProductProviderImages(productEntity, request.getImages());

        updateProductTags(request.getTagsId(), productEntity);


        return new IdName(productEntity.getId(), productEntity.getName());

    }

    private void checkProductAddOrEdit(ProductResponse p) throws BehtaShopException {


        if (ArraysUtil.isNullOrEmpty(p.getImages())) {
            throw new BehtaShopException("ثبت محصول نیازمند عکس می‌باشد! عکسی یافت نشد!");
        }

        if (StringUtil.isNullOrEmpty(p.getName()) || p.getName().length() > 50) {
            throw new BehtaShopException("ثبت محصول نیازمند نام می‌باشد! نام وارد شده نامعتبر می‌باشد.");
        }

        if (StringUtil.isNullOrEmpty(p.getShortDescription()) || p.getShortDescription().length() > 250) {
            throw new BehtaShopException("ثبت محصول نیازمند توضیحات کوتاه می‌باشد! توضیحات کوتاه وارد شده نامعتبر می‌باشد.");
        }

//        if(StringUtil.isNullOrEmpty(p.getFullDescription()) || p.getFullDescription().length() > 500){
//            throw new HashtagMarketException("ثبت محصول نیازمند توضیحات می‌باشد! توضیحات وارد شده نامعتبر می‌باشد.");
//        }

        if (p.getCategoryId() == 0) {
            throw new BehtaShopException("دسته بندی مورد نظر یافت نشد!");
        }

        if (ArraysUtil.isNullOrEmpty(p.getTagsId())) {
            throw new BehtaShopException("ثبت محصول نیازمند تگ می‌باشد! تگی یافت نشد!");
        }


        if (p.getUnitStep() == 0) {
            throw new BehtaShopException("مقدار قابل افزایش به درستی وارد نشده");
        }

    }

    private void updateProduct(ProductEntity product, ProductResponse request) {

        product.setName(request.getName());
        product.setShortDescription(request.getShortDescription());
        product.setFullDescription(request.getFullDescription());
        product.setUnitStep(request.getUnitStep());

    }


    private List<ProductImageEntity> saveProductImages(final ProductEntity product, List<Long> images) throws BehtaShopException {

        List<ProductImageEntity> list = new ArrayList<>();

        if (images == null || images.isEmpty()) {
            throw new BehtaShopException("عکسی برای ثبت محصول وجود ندارد!");
        }


        for (Long id : images) {

            ImageEntity image = imageRepository.findFirstById(id);
            if (image == null) {
                throw new BehtaShopException("عکس انتخاب شده وجود ندارد!");
            }

            ProductImageEntity productImage = new ProductImageEntity(
                    product,
                    image
            );

            productImageRepository.save(productImage);
            list.add(productImage);
        }

        return list;
    }

    private void updateProductProviderImages(final ProductEntity productEntity, List<Long> images) throws BehtaShopException {

        List<ProductImageEntity> list = new ArrayList<>();

        if (images == null || images.isEmpty()) {
            return;
        }

        List<ProductImageEntity> imageOlds = productImageRepository.findAllByProduct_IdAndProduct_DeletedIsFalseAndDeletedIsFalse(productEntity.getId());
        if (imageOlds == null) {
            throw new BehtaShopException("خطا در اطلاعات محصول! عکسی یافت نشد!");
        }
        List<Long> imageOldId = imageOlds.stream().map(e -> e.getImage().getId()).collect(Collectors.toList());

        for (Long i : images) {

            if (imageOldId.contains(i)) {
                //exist
                imageOldId.remove(i);
            } else {
                //new
                ImageEntity image = imageRepository.findFirstById(i);
                if (image == null) {
                    throw new BehtaShopException("عکس انتخاب شده وجود ندارد!");
                }

                ProductImageEntity productProviderImage = new ProductImageEntity(
                        productEntity,
                        image
                );

                productImageRepository.save(productProviderImage);
                list.add(productProviderImage);
            }
        }

        List<ProductImageEntity> deletedP = imageOlds.stream().filter(e -> imageOldId.contains(e.getImage().getId())).collect(Collectors.toList());
        for (ProductImageEntity i : deletedP) {
            i.setDeleted(true);
            productImageRepository.save(i);
        }

    }


    private List<ProductTagEntity> saveProductTags(ProductEntity productEntity, List<Long> tagIdList) throws BehtaShopException {

        List<ProductTagEntity> list = new ArrayList<>();

        if (ArraysUtil.isNullOrEmpty(tagIdList)) {
            return list; // no tags
        }


        List<TagEntity> tags = tagRepository.findAllByIdInAndDeletedIsFalse(tagIdList);

        if (tags == null) {
            throw new BehtaShopException("تگ وارد شده یافت نشد.");
        }

        list =
                tags
                        .stream()
                        .map(e ->
                                new ProductTagEntity(e, productEntity)
                        )
                        .collect(Collectors.toList());


        productTagRepository.saveAll(list);

        return list;
    }

    private void updateProductTags(List<Long> tags, ProductEntity productEntity) throws BehtaShopException {

        List<ProductTagEntity> list = new ArrayList<>();

        if (ArraysUtil.isNullOrEmpty(tags)) {
            return; // no tags
        }

        List<ProductTagEntity> oldTags =
                productTagRepository.findAllByProductIdAndDeletedIsFalse(productEntity.getId())
                        .stream().peek(p -> p.setDeleted(true)).collect(Collectors.toList());
        productTagRepository.saveAll(oldTags);


        List<ProductTagEntity> newTags =
                tagRepository.findAllByIdInAndDeletedIsFalse(tags)
                        .stream().map(m -> new ProductTagEntity(m, productEntity)).collect(Collectors.toList());
        productTagRepository.saveAll(newTags);

        if (tags == null) {
            throw new BehtaShopException("خطا در اطلاعات محصول! تگ یافت نشد!");
        }


    }

    public void delete(IdRequest request, int providerId) throws BehtaShopException {

        ProductEntity product =
                productRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        if (product == null) {
            throw new BehtaShopException("پیدا نشد.");
        }

        product.setDeleted(true);
        productRepository.save(product);


        List<ProductProviderEntity> pps =
                productProviderRepository.findAllByProduct_IdAndDeletedIsFalse(product.getId()).stream().peek(p -> p.setDeleted(true)).collect(Collectors.toList());
        productProviderRepository.saveAll(pps);

    }

    public DataTableResponse<InformationCategoryRestResponse> getInformationCategoryList(int page, int length, String search) {

        PageRequest pageRequest = PageRequest.of(page, length);

        HashMap<String, Object> params = new HashMap<>();
        params.put("pageOffset", pageRequest.getOffset());
        params.put("pageLimit", pageRequest.getPageSize());
        params.put("searchStr", "%" + search + "%");

        String queryPart = "FROM information_category p " +
                "WHERE  p.deleted = FALSE " +
                (StringUtil.isNullOrEmpty(search) ? "" :
                        "AND ( p.name LIKE :searchStr ");

        String query =
                "SELECT " +
                        "p.id id, " +
                        "p.name, " +
                        "p.show_order " +
                        queryPart +
                        "ORDER BY show_order " +
                        "LIMIT :pageLimit OFFSET :pageOffset " +
                        "";


        List<InformationCategoryRestResponse> result = jdbcTemplate.query(
                query,
                params,
                (res, rowId) ->
                        new InformationCategoryRestResponse(
                                res.getLong("id"),
                                res.getString("name"),
                                res.getDouble("show_order")
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
        DataTableResponse<InformationCategoryRestResponse> dateTable = new DataTableResponse<>();
        dateTable.setData(result);
        dateTable.setRecordsTotal(total);
        dateTable.setRecordsFiltered(total);
        return dateTable;
    }

    public DataTableResponse<InformationRestResponse> getProductInformationList(int page, int length, String search, long infoCategoryId, long productId) {

        PageRequest pageRequest = PageRequest.of(page, length);

        HashMap<String, Object> params = new HashMap<>();
        params.put("pageOffset", pageRequest.getOffset());
        params.put("infoCategoryId", infoCategoryId);
        params.put("productId", productId);
        params.put("pageLimit", pageRequest.getPageSize());
        params.put("searchStr", "%" + search + "%");

        String queryPart = "FROM product_information p " +
                "INNER JOIN information_category ic ON ic.id = p.information_category_id AND ic.deleted = FALSE " +
                "WHERE p.deleted = FALSE " +
                "AND p.product_id = :productId " +
                "AND p.information_category_id = :infoCategoryId " +
                (StringUtil.isNullOrEmpty(search) ? "" :
                        "AND ( p.name LIKE :searchStr ");

        String query =
                "SELECT " +
                        "p.product_id, " +
                        "ic.id icid, " +
                        "ic.name icnm, " +
                        "p.id id, " +
                        "p.name, " +
                        "p.value, " +
                        "p.show_order " +
                        queryPart +
                        "ORDER BY p.show_order  " +
                        "LIMIT :pageLimit OFFSET :pageOffset " +
                        "";


        List<InformationRestResponse> result = jdbcTemplate.query(
                query,
                params,
                (res, rowId) ->
                        new InformationRestResponse(
                                res.getLong("id"),
                                res.getString("name"),
                                res.getString("value"),
                                res.getLong("product_id"),
                                res.getString("icnm"),
                                res.getLong("icid"),
                                res.getDouble("show_order")
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
        DataTableResponse<InformationRestResponse> dateTable = new DataTableResponse<>();
        dateTable.setData(result);
        dateTable.setRecordsTotal(total);
        dateTable.setRecordsFiltered(total);
        return dateTable;
    }

    public void saveProductInformation(InformationRestRequest request) throws BehtaShopException {

        InformationCategoryEntity inc =
                informationCategoryRepository.findFirstByIdAndDeletedIsFalse(request.getInformationCategoryId());

        if (inc == null)
            throw new BehtaShopException("خطا ، دسته یافت نشد");


        ProductEntity pr =
                productRepository.findFirstByIdAndDeletedIsFalse(request.getProductId());

        if (pr == null)
            throw new BehtaShopException("خطا ، محصول یافت نشد");

        ProductInformationEntity entity = request.getId() == 0 ? new ProductInformationEntity() :
                productInformationRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        if (entity == null && request.getId() != 0)
            throw new BehtaShopException("خطا ، یافت نشد");

        entity.setInformationCategory(inc);
        entity.setValue(request.getValue());
        entity.setShowOrder(request.getShowOrder());
        entity.setName(request.getName());
        entity.setProduct(pr);

        productInformationRepository.save(entity);

    }


    public void deleteProductInformation(IdRequest request) throws BehtaShopException {

        ProductInformationEntity old =
                productInformationRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        if (old == null)
            throw new BehtaShopException("خطا ، یافت نشد");

        old.setDeleted(true);

        productInformationRepository.save(old);

    }

    public InformationRestResponse productInformationDetail(IdRequest request) throws BehtaShopException {

        ProductInformationEntity entity = request.getId() == 0 ? new ProductInformationEntity() :
                productInformationRepository.findFirstByIdAndDeletedIsFalse(request.getId());

        return new InformationRestResponse(entity);
    }

    public List<InformationRestResponse> getProductInformationCategoryList(IdRequest request, int customerId) {

        HashMap<String, Object> params = new HashMap<>();
        params.put("productId", request.getId());

        String queryPart = "FROM product_information p " +
                "INNER JOIN information_category ic ON ic.id = p.information_category_id AND ic.deleted = FALSE " +
                "WHERE p.deleted = FALSE " +
                "AND p.product_id = :productId ";
        String query =
                "SELECT " +
                        "p.product_id, " +
                        "ic.id icid, " +
                        "ic.name icnm, " +
                        "p.id id, " +
                        "p.name, " +
                        "p.value, " +
                        "p.show_order " +
                        queryPart +
                        "ORDER BY p.show_order  " +
                        "";

        return jdbcTemplate.query(
                query,
                params,
                (res, rowId) ->
                        new InformationRestResponse(
                                res.getLong("id"),
                                res.getString("name"),
                                res.getString("value"),
                                res.getLong("product_id"),
                                res.getString("icnm"),
                                res.getLong("icid"),
                                res.getDouble("show_order")
                        )
        );
    }
}
