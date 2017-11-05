package com.mojapl.mobile_app.main.listeners;

public interface UserRequestListener {
    void serviceSuccess(String message);
    void serviceFailure(Exception e);
}
