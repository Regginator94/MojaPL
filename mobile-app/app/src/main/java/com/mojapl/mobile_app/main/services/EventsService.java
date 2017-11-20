package com.mojapl.mobile_app.main.services;

import android.util.Log;

import com.mojapl.mobile_app.main.Config;
import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.connection.IClientHTTP;
import com.mojapl.mobile_app.main.listeners.ServerRequestListener;
import com.mojapl.mobile_app.main.models.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Klaudia on 23.10.2017.
 */

public class EventsService {
    private ServerRequestListener serverRequestListener;
    private Connector connector;


    public EventsService(ServerRequestListener serverRequestListener) {
        this.serverRequestListener = serverRequestListener;
        connector = Connector.getInstance();
    }

    public void getDataByOrganisation(String token) {
        IClientHTTP client = connector.getRetrofit().create(IClientHTTP.class);

        Call<List<Event>> call = client.getEventsByOrganisation(token);

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (Config.DEBUG) {
                    Log.d(TAG, call.toString());
                }
                serverRequestListener.serviceSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                if (Config.DEBUG) {
                    Log.e(TAG + " error", t.toString());
                }
                serverRequestListener.serviceFailure(new Exception());
            }
        });
    }

    public void getDataByCategory(String token, int categoryId) {
        IClientHTTP client = connector.getRetrofit().create(IClientHTTP.class);

        Call<List<Event>> call = client.getEventsByCategory(token, categoryId);

        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (Config.DEBUG) {
                    Log.d(TAG, call.toString());
                }
                serverRequestListener.serviceSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                if (Config.DEBUG) {
                    Log.e(TAG + " error", t.toString());
                }
                serverRequestListener.serviceFailure(new Exception());
            }
        });
    }

}
