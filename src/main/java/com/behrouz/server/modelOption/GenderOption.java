package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.modelOption
 * Project Name: behta-server
 * 06 February 2019
 **/
public enum GenderOption {

    MALE(1),
    FEMALE(2);

    private final int id;

    GenderOption(int id ) {
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

        throw new BehtaShopException( "جنسیت نا معتبر." );
    }



}
