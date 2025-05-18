package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.modelOption
 * Project Name: behta-server
 * 18 December 2018
 **/
public enum CallSupportStatusOption {

    RECEIVED(1),
    ANSWERED(2);


    private final int id;

    CallSupportStatusOption( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CallSupportStatusOption getById( int id) throws BehtaShopException {
        for(CallSupportStatusOption option : CallSupportStatusOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "وضعیت نا معتبر." );
    }

}
