package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.data.LatLngData;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.utils.city.CityLocationUtils;
import com.behrouz.server.utils.city.CityOption;

public class RegionApi extends BaseApi {

    @ApiAction(value = "app.customer.region.find", tokenRequired = false)
    public ApiResponseBody find(@ApiActionParam(nullable = false) LatLngData data, int customerId){

        CityOption city = CityLocationUtils.city(data.getLat(), data.getLng());

        return new ApiResponseBody<>().ok(new IdName(city.getId(), city.getName()));
    }
}
