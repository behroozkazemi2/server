package com.behrouz.server.modelOption;

import com.behrouz.server.exception.BehtaShopException;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.modelOption
 * Project Name: behta-server
 * 06 December 2018
 **/
public enum PaymentOption {

    INITIALIZE(1),
    WAIT_FOR_PAY(2),
    PAYED(3),
    SENDING(5),
    RECEIVED(6),
    CANCELED(7);


    private final int id;

    PaymentOption( int id ) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static PaymentOption getById( int id) throws BehtaShopException {
        for(PaymentOption option : PaymentOption.values()){
            if(option.getId() == id){
                return option;
            }
        }

        throw new BehtaShopException( "وضعیت تامین کننده نا معتبر." );
    }














//    public static final double MIN_CHARGE_AMOUNT = 1000; // 100 toman
//
//    public static final double MAX_CHARGE_AMOUNT = 10 * 1000 * 1000; // 1 melyun
//
//    public static final String MERCHANT_ID‬‬ = "";
//
//    public static final String TERMINAL_ID = "";
//
//    public static final String EPG_KEY = "";
//
//    public static final String CALL_BACK_URL = "localhost:8030/online.melli.callback";
//
//    public final static String REQUEST_URL = "https://sadad.shaparak.ir/VPG/api/v0/Request/PaymentRequest";
//
//    public final static String REDIRECT_URL = "https://sadad.shaparak.ir/VPG/Purchase?token=";
//
//    public final static String VERIFY_URL = "https://sadad.shaparak.ir/VPG/api/v0/Advice/Verify";
//
//    public final static int RESPONSE_CODE_OK = 0;






}
