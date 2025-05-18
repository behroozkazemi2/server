package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.modelOption
 * Project server
 * 09 October 2018 14:50
 **/
public enum ProductProviderOption {

    EXIST(1),
    NOT_EXIST(2);

    private final int id;

    ProductProviderOption( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ProductProviderOption getById( int id) throws BehtaShopException {
        for(ProductProviderOption option : ProductProviderOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "وضعیت کالا نا معتبر." );
    }

}
