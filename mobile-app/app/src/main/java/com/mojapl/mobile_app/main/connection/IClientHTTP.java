package com.mojapl.mobile_app.main.connection;

import com.mojapl.mobile_app.main.models.EditProfileRequest;
import com.mojapl.mobile_app.main.models.EmailRequest;
import com.mojapl.mobile_app.main.models.Event;
import com.mojapl.mobile_app.main.models.StatusResponse;
import com.mojapl.mobile_app.main.models.RegistrationStatusResponse;
import com.mojapl.mobile_app.main.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface IClientHTTP {

    @GET("/dataByOrganisation")
    Call<List<Event>> getEventsByOrganisation(@Header("token") String token);


    @GET("/dataByCategory")
    Call<List<Event>> getEventsByCategory(@Header("token") String token, @Query("category") int categoryId);


    @POST("/createUser")
    Call<RegistrationStatusResponse> createUser(@Body User user);

    @POST("/login")
    Call<StatusResponse> loginUser(@Header("token") String token, @Body User user);

    @POST("/passwordRepeater")
    Call<StatusResponse> resetPassword(@Body EmailRequest emailRequest);

    @PUT("/modifyUser")
    Call<StatusResponse> editProfile(@Header("token") String token, @Body EditProfileRequest editProfileRequest);
}
