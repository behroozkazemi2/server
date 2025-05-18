package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.modelOption
 * Project Name: behta-server
 * 04 March 2019
 **/
public enum UnitOption {

    KG(1),
    G_50(2),
    G_100(3),
    NUMBER(4);

    private final int id;

    UnitOption( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UnitOption getById( int id) throws BehtaShopException {
        for(UnitOption option : UnitOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "واحد نا معتبر." );
    }

}
