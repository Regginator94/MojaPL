package com.mojapl.mobile_app.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.listeners.UserRequestListener;
import com.mojapl.mobile_app.main.models.LoginStatusResponse;
import com.mojapl.mobile_app.main.models.RegistrationStatusResponse;
import com.mojapl.mobile_app.main.models.User;

public class LoginActivity extends Activity implements UserRequestListener {

    private UserRequestListener userRequestListener;
    private User user;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_login);

        final EditText emailInput = (EditText)findViewById(R.id.input_user);
        final EditText passwordInput = (EditText)findViewById(R.id.input_password);

        userRequestListener = this;

        Button loginButton = (Button)findViewById(R.id.button_sign_in);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailInput.getText().toString().isEmpty() || passwordInput.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, R.string.error_empty_form_fields,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                user = new User(emailInput.getText().toString(), passwordInput.getText().toString(), null);
                Connector connector = Connector.getInstance();
                connector.loginUser(userRequestListener, null, user);
            }
        });

/*        TextView forgotPasswordText = (TextView)findViewById(R.id.text_forgot_password);
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
        });*/

        TextView signUpText = (TextView)findViewById(R.id.text_sign_up);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

    @Override
    public void serviceSuccess(RegistrationStatusResponse response) {
    }

    @Override
    public void serviceSuccess(LoginStatusResponse response) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("LoginData", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", response.getToken());
        editor.putLong("userId", response.getUserId());
        editor.putString("email", response.getEmail());
        editor.commit();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        LoginActivity.this.startActivity(intent);
        LoginActivity.this.finish();
    }

    @Override
    public void serviceFailure(Exception e) {
        Toast.makeText(LoginActivity.this, R.string.error_incorrect_login_data,
                Toast.LENGTH_LONG).show();
    }
}