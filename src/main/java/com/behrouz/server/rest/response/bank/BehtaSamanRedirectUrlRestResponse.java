package com.behrouz.server.rest.response.bank;

public class BehtaSamanRedirectUrlRestResponse {
    private String token;
    private SamanRedirectUrlRestResponse samanRedirectUrlRestResponse;

    public BehtaSamanRedirectUrlRestResponse() {
    }


    public BehtaSamanRedirectUrlRestResponse(String token, SamanRedirectUrlRestResponse samanRedirectUrlRestResponse) {
        this.token = token;
        this.samanRedirectUrlRestResponse = samanRedirectUrlRestResponse;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public SamanRedirectUrlRestResponse getSamanRedirectUrlRestResponse() {
        return samanRedirectUrlRestResponse;
    }
    public void setSamanRedirectUrlRestResponse(SamanRedirectUrlRestResponse samanRedirectUrlRestResponse) {
        this.samanRedirectUrlRestResponse = samanRedirectUrlRestResponse;
    }
}
