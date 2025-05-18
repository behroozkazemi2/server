package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.modelOption
 * Project server
 * 09 October 2018 14:50
 **/
public enum SpecialProductProviderStatusOption {

    SUBMIT(1),
    VIEW(2),
    CONFIRM(3),
    DELETE(4);

    private final int id;

    SpecialProductProviderStatusOption(int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static SpecialProductProviderStatusOption getById(int id) throws BehtaShopException {
        for(SpecialProductProviderStatusOption option : SpecialProductProviderStatusOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "وضعیت کالا نا معتبر." );
    }

}
