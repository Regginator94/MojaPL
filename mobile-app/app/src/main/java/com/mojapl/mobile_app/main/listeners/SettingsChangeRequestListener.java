package com.mojapl.mobile_app.main.listeners;


import com.mojapl.mobile_app.main.models.FiltersUpdateResponse;

public interface SettingsChangeRequestListener {
    void serviceSuccess(FiltersUpdateResponse response);

    void serviceFailure(Exception e);
}
