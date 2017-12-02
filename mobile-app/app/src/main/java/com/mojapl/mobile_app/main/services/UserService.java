package com.mojapl.mobile_app.main.services;

import android.util.Log;

import com.mojapl.mobile_app.main.Config;
import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.connection.IClientHTTP;
import com.mojapl.mobile_app.main.listeners.UserRequestListener;
import com.mojapl.mobile_app.main.models.EmailRequest;
import com.mojapl.mobile_app.main.models.LoginStatusResponse;
import com.mojapl.mobile_app.main.models.RegistrationStatusResponse;
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

        Call<RegistrationStatusResponse> call = client.createUser(user);
        call.enqueue(new Callback<RegistrationStatusResponse>() {

            @Override
            public void onResponse(Call<RegistrationStatusResponse> call, Response<RegistrationStatusResponse> response) {
                if (response.body() == null) {
                    userRequestListener.serviceFailure(new Exception());
                    return;
                }
                if (Config.DEBUG){
                    Log.d(TAG, call.toString());
                    Log.d(TAG, response.body().toString());
                }
                userRequestListener.serviceSuccess(response.body());
            }

            @Override
            public void onFailure(Call<RegistrationStatusResponse> call, Throwable t) {
                if(Config.DEBUG){
                    Log.e(TAG+" error", t.toString());
                }
                userRequestListener.serviceFailure(new Exception());
            }
        });
    }

    public void findUser(String token, User user){
        IClientHTTP client = connector.getRetrofit().create(IClientHTTP.class);

        Call<LoginStatusResponse> call = client.loginUser(token, user);
        call.enqueue(new Callback<LoginStatusResponse>() {

            @Override
            public void onResponse(Call<LoginStatusResponse> call, Response<LoginStatusResponse> response) {
                LoginStatusResponse responseBody = response.body();
                if (responseBody == null) {
                    userRequestListener.serviceFailure(new Exception());
                    return;
                }
                if (Config.DEBUG){
                    Log.d(TAG, call.toString());
                    Log.d(TAG, responseBody.toString());
                }
                userRequestListener.serviceSuccess(responseBody);
            }

            @Override
            public void onFailure(Call<LoginStatusResponse> call, Throwable t) {
                if (Config.DEBUG) {
                    Log.e(TAG + " error", t.toString());
                }
                userRequestListener.serviceFailure(new Exception());
            }
        });
    }

    public void resetPassword(EmailRequest emailRequest){
        IClientHTTP client = connector.getRetrofit().create(IClientHTTP.class);

        Call<LoginStatusResponse> call = client.resetPassword(emailRequest);
        call.enqueue(new Callback<LoginStatusResponse>() {

            @Override
            public void onResponse(Call<LoginStatusResponse> call, Response<LoginStatusResponse> response) {
                LoginStatusResponse responseBody = response.body();
                if (responseBody == null) {
                    userRequestListener.serviceFailure(new Exception());
                    return;
                }
                if (Config.DEBUG){
                    Log.d(TAG, call.toString());
                    Log.d(TAG, responseBody.toString());
                }
                userRequestListener.serviceSuccess(responseBody);
            }

            @Override
            public void onFailure(Call<LoginStatusResponse> call, Throwable t) {
                if (Config.DEBUG) {
                    Log.e(TAG + " error", t.toString());
                }
                userRequestListener.serviceFailure(new Exception());
            }
        });
    }
}
