package com.mojapl.mobile_app.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.listeners.UserRequestListener;
import com.mojapl.mobile_app.main.models.LoginStatusResponse;
import com.mojapl.mobile_app.main.models.RegistrationStatusResponse;
import com.mojapl.mobile_app.main.models.User;

public class SplashScreenActivity extends Activity implements UserRequestListener {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences preferences = this.getSharedPreferences("LoginData", MODE_PRIVATE);
        String token = preferences.getString("token", null);

        if (token != null) {
            Connector connector = Connector.getInstance();
            connector.loginUser(this, token, new User());
        } else {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    SplashScreenActivity.this.startActivity(intent);
                    SplashScreenActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }

    @Override
    public void serviceSuccess(RegistrationStatusResponse response) {
    }

    @Override
    public void serviceSuccess(LoginStatusResponse response) {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public void serviceFailure(Exception e) {
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                SplashScreenActivity.this.startActivity(intent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}