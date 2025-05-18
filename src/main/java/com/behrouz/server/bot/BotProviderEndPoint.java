package com.behrouz.server.bot;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 08 July 2020
 **/
public interface  BotProviderEndPoint {



    //<editor-fold desc="Doctor">

    @POST("newBillReceived")
    @FormUrlEncoded
    Call<Void> newBillReceived(
            @Field("providerId") long providerId,
            @Field("productBillId") long productBillId,
            @Field("billId") long billId
    );

    //</editor-fold>



}
