package com.behrouz.server.rest.request;


import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.global.CategoryEntity;
import com.behrouz.server.model.global.UnitEntity;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.request
 * Project Name: behta-server
 * 27 May 2020
 **/
public class IdName {

    protected long id;

    protected String name;

    public IdName ( long id ) {
        this.id = id;
    }

    public IdName ( String name ) {
        this.name = name;
    }

    public IdName ( long id, String name ) {
        this.id = id;
        this.name = name;
    }


    public IdName ( String name, long id ) {
        this.id = id;
        this.name = name;
    }

    public IdName(UnitEntity productProviderUnit) {
        this.id = productProviderUnit.getId();
        this.name = productProviderUnit.getName();
    }

    public IdName(ProviderEntity provider) {
        this.id = provider.getId();
        this.name = provider.getName();
    }

    public IdName(CategoryEntity category) {
        this.id = category.getId();
        this.name = category.getName();
    }


    public long getId () {
        return id;
    }
    public void setId ( long id ) {
        this.id = id;
    }


    public String getName () {
        return name;
    }
    public void setName ( String name ) {
        this.name = name;
    }

    public IdName () {
    }
}
