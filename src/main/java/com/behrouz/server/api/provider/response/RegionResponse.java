package com.behrouz.server.api.provider.response;

import com.behrouz.server.rest.request.IdName;

import java.util.List;

public class RegionResponse extends IdName {


    private List<IdName> regions;


    public RegionResponse() {
    }

    public RegionResponse(int id, String name, List<IdName> regions) {
        super(id, name);
        this.regions = regions;
    }

    public List<IdName> getRegions() {
        return regions;
    }
    public void setRegions(List<IdName> regions) {
        this.regions = regions;
    }
}
