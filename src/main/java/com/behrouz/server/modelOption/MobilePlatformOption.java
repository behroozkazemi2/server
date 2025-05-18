package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.modelOption
 * Project server
 * 22 September 2018 09:43
 **/
public enum MobilePlatformOption {

    ANDROID(1),
    IOS(2),
    WEB(2);


    private final int id;

    MobilePlatformOption( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static MobilePlatformOption getById( int id) throws BehtaShopException {
        for(MobilePlatformOption option : MobilePlatformOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "وضعیت دستگاه نا معتبر." );
    }


}
