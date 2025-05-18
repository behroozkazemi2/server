package com.behrouz.server.base.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * created by: Hapi
 * company: mobin
 * package: com.behrouz.server.api
 * project name:  ximaServer
 * 07 July 2018
 **/

@Service
@RestController
public class BaseControllerApi {



    @PostMapping("/api")
    public void api(HttpServletRequest request) {

    }

    @PostMapping("/api/simple")
    public void apiSimple() {

    }





}