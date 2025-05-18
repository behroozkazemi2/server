package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.modelOption
 * Project server
 * 10 October 2018 13:08
 **/


public enum BillStatusOption {

    INITIALIZE(0),
    WAIT_FOR_PAY(1),
    PAYED(2),
    SENDING(3),
    DELIVERED(4),
    CANCELED(5);




    private final long id;

    BillStatusOption( long id ) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static BillStatusOption getById( long id) throws BehtaShopException {
        for(BillStatusOption option : BillStatusOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "وضعیت نا معتبر." );
    }

}
