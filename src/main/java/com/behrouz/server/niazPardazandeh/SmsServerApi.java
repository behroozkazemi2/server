package com.behrouz.server.niazPardazandeh;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;

public interface SmsServerApi {

    @HTTP(method = "POST", path = "http://login.niazpardaz.ir/api/v1/RestWebApi/SendBatchSms", hasBody = true)
    public Call<SmsResponse> sendSms(@Body SmsData smsData);
}