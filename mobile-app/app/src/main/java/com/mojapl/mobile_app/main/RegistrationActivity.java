package com.mojapl.mobile_app.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mojapl.mobile_app.R;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText emailInput = (EditText)findViewById(R.id.input_user);
        final EditText passwordInput = (EditText)findViewById(R.id.input_password);
        final EditText passwordRepeatInput = (EditText)findViewById(R.id.input_password_repeat);

        Button signUpButton = (Button)findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
