package com.behrouz.server.api.customer;

import com.behrouz.server.utils.ArraysUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.request.CallSupportRequest;
import com.behrouz.server.api.customer.response.AllCategoriesResponse;
import com.behrouz.server.api.customer.response.ApplicationUpdateResponse;
import com.behrouz.server.api.customer.response.InviteCodeResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.HttpCode;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.component.ConstantComponent;
import com.behrouz.server.model.CallSupportEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.global.CategoryEntity;
import com.behrouz.server.repository.CallSupportRepository;
import com.behrouz.server.repository.CategoryRepository;
import com.behrouz.server.rest.constant.IdRequest;
import com.behrouz.server.rest.constant.RequestDetailResponse;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.strategy.StrategyGenerator;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.values.AndroidValues;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer
 * Project server
 * 27 September 2018 10:41
 **/

/**
 *  1 ) Invite Information -> get inviting code and link, how many time used
 *  {@link #getInviteCode(int)}
 *
 */
public class ConstantApi extends BaseApi {



    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CallSupportRepository callSupportRepository;


    @Autowired
    private ConstantComponent constantComponent;





    @ApiAction( value = "app.customer.constant.inviteCode.get" )
    public ApiResponseBody getInviteCode(int customerId ) throws ApiActionException {

        CustomerEntity customer = getCustomerById( customerId );

        if ( StringUtil.isNullOrEmpty( customer.getInvitingCode() ) ){
            throw new ApiActionException(
                    HttpCode.INTERNAL_SERVER_ERROR,
                    "مشکل در سمت سرور"
            );
        }


//        List<CustomerEntity> usedInviteCode =
//                allUsedInviteCode( customer.getId() );


        return new ApiResponseBody<>().ok(
                new InviteCodeResponse(
                        customer.getInvitingCode(),
                        StrategyGenerator.generateInvitingCodeLink( customer.getInvitingCode() ),
//                        usedInviteCode.size()
                        0
                )
        );
    }

    @ApiAction( value = "app.customer.constant.category.all", tokenRequired = false)
    public ApiResponseBody getAllCategories(IdRequest catId) throws ApiActionException {

        List<AllCategoriesResponse> allCategories =
                categoryRepository.findAllByDeletedIsFalse().stream().sorted(Comparator.comparingDouble(CategoryEntity::getShowOrder)).collect(Collectors.toList())
                        .stream().map( m -> new AllCategoriesResponse(
                                new RequestDetailResponse(m.getId(), m.getName(),(m.getImage() != null ? m.getImage().getId() : 0 ), m.getDescription(),m.getIcon()),
                        m.getParent() == null ? null :new RequestDetailResponse(m.getParent().getId(), m.getParent().getName(), (m.getParent().getImage() != null ? m.getParent().getImage().getId() : 0 ) , m.getParent().getDescription(),m.getIcon()),
                        null
                )).collect(Collectors.toList());

        if ( allCategories == null ) {
            throw new ApiActionException(
                    HttpCode.INTERNAL_SERVER_ERROR,
                    "دسته بندی یافت نشد!"
            );
        }

            if (catId.getId() == 0) {

                List<AllCategoriesResponse> results = addMenuAndSubChildren(allCategories);
            return new ApiResponseBody<>().ok(results);
        }else {
            List<AllCategoriesResponse> parentResponse = new ArrayList<>();
            List<AllCategoriesResponse> response = new ArrayList<>();

            List<AllCategoriesResponse> firstParents =
                    allCategories.stream().filter(f -> f.getCurrent().getId() == catId.getId()).collect(Collectors.toList());
            if (firstParents.get(0).getParent() != null) {
                firstParents.add(new AllCategoriesResponse(firstParents.get(0).getParent(), null, null));
                firstParents.remove(0);

            }
            for (AllCategoriesResponse currentCategory : firstParents) {
                currentCategory.setChildren(fillSubCategories(allCategories, currentCategory));
                response.add(currentCategory);
            }
            if (firstParents.get(0).getParent() != null){
                parentResponse.add( new AllCategoriesResponse(
                        new RequestDetailResponse(firstParents.get(0).getParent().getId(), firstParents.get(0).getParent().getName(), firstParents.get(0).getParent().getImageId() , firstParents.get(0).getParent().getDescription(),firstParents.get(0).getParent().getIcon()),
                        null ,
                        response
                ));;

            }
            return new ApiResponseBody<>().ok(parentResponse.size() != 0 ? parentResponse : response);
        }
    }

    public List<AllCategoriesResponse> addMenuAndSubChildren(List<AllCategoriesResponse> allCategories){
        List<AllCategoriesResponse> response = new ArrayList<>();

        List<AllCategoriesResponse> mainCategory =
                allCategories.stream().filter(
                        f -> f.getParent() == null
                ).collect(Collectors.toList());

        Map<Long, List<AllCategoriesResponse>> subCat =
                allCategories.stream().filter(
                        f -> f.getParent() != null
                ).collect(
                        Collectors.groupingBy(
                                k -> k.getParent().getId()
                        )
                );

        for(AllCategoriesResponse par : mainCategory){

            List<AllCategoriesResponse> children =
                    getSubCategory(subCat, par);

            par.setChildren(children);
            response.add(par);
        }

        return response;
    }


    public List<AllCategoriesResponse> getSubCategory(Map<Long, List<AllCategoriesResponse>> subCatMap, AllCategoriesResponse current ){

        List<AllCategoriesResponse> childResponse = new ArrayList<>();

        List<AllCategoriesResponse> children =
                subCatMap.get(current.getCurrent().getId());

        if (!ArraysUtil.isNullOrEmpty(children)){

            for ( AllCategoriesResponse subCh : children) {

                List<AllCategoriesResponse> subChChild =
                        getSubCategory(subCatMap, subCh);

                subCh.setChildren(subChChild);

                childResponse.add(subCh);
            }
        }
        return childResponse;
    }

    @ApiAction( value = "app.customer.constant.tag.all", tokenRequired = false)
    public ApiResponseBody getAllTags(@ApiActionParam(nullable = false) IdName request) throws ApiActionException {


        List<RequestDetailResponse> allTags =
                constantComponent.getTagListByCategory(
                        request.getId() == 0 ? null :
                        Collections.singletonList(
                                request.getId()
                        )
                );

        if ( allTags == null ) {
            throw new ApiActionException(
                    HttpCode.INTERNAL_SERVER_ERROR,
                    "تگ یافت نشد!"
            );
        }

        return new ApiResponseBody<>().ok(allTags);
    }

    @ApiAction( value = "app.customer.constant.tags.all", tokenRequired = false)
    public ApiResponseBody getAllTags(@ApiActionParam(nullable = false) List<Long> request) throws ApiActionException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            request = mapper.readValue(mapper.writeValueAsString(request) , TypeFactory.defaultInstance().constructCollectionType(List.class , Integer.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<RequestDetailResponse> allTags = constantComponent.getTagListByCategory(request);

        if ( allTags == null ) {
            throw new ApiActionException(
                    HttpCode.INTERNAL_SERVER_ERROR,
                    "تگ یافت نشد!"
            );
        }

        return new ApiResponseBody<>().ok(allTags);
    }




    @ApiAction( value = "app.customer.constant.update", tokenRequired = false )
    public ApiResponseBody getLastVersionDetail( ApplicationUpdateResponse request ) throws ApiActionException {


        return new ApiResponseBody <>().ok( new ApplicationUpdateResponse(
                AndroidValues.APK_VERSION_CODE,
                AndroidValues.APK_LINK,
                AndroidValues.APK_VERSION_NAME,
                AndroidValues.APK_FORCE_UPDATE
        ) );
    }


    @ApiAction( value = "app.customer.constant.callSupport" )
    public ApiResponseBody callSupport(CallSupportRequest callSupportRequest, int customerId ) throws ApiActionException {

        CustomerEntity customer = getCustomerById( customerId );

        CallSupportEntity callSupport =
                new CallSupportEntity(
                        customer,
                        callSupportRequest
//                        callSupportStatusRepository.findFirstById( CallSupportStatusOption.RECEIVED.getId() )
                );

        callSupportRepository.save( callSupport );


        return new ApiResponseBody().ok();
    }


    private List<AllCategoriesResponse> fillSubCategories(List<AllCategoriesResponse> all, AllCategoriesResponse current) {
        List<AllCategoriesResponse> found = getCategoriesByParentId(all, current.getCurrent().getId());
        List<AllCategoriesResponse> result = new ArrayList<>();

        if (found.size() != 0) {
            for (AllCategoriesResponse cur : found) {
                List<AllCategoriesResponse> sub = fillSubCategories(all, cur);

                    cur.setChildren(sub);

                result.add(cur);
            }
        }

        if (result.size() != 0) {
            current.setChildren(result) ;
        }

        return current.getChildren();
    }
    private List<AllCategoriesResponse> getCategoriesByParentId(List<AllCategoriesResponse> all, long parentId) {
        return all.stream().filter(m -> m.getParent() != null &&  m.getCurrent().getId() != parentId && m.getParent().getId() == parentId).collect(Collectors.toList());
    }



}