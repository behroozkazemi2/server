package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.component.bank.SamanComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.rest.response.bank.BehtaSamanRedirectUrlRestResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class BankApi extends BaseApi {

    @Autowired
    private SamanComponent samanComponent;

    @ApiAction( value = "app.customer.payment.confirm" )
    public ApiResponseBody paymentConfirm (@ApiActionParam(nullable = false) BehtaSamanRedirectUrlRestResponse request ,int customerId) {

        System.out.println( "---------------- paymentConfirm ---------------- " );
        System.out.println(  request.getSamanRedirectUrlRestResponse().toString() );
        String applicationLink = "";
        try {
            applicationLink =
                    samanComponent.verifyBankTransaction(request.getSamanRedirectUrlRestResponse(), request.getToken());
        } catch (IOException e) {
            System.out.println( "---------------- IOException e ---------------- " );

            e.printStackTrace();
            applicationLink =  "redirect:https://shop.behrouz.com/pay/error";
        } catch (BehtaShopException e) {
            System.out.println( "---------------- BehtaShopException e ---------------- " );
            e.printStackTrace();
            applicationLink=  "redirect:https://shop.behrouz.com/pay/error";
        }

        return new ApiResponseBody <>().ok(applicationLink);
    }

}
