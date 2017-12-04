package com.mojapl.mobile_app.main.services;

import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.connection.IClientHTTP;
import com.mojapl.mobile_app.main.listeners.SettingsChangeRequestListener;
import com.mojapl.mobile_app.main.models.FiltersUpdateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsService {
    private SettingsChangeRequestListener settingsChangeRequestListener;
    private Connector connector;

    public SettingsService(SettingsChangeRequestListener listener){
        this.settingsChangeRequestListener = listener;
        connector = Connector.getInstance();
    }

    public void updateFilters(String token, String categoryList){
        IClientHTTP clientHTTP = connector.getRetrofit().create(IClientHTTP.class);

        Call<FiltersUpdateResponse> call = clientHTTP.updateFilters(token, categoryList);

        call.enqueue(new Callback<FiltersUpdateResponse>() {
            @Override
            public void onResponse(Call<FiltersUpdateResponse> call, Response<FiltersUpdateResponse> response) {
                FiltersUpdateResponse responseBody = response.body();
                if(responseBody != null){
                    settingsChangeRequestListener.serviceSuccess(responseBody);
                }
            }

            @Override
            public void onFailure(Call<FiltersUpdateResponse> call, Throwable t) {
            settingsChangeRequestListener.serviceFailure(new Exception());
            }
        });
    }
}
