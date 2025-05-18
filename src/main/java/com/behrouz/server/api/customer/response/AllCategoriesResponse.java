package com.behrouz.server.api.customer.response;



import com.behrouz.server.rest.constant.RequestDetailResponse;

import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api.customer.response
 * Project server
 * 30 September 2018 13:21
 **/
public class AllCategoriesResponse {
    private RequestDetailResponse current;
    private RequestDetailResponse parent;

    private List< AllCategoriesResponse > children;

    public AllCategoriesResponse() {
    }

    public AllCategoriesResponse(RequestDetailResponse current, RequestDetailResponse parent, List<AllCategoriesResponse> children) {
        this.current = current;
        this.parent = parent;
        this.children = children;
    }

    public RequestDetailResponse getParent() {
        return parent;
    }
    public void setParent( RequestDetailResponse parent ) {
        this.parent = parent;
    }


    public RequestDetailResponse getCurrent() {
        return current;
    }
    public void setCurrent(RequestDetailResponse current) {
        this.current = current;
    }


    public List<AllCategoriesResponse> getChildren() {
        return children;
    }
    public void setChildren(List<AllCategoriesResponse> children) {
        this.children = children;
    }
}
