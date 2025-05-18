package com.behrouz.server.api.provider.request;


import com.behrouz.server.rest.request.IdName;

import java.util.List;

public class BannerRestRequest {

    private List<IdName> images;
    private long type;

    public BannerRestRequest() {
    }

    public BannerRestRequest(List<IdName> images) {
        this.images = images;
    }
    public BannerRestRequest(List<IdName> images, long type) {
        this.images = images;
        this.type = type;
    }



    public long getType() {
        return type;
    }
    public void setType(long type) {
        this.type = type;
    }

    public List<IdName> getImages() {
        return images;
    }
    public void setImages(List<IdName> images) {
        this.images = images;
    }
}
