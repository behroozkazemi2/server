package com.behrouz.server.configuration;

import com.behrouz.server.component.ConstantComponent;
import com.behrouz.server.rest.constant.RequestDetailResponse;
import com.behrouz.server.rest.constant.SelectTwoGroupResponse;
import com.behrouz.server.rest.constant.SelectTwoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.configuration
 * Project Koala Server
 * 24 September 2018 16:25
 **/

@Configuration
public class ConstantConfiguration {

    @Autowired
    private ConstantComponent constantComponent;

    @Bean(name = "providerCategories")
    public ProviderCategoriesList providerCategoriesList() {
        return constantComponent::getProviderCategories;
    }
    interface ProviderCategoriesList {
        List<SelectTwoResponse> getList() ;
    }

    @Bean(name = "categories")
    public CategoriesList categoriesList() {
//        return constantComponent::getProductCategories;
        return  null;
    }
    interface CategoriesList {
        List<SelectTwoGroupResponse> getList() ;
    }

    @Bean(name = "tags")
    public TagsList tagsList() {
        return constantComponent::getTags;
    }
    interface TagsList {
        List<SelectTwoResponse> getList();
    }

    @Bean(name = "productUnits")
    public ProductUnitsList productUnitsList() {
        return constantComponent::getProductUnits;
    }
    interface ProductUnitsList {
        List<SelectTwoResponse> getList();
    }

    @Bean(name = "productProviderList")
    public ProductProviderList productProviderList() {
        return constantComponent::getProductProviderList;
    }
    interface ProductProviderList {
        List<SelectTwoResponse> getList();
    }

    @Bean(name = "providerList")
    public ProviderList providerList() {
        return () -> constantComponent.getProviderList();
    }
    interface ProviderList {
        List<SelectTwoResponse> getList();
    }

    @Bean(name = "customerList")
    public CustomerList customerList() { return constantComponent::searchInCustomer; }
    interface CustomerList {
        List<RequestDetailResponse> getList();
    }
    @Bean(name = "activityList")
    public ActivityList activityList() { return constantComponent::getActivityList; }
    interface ActivityList {
        List<RequestDetailResponse> getList();
    }

}
