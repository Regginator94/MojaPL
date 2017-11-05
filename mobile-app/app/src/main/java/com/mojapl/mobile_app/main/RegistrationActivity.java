package com.mojapl.mobile_app.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.listeners.UserRequestListener;
import com.mojapl.mobile_app.main.models.User;

public class RegistrationActivity extends AppCompatActivity implements UserRequestListener {

    private UserRequestListener userRequestListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText emailInput = (EditText)findViewById(R.id.input_user);
        final EditText passwordInput = (EditText)findViewById(R.id.input_password);
        final EditText passwordRepeatInput = (EditText)findViewById(R.id.input_password_repeat);

        userRequestListener = this;

        Button signUpButton = (Button)findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!passwordInput.getText().toString().equals(passwordRepeatInput.getText().toString())) {
                    Toast.makeText(RegistrationActivity.this, R.string.error_string_dont_match_each_other,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (emailInput.getText().toString().isEmpty() || passwordInput.getText().toString().isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, R.string.error_empty_form_fields,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                User user = new User(emailInput.getText().toString(), passwordInput.getText().toString());
                Connector connector = Connector.getInstance();
                connector.createUser(userRequestListener, user);
            }
        });

        TextView backToLoginText = (TextView)findViewById(R.id.text_back_to_login);
        backToLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
                RegistrationActivity.this.startActivity(mainIntent);
                RegistrationActivity.this.finish();
            }
        });
    }

    @Override
    public void serviceSuccess(String message) {
        Toast.makeText(RegistrationActivity.this, getString(R.string.message_registration_successful),
                Toast.LENGTH_LONG).show();
        Intent mainIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
        RegistrationActivity.this.startActivity(mainIntent);
        RegistrationActivity.this.finish();
    }

    @Override
    public void serviceFailure(Exception e) {
        Toast.makeText(RegistrationActivity.this, R.string.error_registration_failed,
                Toast.LENGTH_LONG).show();
    }
}
