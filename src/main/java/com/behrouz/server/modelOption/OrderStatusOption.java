package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.modelOption
 * Project Name: behta-server
 * 04 March 2019
 **/
public enum OrderStatusOption {

    NOT_SEEN(0),
    ACCEPTED(1),
    REJECTED(2);

    private final int id;

    OrderStatusOption(int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static OrderStatusOption getById(int id) throws BehtaShopException {
        for(OrderStatusOption option : OrderStatusOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "واحد نا معتبر." );
    }

}
