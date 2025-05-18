package com.behrouz.server.api.provider.response;

import com.behrouz.server.rest.request.IdName;

import java.util.List;

public class GroupProviderResponse extends IdName {

    private List<Integer> categories;

    public GroupProviderResponse(int id, String name, List<Integer> categories) {
        super(id, name);
        this.categories = categories;
    }

    public List<Integer> getCategories() {
        return categories;
    }
    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }
}
