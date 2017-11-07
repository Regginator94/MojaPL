package com.mojapl.mobile_app.main.connection;

import com.mojapl.mobile_app.main.models.Event;
import com.mojapl.mobile_app.main.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IClientHTTP {

    @GET("/data")
    Call<List<Event>> getEvents();

    @POST("/createUser")
    Call<String> createUser(@Body User user);

    @POST("/login")
    Call<String> loginUser(@Body User user);
}
