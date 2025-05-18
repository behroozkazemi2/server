package com.behrouz.server.exception;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.exception
 * Project Koala Server
 * 09 September 2018 16:49
 **/
public class BehtaShopException extends Throwable {


    private final String description;


    public BehtaShopException(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static void make(String message) throws BehtaShopException {
        throw new BehtaShopException( message );
    }


}