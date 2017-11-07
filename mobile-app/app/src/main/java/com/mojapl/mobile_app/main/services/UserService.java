package com.mojapl.mobile_app.main.services;

import android.util.Log;

import com.mojapl.mobile_app.main.Config;
import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.connection.IClientHTTP;
import com.mojapl.mobile_app.main.listeners.UserRequestListener;
import com.mojapl.mobile_app.main.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class UserService {

    private UserRequestListener userRequestListener;
    private Connector connector;

    public UserService(UserRequestListener userRequestListener){
        this.userRequestListener = userRequestListener;
        connector = Connector.getInstance();
    }

    public void saveUser(User user){
        IClientHTTP client = connector.getRetrofit().create(IClientHTTP.class);

        Call<String> call = client.createUser(user);
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(Config.DEBUG){
                    Log.d(TAG, call.toString());
                    Log.d(TAG, response.body().toString());
                }
                userRequestListener.serviceSuccess(response.body().toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if(Config.DEBUG){
                    Log.e(TAG+" error", t.toString());
                }
                userRequestListener.serviceFailure(new Exception());
            }
        });
    }

    public void findUser(User user){
        IClientHTTP client = connector.getRetrofit().create(IClientHTTP.class);

        Call<String> call = client.loginUser(user);
        call.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // TODO: json from server should contain status code -> make adjusted message based on it
                String responseBody = response.body();
                if (responseBody == null) {
                    userRequestListener.serviceFailure(new Exception());
                    return;
                }
                if(Config.DEBUG){
                    Log.d(TAG, call.toString());
                    Log.d(TAG, responseBody.toString());
                }
                userRequestListener.serviceSuccess(responseBody.toString());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if(Config.DEBUG){
                    Log.e(TAG+" error", t.toString());
                }
                userRequestListener.serviceFailure(new Exception());
            }
        });
    }
}
