package com.behrouz.server.api.provider.response;

import com.behrouz.server.rest.request.IdName;

import java.util.Collections;
import java.util.List;

public class TagResponse extends IdName {

    private List<IdName> tags;


    public TagResponse() {
    }

    public TagResponse(long id, String name, List<IdName> tags) {
        super(id, name);
        this.tags = tags;
    }

    public TagResponse(long id, String name, IdName tag) {
        super(id, name);
        this.tags = Collections.singletonList(tag);
    }



    public List<IdName> getTags() {
        return tags;
    }
    public void setTags(List<IdName> tags) {
        this.tags = tags;
    }
}
