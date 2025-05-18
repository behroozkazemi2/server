package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.response.SuggestionResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.component.SuggestionComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer
 * Project Name: behta-server
 * 19 January 2019
 **/
public class SuggestionApi extends BaseApi {


    @Autowired
    private SuggestionComponent suggestionComponent;



    @ApiAction( value = "app.customer.suggestion.list", tokenRequired = false )
    public ApiResponseBody list ( ) throws ApiActionException {

        List<SuggestionResponse> list = suggestionComponent.getListOfSuggestionProduct();

        return new ApiResponseBody<>().ok( list );

    }


}
