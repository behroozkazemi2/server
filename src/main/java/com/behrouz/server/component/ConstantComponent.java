package com.behrouz.server.component;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.global.CategoryEntity;
import com.behrouz.server.model.global.UnitEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.model.product.ProductTagEntity;
import com.behrouz.server.repository.*;
import com.behrouz.server.rest.constant.RequestDetailResponse;
import com.behrouz.server.rest.constant.SelectTwoResponse;
import com.behrouz.server.rest.request.PanelSearchRequest;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.modelOption.ActivityOption;
import com.behrouz.server.repository.*;
import com.behrouz.server.specification.CustomerSpecification;
import com.behrouz.server.specification.ProviderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.component
 * Project Koala Server
 * 24 September 2018 16:24
 **/

@Component
public class ConstantComponent {


    @Autowired
    private ProductTagRepository productTagRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProductProviderRepository productProviderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<SelectTwoResponse> getProviderCategories() {

        List<CategoryEntity> categories = categoryRepository.findAllByDeletedIsFalse();

        if ( categories == null ) {
            return new ArrayList<>();
        }

        return
                categories
                        .stream()
                        .map( e ->
                                new SelectTwoResponse(
                                        e.getId(),
                                        e.getName()
                                )
                        )
                        .collect( Collectors.toList());
    }

//    public List<SelectTwoGroupResponse> getProductCategories() {
//
//        List< CategoryEntity > categories = categoryRepository.findAllByParentIsNullAndDeletedIsFalse();
//
//        if ( categories == null ) {
//            return new ArrayList<>(  );
//        }
//
//
//        return
//                categories
//                        .stream()
//                        .map( e ->
//                                new SelectTwoGroupResponse(
//                                        e.getId(),
//                                        e.getName()
//                                )
//                        )
//                        .collect( Collectors.toList());
//    }

//    public List< SelectTwoResponse > getChildrenCategories(int categoryId) {
//
//        CategoryEntity category = categoryRepository.findFirstByIdAndDeletedIsFalse(categoryId);
//
//        if (category == null) {
//            return new ArrayList<>();
//        }
//
//        return getChildrenCategories(category);
//
//    }

//    private List< SelectTwoResponse > getChildrenCategories( CategoryEntity category ) {
//
//        List< CategoryEntity > children = categoryRepository.findAllByParent_IdAndDeletedIsFalse( category.getId() );
//
//        if ( children == null || children.isEmpty() ) {
//            return new ArrayList<>();
//        }
//
//
//        return
//                children
//                        .stream()
//                        .map( e ->
//                                new SelectTwoResponse(
//                                        e.getId(),
//                                        e.getName()
//                                )
//                        )
//                        .collect( Collectors.toList() );
//    }


    public List<SelectTwoResponse> getProductUnits() {

        List<UnitEntity> units = unitRepository.findAllByDeletedIsFalse();

        if ( units == null ) {
            return new ArrayList<>(  );
        }


        return units
                .stream()
                .map( e ->
                        new SelectTwoResponse(
                                e.getId(),
                                e.getName()
                        )
                )
                .collect( Collectors.toList());
    }

    public List<SelectTwoResponse> getTags() {

        List<ProductTagEntity> tags = productTagRepository.findAllByDeletedIsFalse();

        if ( tags.isEmpty() ) {
            return new ArrayList<>(  );
        }


        return
                tags
                        .stream()
                        .map( e ->
                                new SelectTwoResponse(
                                        e.getId(),
                                        e.getTag().getName()
                                )
                        )
                        .collect( Collectors.toList());
    }

    public List<SelectTwoResponse> getProductProviderList() {

        List<ProductProviderEntity> products = productProviderRepository.findAllByDeletedIsFalseAndProductProviderExistenceIsTrue();

        if ( products == null ) {
            return new ArrayList<>(  );
        }


        return products
                .stream()
                .map( e ->
                        new SelectTwoResponse(
                                e.getId(),
                                e.getProduct().getName()
                        )
                )
                .collect( Collectors.toList());
    }

    public List<SelectTwoResponse> getProviderList() {

        List<ProviderEntity> providers = providerRepository.findAllByDeletedIsFalseAndBannedIsFalseAndActiveIsTrue();

        if ( providers == null ) {
            return new ArrayList<>(  );
        }


        return providers
                .stream()
                .map( e ->
                        new SelectTwoResponse(
                                e.getId(),
                                e.getName()
                        )
                )
                .collect( Collectors.toList());
    }



    public List<RequestDetailResponse> searchInProviders(String name ) {
        // TODO jdbcTemplate

        List < ProviderEntity > searchResults =
                providerRepository.findAll(
                        ProviderSpecification.findAllBySearch(
                                new PanelSearchRequest( name, 1 )
                        )
                );


        return searchResults
                .stream()
                .map( e ->
                        new RequestDetailResponse(
                                e.getId(),
                                e.getName()
                        )
                )
                .collect(Collectors.toList());
    }

    public List<RequestDetailResponse> searchInProductProviders( int providerId ) throws BehtaShopException {

        List < ProductProviderEntity > productProviders =
                productProviderRepository.findAllByProvider_IdAndDeletedIsFalse( providerId );

        if ( productProviders == null || productProviders.isEmpty() ){
            throw new BehtaShopException( "کالا یافت نشد." );
        }


        return productProviders
                .stream()
                .map( e ->
                        new RequestDetailResponse(
                                e.getId(),
                                e.getProduct().getName()
                        )
                )
                .collect(Collectors.toList());
    }

    public List<RequestDetailResponse> searchInCustomer( String name ) {
        // TODO jdbcTemplate

        List <CustomerEntity> searchResults =
                customerRepository.findAll(
                        CustomerSpecification.searchByFirstNameAndLastNameAndMobile(
                                name, name, null, 1
                        )
                );


        return searchResults
                .stream()
                .map( e ->
                        new RequestDetailResponse(
                                e.getId(),
                                e.getFirstName() + " " + e.getLastName()
                        )
                )
                .collect(Collectors.toList());

    }

    public List<RequestDetailResponse> searchInCustomer() {
        return searchInCustomer(null);
    }


    public List<RequestDetailResponse> getTagListByCategory(List<Long> categoryIds){

        if(!ArraysUtil.isNullOrEmpty(categoryIds)){
            categoryIds.remove(0);
        }
        String query =
                "SELECT " +
                        "pt.id , " +
                        "pt.name " +
                        "FROM product_tag pt " +
                        "LEFT JOIN product_tag ptc on pt.id =  ptc.tag_id " +
                        "WHERE pt.deleted = FALSE " +
                        "and ptc.deleted = FALSE " +
//                        ( !ArraysUtil.isNullOrEmpty(categoryIds) ? "AND ptc.category_id in (:cid) " : "" ) +
                        "GROUP BY pt.id, pt.name " +
                        "ORDER BY pt.name ";


        return jdbcTemplate.query(
                query,
                new MapSqlParameterSource("cid", categoryIds),
                (res, rowId) -> new RequestDetailResponse(
                        res.getLong("id"),
                        res.getString("name")
                )
        );
    }

    public List<RequestDetailResponse> getActivityList() {

        List<RequestDetailResponse> list = new ArrayList<>();
        list.add(new RequestDetailResponse((long) ActivityOption.ProductActivity.getId(),ActivityOption.ProductActivity.getName()  ));
        list.add(new RequestDetailResponse((long)ActivityOption.ProviderActivity.getId() ,ActivityOption.ProviderActivity.getName()  ));
        list.add(new RequestDetailResponse((long)ActivityOption.CartActivity.getId() ,ActivityOption.CartActivity.getName()  ));
        list.add(new RequestDetailResponse((long)ActivityOption.OffCodeActivity.getId() ,ActivityOption.OffCodeActivity.getName()  ));
        list.add(new RequestDetailResponse((long)ActivityOption.ProductDetailActivity.getId() ,ActivityOption.ProductDetailActivity.getName()  ));

        return list;
    }
}

