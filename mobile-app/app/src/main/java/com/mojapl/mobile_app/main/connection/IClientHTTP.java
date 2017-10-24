package com.mojapl.mobile_app.main.connection;

import com.mojapl.mobile_app.main.models.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


/**
 * Created by Klaudia on 23.10.2017.
 */

public interface IClientHTTP {
    @GET("/api/events")
    Call<List<Event>> getEvents(
    );
}
