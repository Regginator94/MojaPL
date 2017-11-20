package com.mojapl.mobile_app.main.connection;

import com.mojapl.mobile_app.main.models.Event;
import com.mojapl.mobile_app.main.models.LoginStatusResponse;
import com.mojapl.mobile_app.main.models.RegistrationStatusResponse;
import com.mojapl.mobile_app.main.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IClientHTTP {

    @GET("/dataByOrganisation")
    Call<List<Event>> getEventsByOrganisation(@Header("token") String token);


    @GET("/dataByCategory")
    Call<List<Event>> getEventsByCategory(@Header("token") String token, @Query("category") int categoryId);


    @POST("/createUser")
    Call<RegistrationStatusResponse> createUser(@Body User user);

    @POST("/login")
    Call<LoginStatusResponse> loginUser(@Header("token") String token, @Body User user);
}
