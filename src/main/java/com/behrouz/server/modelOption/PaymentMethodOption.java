package com.behrouz.server.modelOption;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.modelOption
 * Project server
 * 10 October 2018 13:08
 **/


public enum PaymentMethodOption {

    INTERNETI(1, "اینترنتی"),
    QYER_NAQDI(2, "غیر نقدی");


    private final int id;
    private final String  name;

    PaymentMethodOption(int id, String  name ) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public static PaymentMethodOption getById(int id)  {
        for(PaymentMethodOption option : PaymentMethodOption.values()){
            if(option.getId() == id){
                return option;
            }
        }
        return INTERNETI;
    }

}
