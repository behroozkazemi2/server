package com.behrouz.server.utils;

import com.behrouz.server.niazPardazandeh.SmsData;
import com.behrouz.server.niazPardazandeh.SmsResponse;
import com.behrouz.server.niazPardazandeh.SmsComponent;
import com.behrouz.server.values.NiazPardazandehSmsValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NiazPardazandehSendSmsUtil {

    @Autowired
    private SmsComponent smsComponent;

    private SmsData smsData;

    public SmsData getInstance() {
        if (smsData == null) {
            smsData = new SmsData();
        }
        return smsData;
    }

    public SmsResponse sendLoginVerifyCode(String toNumbers, String code) {
        SmsData smsData = getInstance();
        smsData.setToNumbers(toNumbers);
        String message = String.format(NiazPardazandehSmsValues.LOGIN_VERIFY_TEXT, code);
        smsData.setMessageContent(message);
        return smsComponent.sendSmsWithRetro(smsData);
    }

    public SmsResponse sendTransactionCompeleteSms(String name, String toNumbers, String code) {
        SmsData smsData = getInstance();
        smsData.setToNumbers(toNumbers);
        String message = String.format(NiazPardazandehSmsValues.TRANSACTION_CODE_TEXT, name, code);
        smsData.setMessageContent(message);
        return smsComponent.sendSmsWithRetro(smsData);
    }

    public SmsResponse sendNewProviderBill(String name, String toNumbers, String code, String  date) {
        SmsData smsData = getInstance();
        smsData.setToNumbers(toNumbers);
        String message = String.format(NiazPardazandehSmsValues.PROVIDER_NEW_BILL, name, code, date);
        smsData.setMessageContent(message);
        return smsComponent.sendSmsWithRetro(smsData);
    }
    public SmsResponse sendNewTicketSms(String toNumbers, String code) {
        SmsData smsData = getInstance();
        smsData.setToNumbers(toNumbers);
        String message = String.format(NiazPardazandehSmsValues.TICKET_TEXT, code);
        smsData.setMessageContent(message);
        return smsComponent.sendSmsWithRetro(smsData);
    }

    public SmsResponse sendNewTicketReplySms(String toNumbers, String code) {
        SmsData smsData = getInstance();
        smsData.setToNumbers(toNumbers);
        String message = String.format(NiazPardazandehSmsValues.TICKET_REPLY_TEXT, code);
        smsData.setMessageContent(message);
        return smsComponent.sendSmsWithRetro(smsData);
    }

}
