package com.mojapl.mobile_app.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mojapl.mobile_app.R;

public class LoginActivity extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);

        final EditText emailInput = (EditText)findViewById(R.id.input_user);
        final EditText passwordInput = (EditText)findViewById(R.id.input_password);

        Button loginButton = (Button)findViewById(R.id.button_sign_in);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((emailInput.getText().toString().equals("test@test") && passwordInput.getText().toString().equals("test")) || Config.DEBUG) {
                            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(mainIntent);
                            LoginActivity.this.finish();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.error_incorrect_login_data,
                            Toast.LENGTH_LONG).show();
                }
                
            }
        });

        TextView forgotPasswordText = (TextView)findViewById(R.id.text_forgot_password);
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailInput.getText().toString().length() != 0) {
                    Toast.makeText(LoginActivity.this, R.string.message_sent_link_to_reset_password,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.message_type_email,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        TextView signUpText = (TextView)findViewById(R.id.text_sign_up);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                LoginActivity.this.startActivity(mainIntent);
                LoginActivity.this.finish();
            }
        });
    }
}