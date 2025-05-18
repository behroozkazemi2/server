package com.behrouz.server.niazPardazandeh;

import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.logging.Logger;


@Component
public class SmsComponent {
    protected Logger logger = Logger.getLogger( SmsComponent.class.getSimpleName() );

    public SmsResponse sendSmsWithRetro(SmsData smsData) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sep.shaparak.ir")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SmsServerApi gerritAPI = retrofit.create(SmsServerApi.class);


        Call<SmsResponse> call =
                gerritAPI.sendSms(
                        smsData
                );
        logger.info("send sms: " + smsData.toString());

        Response<SmsResponse> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.err.println(response.body() == null ?  "خطا در ارسال SMS" : response.body().toString());

        return response.body();
    }
}