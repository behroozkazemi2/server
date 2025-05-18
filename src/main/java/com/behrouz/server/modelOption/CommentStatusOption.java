package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.modelOption
 * Project Name: behta-server
 * 13 December 2018
 **/
public enum CommentStatusOption {

    CREATED(1),
    VERIFIED(2),
    DELETED(3);


    private final int id;

    CommentStatusOption( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CommentStatusOption getById( int id) throws BehtaShopException {
        for(CommentStatusOption option : CommentStatusOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "وضعیت نا معتبر." );
    }

}
