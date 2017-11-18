package com.mojapl.mobile_app.main.listeners;

import com.mojapl.mobile_app.main.models.LoginStatusResponse;
import com.mojapl.mobile_app.main.models.RegistrationStatusResponse;

public interface UserRequestListener {
    void serviceSuccess(RegistrationStatusResponse response);
    void serviceSuccess(LoginStatusResponse response);
    void serviceFailure(Exception e);
}
