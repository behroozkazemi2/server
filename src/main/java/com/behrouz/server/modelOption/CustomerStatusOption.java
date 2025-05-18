package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.modelOption
 * Project server
 * 01 October 2018 12:46
 **/
public enum CustomerStatusOption {

    ACTIVE(1),
    BANNED(2);

    private final int id;

    CustomerStatusOption(int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CustomerStatusOption getById(int id) throws BehtaShopException {
        for(CustomerStatusOption option : CustomerStatusOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "وضعیت تامین کننده نا معتبر." );
    }


}
