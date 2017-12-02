package com.mojapl.mobile_app.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.listeners.UserRequestListener;
import com.mojapl.mobile_app.main.models.EditProfileRequest;
import com.mojapl.mobile_app.main.models.StatusResponse;
import com.mojapl.mobile_app.main.models.RegistrationStatusResponse;

public class EditProfileActivity extends AppCompatActivity implements UserRequestListener {

    private final static String REGEX_PASSWORD = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}";
    private final static String REGEX_EMAIL = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    private UserRequestListener userRequestListener;

    private ProgressBar progressBarEmail;
    private ProgressBar progressBarPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        final EditText emailInput = (EditText)findViewById(R.id.input_new_email);
        final EditText passwordInput = (EditText)findViewById(R.id.input_new_password);
        progressBarEmail = (ProgressBar)findViewById(R.id.progress_bar_email);
        progressBarPassword = (ProgressBar)findViewById(R.id.progress_bar_password);

        SharedPreferences preferences = this.getSharedPreferences("LoginData", MODE_PRIVATE);
        final String token = preferences.getString("token", null);

        userRequestListener = this;

        Button changeEmailButton = (Button)findViewById(R.id.button_change_email);
        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailInput.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, R.string.error_empty_form_fields,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!emailInput.getText().toString().matches(REGEX_EMAIL)) {
                    Toast.makeText(EditProfileActivity.this, R.string.error_invalidate_email,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                EditProfileRequest editProfileRequest = new EditProfileRequest(emailInput.getText().toString(), null);
                progressBarEmail.setVisibility(View.VISIBLE);
                Connector connector = Connector.getInstance();
                connector.editProfile(userRequestListener, token, editProfileRequest);
            }
        });

        Button changePasswordButton = (Button)findViewById(R.id.button_change_password);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordInput.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, R.string.error_empty_form_fields,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!passwordInput.getText().toString().matches(REGEX_PASSWORD)) {
                    Toast.makeText(EditProfileActivity.this, R.string.error_invalidate_password,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                EditProfileRequest editProfileRequest = new EditProfileRequest(null, passwordInput.getText().toString());
                progressBarPassword.setVisibility(View.VISIBLE);
                Connector connector = Connector.getInstance();
                connector.editProfile(userRequestListener, token, editProfileRequest);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(EditProfileActivity.this, MainActivity.class);
        EditProfileActivity.this.startActivity(mainIntent);
        EditProfileActivity.this.finish();
    }

    @Override
    public void serviceSuccess(RegistrationStatusResponse response) {
    }

    @Override
    public void serviceSuccess(StatusResponse response) {
        if (response.getStatus()) {
            Toast.makeText(EditProfileActivity.this, R.string.message_profile_edition_successful,
                    Toast.LENGTH_LONG).show();
            Intent mainIntent = new Intent(EditProfileActivity.this, MainActivity.class);
            EditProfileActivity.this.startActivity(mainIntent);
            EditProfileActivity.this.finish();
        } else {
            Toast.makeText(EditProfileActivity.this, R.string.error_profile_edition_failed,
                    Toast.LENGTH_LONG).show();
        }
        progressBarEmail.setVisibility(View.GONE);
        progressBarPassword.setVisibility(View.GONE);
    }

    @Override
    public void serviceFailure(Exception e) {
        Toast.makeText(EditProfileActivity.this, R.string.error_profile_edition_failed,
                Toast.LENGTH_LONG).show();
        progressBarEmail.setVisibility(View.GONE);
        progressBarPassword.setVisibility(View.GONE);
    }
}
