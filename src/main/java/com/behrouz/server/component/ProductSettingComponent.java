package com.behrouz.server.component;

import com.behrouz.server.api.provider.request.TagAddRequest;
import com.behrouz.server.model.global.CategoryEntity;
import com.behrouz.server.model.global.UnitEntity;
import com.behrouz.server.model.product.TagEntity;
import com.behrouz.server.repository.CategoryRepository;
import com.behrouz.server.repository.ProductTagRepository;
import com.behrouz.server.repository.TagRepository;
import com.behrouz.server.repository.UnitRepository;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.constant.RequestDetailResponse;
import com.behrouz.server.rest.request.NewSubsetCategoryRequest;
import com.behrouz.server.rest.response.digestList.UnitListDigestResponse;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.component
 * Project Koala Server
 * 25 September 2018 16:46
 **/
@Component
public class ProductSettingComponent {

    //<editor-fold desc="Autowired">

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductTagRepository productTagRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private TagRepository tagRepository;


    //</editor-fold>

    //<editor-fold desc="Category">

    public void saveCategory(RequestDetailResponse category) throws BehtaShopException {

        if( category.getId() != 0 ) {

            CategoryEntity categoryEntity = categoryRepository.findFirstByIdAndDeletedIsFalse( category.getId() );
            if (categoryEntity == null) {
                throw new BehtaShopException("داده مورد نظر موجود نمی‌باشد.");
            }

            categoryEntity.setName(category.getName());
            categoryRepository.save(categoryEntity);

        } else {

            CategoryEntity categoryEntity = new CategoryEntity(category.getName());
            categoryRepository.save(categoryEntity);

        }

    }

    public void deleteCategory(IdRequest categoryId) throws BehtaShopException {

        CategoryEntity categoryEntity = categoryRepository.findFirstByIdAndDeletedIsFalse(categoryId.getId());

        if (categoryEntity == null) {
            throw new BehtaShopException("دسته مورد نظر یافت نشد.");
        }

        categoryEntity.setDeleted(true);

        categoryRepository.save(categoryEntity);

    }

    public IdRequest addSubsetCategory(NewSubsetCategoryRequest subsetCategory) throws BehtaShopException {

        if ( subsetCategory.getId() != 0 ){

            throw new BehtaShopException("ویرایش غیرفعال می‌باشد.");

        }else {

            CategoryEntity parentCategory = categoryRepository.findFirstByIdAndDeletedIsFalse(subsetCategory.getParentId());

            if (parentCategory == null) {
                throw new BehtaShopException("دسته مورد نظر یافت نشد.");
            }

            CategoryEntity categoryEntity =
                    new CategoryEntity(
                            subsetCategory.getName()
                    );

            categoryRepository.save(categoryEntity);

            return new IdRequest(categoryEntity.getId());

        }


    }

    //</editor-fold>

    //<editor-fold desc="Tag">

    public void saveTag(TagAddRequest tagRequest) throws BehtaShopException {

        if(tagRequest == null){
            throw new BehtaShopException("درخواست نامعتبر");
        }
//
//        List<CategoryEntity> categories = ArraysUtil.isNullOrEmpty(tagRequest.getCategories()) ? null :
//                categoryRepository.findAllByIdInAndDeletedIsFalse(tagRequest.getCategories());
//
//        if (ArraysUtil.isNullOrEmpty(categories)){
//            throw new HashtagMarketException("دسته(ها) انتخاب شده معتبر نمی‌باشد.");
//        }

        TagEntity tagEntity;
        if (tagRequest.getId() != 0 ) {
            tagEntity = tagRepository.findFirstByIdAndDeletedIsFalse(tagRequest.getId());

            if (tagEntity == null) {
                throw new BehtaShopException("داده مورد نظر یافت نشد");
            }

            tagEntity.setName(tagRequest.getName());
            tagRepository.save(tagEntity);

        }else {

            tagEntity = new TagEntity(tagRequest);
            tagRepository.save(tagEntity);
        }

    }

    public void deleteTag(IdRequest tagId) throws BehtaShopException {

        if ( tagId == null ){
            throw new BehtaShopException("اطلاعات را وارد نمایید.");
        }

        TagEntity tagEntity = tagRepository.findFirstByIdAndDeletedIsFalse(tagId.getId());

        if (tagEntity == null) {
            throw new BehtaShopException("تگ مورد نظر یافت نشد.");
        }

        tagEntity.setDeleted(true);
        tagRepository.save(tagEntity);

    }

    //</editor-fold>

    //<editor-fold desc="Unit">

    public List<UnitListDigestResponse> getUnitsList() {

        List<UnitEntity> units = unitRepository.findAllByDeletedIsFalse();

        if ( units == null ) {
            return new ArrayList<>(  );
        }

        List<UnitListDigestResponse> responses = new ArrayList<>();

        for (UnitEntity e : units) {
            responses.add(
                    new UnitListDigestResponse(e)
            );
        }


        return responses;


    }

    public void saveUnit(UnitListDigestResponse tagRequest) throws BehtaShopException {

        if (tagRequest.getId() != 0 ) {

            UnitEntity unitEntity = unitRepository.findFirstByIdAndDeletedIsFalse(tagRequest.getId());

            if (unitEntity == null) {
                throw new BehtaShopException("داده مورد نظر یافت نشد");
            }


            unitEntity.update(tagRequest);
            unitRepository.save(unitEntity);



        }else {

            UnitEntity unitEntity = new UnitEntity(tagRequest);
            unitRepository.save(unitEntity);

        }

    }

    public void deleteUnit(IdRequest unitId) throws BehtaShopException {

        if ( unitId == null ){
            throw new BehtaShopException("اطلاعات را وارد نمایید.");
        }

        UnitEntity unitEntity = unitRepository.findFirstByIdAndDeletedIsFalse(unitId.getId());

        if (unitEntity == null) {
            throw new BehtaShopException("داده مورد نظر یافت نشد.");
        }

        unitEntity.setDeleted(true);
        unitRepository.save(unitEntity);

    }

    //</editor-fold>

}
