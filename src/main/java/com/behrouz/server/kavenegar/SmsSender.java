package com.behrouz.server.kavenegar;

import com.kavenegar.sdk.KavenegarApi;
import com.kavenegar.sdk.models.SendResult;


public class SmsSender {

    private static final String API_KEY = "API_KEY";


    private static KavenegarApi kavenegarApi = null;


    private static enum Template{
        LOGIN("LOGIN"),
        CUSTOMER_BILL_ACCEPT("CUSTOMER_BILL_ACCEPT"),
        PROVIDER_NEW_BILL("PROVIDER_NEW_BILL"),
        CUSTOMER_ACCOUNT_CHARGED("CUSTOMER_ACCOUNT_CHARGED"),
        ;

        private final String name;


        Template(String name) {
            this.name = name;
        }
    }




    public static SendResult login(String mobile, String code){
        return sendTemplate(
                mobile,
                Template.LOGIN.name,
                code
        );
    }


    public static void customerBillAccepted(String mobile, String firstName, String trackingCode){
        sendTemplate(
                mobile,
                Template.CUSTOMER_BILL_ACCEPT.name,
                firstName,
                trackingCode
        );
    }





    public static void providerBillAccepted(String mobile, String firstName, String trackingCode, String date){
        sendTemplate(
                mobile,
                Template.PROVIDER_NEW_BILL.name,
                firstName,
                trackingCode,
                date
        );
    }



    public static void customerAccountCharged(String mobile, String firstName, String amount){
        sendTemplate(
                mobile,
                Template.CUSTOMER_ACCOUNT_CHARGED.name,
                firstName,
                amount,
                null
        );
    }







    private static SendResult sendTemplate(String mobile, String template, String ...token){


        SendResult result = getInstance().verifyLookup(
                mobile,
                token.length > 0 ? token[0] : null,
                token.length > 1 ? token[1] : null,
                token.length > 2 ? token[2] : null,
                template
        );


        return result;
    }


    private static KavenegarApi getInstance(){
        if(kavenegarApi == null){
            kavenegarApi = new KavenegarApi(API_KEY);
        }
        return kavenegarApi;
    }
}
