package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.modelOption
 * Project Name: behta-server
 * 04 March 2019
 **/
public enum PromoteOption {

    Special(1);

    private final long id;

    PromoteOption(long id ) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static PromoteOption getById(long id) throws BehtaShopException {
        for(PromoteOption option : PromoteOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "واحد نا معتبر." );
    }

}
