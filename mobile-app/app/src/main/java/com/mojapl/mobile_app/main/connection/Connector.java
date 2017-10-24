package com.mojapl.mobile_app.main.connection;

import com.mojapl.mobile_app.main.Config;
import com.mojapl.mobile_app.main.listeners.ServerRequestListener;
import com.mojapl.mobile_app.main.services.EventsService;


import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Klaudia on 23.10.2017.
 */

public class Connector {
    private static final String TAG = "Communicator";
    private  static Connector connector = null;
    private OkHttpClient.Builder httpClient;
    private Retrofit.Builder builder;
    private Retrofit retrofit;

    private Connector(){
        httpClient = new OkHttpClient.Builder();

         builder =
                new Retrofit.Builder()
                        .baseUrl(Config.SERVER_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

         retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();
    }

    public static Connector getInstance(){
        if(connector == null){
            connector = new Connector();
        }
        return connector;
    }
    public void getEvents(ServerRequestListener serverRequestListener) {
        EventsService eventsService = new EventsService(serverRequestListener);
        eventsService.getData();

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Retrofit.Builder getBuilder() {
        return builder;
    }

    public OkHttpClient.Builder getHttpClient() {
        return httpClient;
    }
}
