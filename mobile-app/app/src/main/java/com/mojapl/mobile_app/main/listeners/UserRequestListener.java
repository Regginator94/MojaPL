package com.mojapl.mobile_app.main.listeners;

import com.mojapl.mobile_app.main.models.StatusResponse;
import com.mojapl.mobile_app.main.models.RegistrationStatusResponse;

public interface UserRequestListener {
    void serviceSuccess(RegistrationStatusResponse response);
    void serviceSuccess(StatusResponse response);
    void serviceFailure(Exception e);
}
