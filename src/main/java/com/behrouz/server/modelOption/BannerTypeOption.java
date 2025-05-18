package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.modelOption
 * Project server
 * 10 October 2018 13:08
 **/


public enum BannerTypeOption {

    UP_SLIDER_MAIN_PAGE(1),
    PICTURE_NEAR_SLIDER_MAIN_PAGE(2),
    DOWN_PICTURE_MAIN_PAGE(3);




    private final long id;

    BannerTypeOption(long id ) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static BannerTypeOption getById(long id) throws BehtaShopException {
        for(BannerTypeOption option : BannerTypeOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "وضعیت نا معتبر." );
    }

}
