package com.behrouz.server.values;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NiazPardazandehSmsValues {
    public static String USER_NAME;
    public static String PASSWORD;
    public static String FROM_NUMBER;
    public static Boolean FLASH;
    public static int SEND_DELAY;
    public static String LOGIN_VERIFY_TEXT;
    public static String TRANSACTION_CODE_TEXT;
    public static String TICKET_TEXT;
    public static String TICKET_REPLY_TEXT;
    public static String PROVIDER_NEW_BILL;

    @Value("${sms.niazpardazandeh.user-name}")
    public void setUserName(String value){
        USER_NAME = value;
    }

    @Value("${sms.niazpardazandeh.password}")
    public void setPASSWORD(String value) {
        PASSWORD = value;
    }

    @Value("${sms.niazpardazandeh.from-number}")
    public void setFromNumber(String value) {
        FROM_NUMBER = value;
    }

    @Value("${sms.niazpardazandeh.falsh}")
    public void setFLASH(Boolean value) {
        FLASH = value;
    }

    @Value("${sms.niazpardazandeh.send-delay}")
    public void setSendDelay(int value) {
        SEND_DELAY = value;
    }

    @Value("${sms.niazpardazandeh.login-verify-text}")
    public void setLoginVerifyText(String value) {
        LOGIN_VERIFY_TEXT = value;
    }

    @Value("${sms.niazpardazandeh.transaction-code-text}")
    public void setTransactionCodeText(String value) {
        TRANSACTION_CODE_TEXT = value;
    }

    @Value("${sms.niazpardazandeh.ticket-text}")
    public void setTicketText(String value) {
        TICKET_TEXT = value;
    }

    @Value("${sms.niazpardazandeh.ticket-reply-text}")
    public void setTicketReplyText(String value) {
        TICKET_REPLY_TEXT = value;
    }

    @Value("${sms.niazpardazandeh.provider-new-bill}")
    public void setProviderNewBill(String value) {
        PROVIDER_NEW_BILL = value;
    }
}
