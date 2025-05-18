package com.behrouz.server.api.provider.response;

import com.behrouz.server.rest.request.IdName;

import java.util.List;

public class GroupCategoryResponse extends IdName {

    private List<Integer> regions;


    public GroupCategoryResponse(int id, String name, List<Integer> regions) {
        super(id, name);
        this.regions = regions;
    }

    public List<Integer> getRegions() {
        return regions;
    }
    public void setRegions(List<Integer> regions) {
        this.regions = regions;
    }
}
