package com.mojapl.mobile_app.main.listeners;

import com.mojapl.mobile_app.main.models.Event;

import java.util.List;

/**
 * Created by Klaudia on 23.10.2017.
 */

public interface ServerRequestListener {
    void serviceSuccess(List<Event> events);
    void serviceFailure(Exception e);
}
