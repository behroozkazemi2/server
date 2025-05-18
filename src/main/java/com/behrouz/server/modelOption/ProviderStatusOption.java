package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.modelOption
 * Project server
 * 01 October 2018 12:46
 **/
public enum ProviderStatusOption {

    ACTIVE(1),
    NOT_ACTIVE(2),
    DELETED(3);


    private final int id;

    ProviderStatusOption( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ProviderStatusOption getById( int id) throws BehtaShopException {
        for(ProviderStatusOption option : ProviderStatusOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "وضعیت تامین کننده نا معتبر." );
    }


}
