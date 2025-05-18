package com.behrouz.server.bank.saman;


import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.bank.BankTransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
public class TestBankGateway {


    @Autowired
    private MelliBankComponent melliBankComponent;



    @RequestMapping("/test/pay/melli/{amount}")
    public String doPay(Model model, @PathVariable("amount") long amount, HttpServletResponse servletResponse) throws BehtaShopException {


        BankTransactionEntity transaction = melliBankComponent.createTransaction(
                1,
                "09391661481",
                10,
                amount
        );


        String redirectUrl = transaction.getUserPayUrl();

        if(redirectUrl == null || redirectUrl.length() < 1){
            return "not_found";
        }
        return String.format("redirect:%s",redirectUrl);

    }

}
