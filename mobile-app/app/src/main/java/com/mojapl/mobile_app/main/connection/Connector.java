package com.mojapl.mobile_app.main.connection;

import com.mojapl.mobile_app.main.Config;
import com.mojapl.mobile_app.main.listeners.ServerRequestListener;
import com.mojapl.mobile_app.main.listeners.SettingsChangeRequestListener;
import com.mojapl.mobile_app.main.listeners.UserRequestListener;
import com.mojapl.mobile_app.main.models.EditProfileRequest;
import com.mojapl.mobile_app.main.models.EmailRequest;
import com.mojapl.mobile_app.main.models.Event;
import com.mojapl.mobile_app.main.models.User;
import com.mojapl.mobile_app.main.services.EventsService;
import com.mojapl.mobile_app.main.services.SettingsService;
import com.mojapl.mobile_app.main.services.UserService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Connector {
    private static final String TAG = "Communicator";
    private static Connector connector = null;
    private OkHttpClient.Builder httpClient;
    private Retrofit.Builder builder;
    private Retrofit retrofit;

    private Connector() {
        httpClient = new OkHttpClient.Builder();

        builder =
                new Retrofit.Builder()
                        .baseUrl(Config.SERVER_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();
    }

    public static Connector getInstance() {
        if (connector == null) {
            connector = new Connector();
        }
        return connector;
    }

    public void getEventsByOrganisation(ServerRequestListener serverRequestListener, String token) {
        EventsService eventsService = new EventsService(serverRequestListener);
        eventsService.getDataByOrganisation(token);
    }

    public void getEventsByCategory(ServerRequestListener serverRequestListener, String token, int categoryId) {
        EventsService eventsService = new EventsService(serverRequestListener);
        eventsService.getDataByCategory(token, categoryId);
    }

    public void getEventsByRegex(ServerRequestListener serverRequestListener, String token, String regex) {
        EventsService eventsService = new EventsService(serverRequestListener);
        eventsService.getEventsByRegex(token, regex);
    }

    public void createUser(UserRequestListener userRequestListener, User user) {
        UserService userService = new UserService(userRequestListener);
        userService.saveUser(user);
    }

    public void loginUser(UserRequestListener userRequestListener, String token, User user) {
        UserService userService = new UserService(userRequestListener);
        userService.findUser(token, user);
    }

    public void resetPassword(UserRequestListener userRequestListener, EmailRequest emailRequest) {
        UserService userService = new UserService(userRequestListener);
        userService.resetPassword(emailRequest);
    }

    public void editProfile(UserRequestListener userRequestListener, String token, EditProfileRequest editProfileRequest) {
        UserService userService = new UserService(userRequestListener);
        userService.editProfile(token, editProfileRequest);
    }

    public  void updateFilters(SettingsChangeRequestListener serverRequestListener, String token, String filtersList){
        SettingsService eventsService = new SettingsService(serverRequestListener);
        eventsService.updateFilters(token, filtersList);

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Retrofit.Builder getBuilder() {
        return builder;
    }

    public OkHttpClient.Builder getHttpClient() {
        return httpClient;
    }
}
