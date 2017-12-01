package com.mojapl.mobile_app.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.listeners.UserRequestListener;
import com.mojapl.mobile_app.main.models.LoginStatusResponse;
import com.mojapl.mobile_app.main.models.RegistrationStatusResponse;

public class ForgotPasswordActivity extends AppCompatActivity implements UserRequestListener {

    private UserRequestListener userRequestListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        final EditText emailInput = (EditText)findViewById(R.id.input_email);

        userRequestListener = this;

        Button sendEmailButton = (Button)findViewById(R.id.button_send_email);
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailInput.getText().toString().isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, R.string.error_empty_form_fields,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                // request to api
//                User user = new User(emailInput.getText().toString(), passwordInput.getText().toString(), selectedFacultyId);
//                Connector connector = Connector.getInstance();
//                connector.createUser(userRequestListener, user);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        ForgotPasswordActivity.this.startActivity(mainIntent);
        ForgotPasswordActivity.this.finish();
    }

    @Override
    public void serviceSuccess(RegistrationStatusResponse response) {
        //handle response
//        if (response.getStatus()) {
//            Toast.makeText(EditProfileActivity.this, getString(R.string.message_registration_successful),
//                    Toast.LENGTH_LONG).show();
//            Intent mainIntent = new Intent(EditProfileActivity.this, MainActivity.class);
//            EditProfileActivity.this.startActivity(mainIntent);
//            EditProfileActivity.this.finish();
//        }
    }

    @Override
    public void serviceSuccess(LoginStatusResponse response) {
    }

    @Override
    public void serviceFailure(Exception e) {
        Toast.makeText(ForgotPasswordActivity.this, R.string.error_sending_email_failed,
                Toast.LENGTH_LONG).show();
    }
}
