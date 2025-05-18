package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.modelOption
 * Project server
 * 06 October 2018 14:35
 **/
public enum CustomerOrderHistoryOption {

    ADD(1),
    DELETE(2),
    FACTOR_CREATED(3),
    INCREASE(4),
    DECREASE(5);


    private final int id;

    CustomerOrderHistoryOption( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CustomerOrderHistoryOption getById( int id) throws BehtaShopException {
        for(CustomerOrderHistoryOption option : CustomerOrderHistoryOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "وضعیت نا معتبر." );
    }


}
