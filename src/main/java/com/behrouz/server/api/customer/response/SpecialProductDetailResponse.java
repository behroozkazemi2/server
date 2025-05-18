package com.behrouz.server.api.customer.response;

import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.utils.ArraysUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/*
 * Request:            End Point
 *
 * IdRequest -> app.customer.special.detail
 */
public class SpecialProductDetailResponse extends SpecialProductDigestResponse{

    protected List<SpecialProviderSuggestion> suggestion;


    public SpecialProductDetailResponse() {
    }

    public SpecialProductDetailResponse(int id, List<Integer> images, IdName region, IdName category, IdName provider, IdName status, IdName customer, Date date, String description, List<SpecialProviderSuggestion> suggestion) {
        super(id, images, region, category, provider, status, customer, date, description);
        this.suggestion = suggestion;
    }

    public SpecialProductDetailResponse toToman(){
        this.suggestion = ArraysUtil.isNullOrEmpty(suggestion) ? null :
                suggestion.stream().map(SpecialProviderSuggestion::toToman).collect(Collectors.toList());
        return this;
    }


    public List<SpecialProviderSuggestion> getSuggestion() {
        return suggestion;
    }
    public void setSuggestion(List<SpecialProviderSuggestion> suggestion) {
        this.suggestion = suggestion;
    }
}
