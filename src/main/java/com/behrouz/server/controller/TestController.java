package com.behrouz.server.controller;


import com.behrouz.server.security.components.PasswordComponent;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.component.ProviderComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/Hapi")
public class TestController {

    @Autowired
    private PasswordComponent passwordComponent;

    @Autowired
    private ProviderComponent providerComponent;

    @RequestMapping("/password")
    public void password(@RequestParam(required = false) String password){

        if(StringUtil.isNullOrEmpty(password)) {
            System.out.println("\n\n\n" + "Password(12345): " + passwordComponent.getHashPassword("12345"));
            System.out.println("\n" + "Password( Main): " + passwordComponent.getHashPassword("mt8Q1awqee") + "\n\n\n");
        }else{
            System.out.println("\n" + "Password encrypt: " + passwordComponent.getHashPassword(password) + "\n\n\n");

        }
    }


}
