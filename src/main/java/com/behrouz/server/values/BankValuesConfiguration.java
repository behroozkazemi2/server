package com.behrouz.server.values;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankValuesConfiguration {

    public static String SADAD_MERCHANT_ID;
    public static String SADAD_TERMINAL_ID;
    public static String SADAD_EPG_KEY;
    public static String SADAD_CALL_BACK_URL;
    public static String SADAD_SADAD_PAY_URL;
    public static String SADAD_USER_PAY_URL;

    public static String SAMAN_MERCHANT_ID;
    public static String SAMAN_TERMINAL_ID;
    public static String SAMAN_EPG_KEY;
    public static String SAMAN_CALL_BACK_URL;
    public static String SAMAN_PAY_URL;
    public static String SAMAN_USER_PAY_URL;



//    @Value("${bank.sadad.user-pay-url}")
//    public void setSadadUserPayUrl(String value){
//        SADAD_USER_PAY_URL = value;
//    }
//    @Value("${bank.sadad.call-back-url}")
//    public void setSadadCallBackUrl(String value){
//        SADAD_CALL_BACK_URL = value;
//    }
//    @Value("${bank.sadad.epg-key}")
//    public void setSadadEpgKey(String value){
//        SADAD_EPG_KEY = value;
//    }
//    @Value("${bank.sadad.terminal-id}")
//    public void setSadadTerminalId(String value){
//        SADAD_TERMINAL_ID = value;
//    }
//    @Value("${bank.sadad.sadad-pay-url}")
//    public void setSadadSadadPayUrl(String value){
//        SADAD_SADAD_PAY_URL = value;
//    }
//    @Value("${bank.sadad.merchant-id}")
//    public void setSadadMerchantId(String value){
//        SADAD_MERCHANT_ID = value;
//    }




    @Value("${bank.saman.terminal-id}")
    public void setSamanTerminalId(String samanTerminalId) {
        SAMAN_TERMINAL_ID = samanTerminalId;
    }

    @Value("${bank.saman.epg-key}")
    public void setSamanEpgKey(String samanEpgKey) {
        SAMAN_EPG_KEY = samanEpgKey;
    }

    @Value("${bank.saman.call-back-url}")
    public void setSamanCallBackUrl(String samanCallBackUrl) {
        SAMAN_CALL_BACK_URL = samanCallBackUrl;
    }

    @Value("${bank.saman.pay-url}")
    public void setSamanPayUrl(String samanPayUrl) {
        SAMAN_PAY_URL = samanPayUrl;
    }

    @Value("${bank.saman.user-pay-url}")
    public void setSamanUserPayUrl(String samanUserPayUrl) {
        SAMAN_USER_PAY_URL = samanUserPayUrl;
    }
    @Value("${bank.saman.merchant-id}")
    public void setSamanMerchantId(String samanMerchantId) {
        SAMAN_MERCHANT_ID = samanMerchantId;
    }
}
