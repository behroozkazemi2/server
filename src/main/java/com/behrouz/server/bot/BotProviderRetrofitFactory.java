package com.behrouz.server.bot;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by: HapiKZM
 * Project Name: behta-server
 * 08 July 2020
 **/
public class BotProviderRetrofitFactory {
    private static final BotProviderRetrofitFactory INSTANCE = new BotProviderRetrofitFactory();
    private Retrofit retrofit;

    private BotProviderRetrofitFactory(){

    }

    public static BotProviderRetrofitFactory getInstance() {
        return INSTANCE;
    }

    public BotProviderEndPoint getBotProviderEndPoint(){
        if (retrofit == null) {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://162.223.94.35:1112/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(BotProviderEndPoint.class);
    }
}