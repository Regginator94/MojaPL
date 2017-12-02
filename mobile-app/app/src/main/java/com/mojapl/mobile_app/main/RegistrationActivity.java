package com.mojapl.mobile_app.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.connection.Connector;
import com.mojapl.mobile_app.main.listeners.UserRequestListener;
import com.mojapl.mobile_app.main.models.StatusResponse;
import com.mojapl.mobile_app.main.models.RegistrationStatusResponse;
import com.mojapl.mobile_app.main.models.User;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, UserRequestListener {

    private final static String REGEX_PASSWORD = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}";
    private final static String REGEX_EMAIL = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    private UserRequestListener userRequestListener;

    private Long selectedFacultyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText emailInput = (EditText)findViewById(R.id.input_user);
        final EditText passwordInput = (EditText)findViewById(R.id.input_password);
        final EditText passwordRepeatInput = (EditText)findViewById(R.id.input_password_repeat);

        userRequestListener = this;

        Spinner spinner = (Spinner) findViewById(R.id.spinner_faculty);
        spinner.setOnItemSelectedListener(this);

        List<String> faculties = new ArrayList<>();
        faculties.add("Wydział EEIA");
        faculties.add("Wydział Mechaniczny");
        faculties.add("Wydział Chemiczny");
        faculties.add("Wydział TMiWT");
        faculties.add("Wydział BiNoŻ");
        faculties.add("Wydział BAIŚ");
        faculties.add("Wydział FTIMS");
        faculties.add("Wydział IPOŚ");
        faculties.add("Wydział OiZ");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, faculties);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        Button signUpButton = (Button)findViewById(R.id.button_sign_up);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailInput.getText().toString().matches(REGEX_EMAIL)) {
                    Toast.makeText(RegistrationActivity.this, R.string.error_invalidate_email,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (!passwordInput.getText().toString().matches(REGEX_PASSWORD)) {
                    Toast.makeText(RegistrationActivity.this, R.string.error_invalidate_password,
                            Toast.LENGTH_LONG).show();
                    return;
                }
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
                User user = new User(emailInput.getText().toString(), passwordInput.getText().toString(), selectedFacultyId);
                Connector connector = Connector.getInstance();
                connector.createUser(userRequestListener, user);
            }
        });

        TextView backToLoginText = (TextView)findViewById(R.id.text_back_to_login);
        backToLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
        RegistrationActivity.this.startActivity(mainIntent);
        RegistrationActivity.this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final Long idOffset = 2L;
        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
        ((TextView) parent.getChildAt(0)).setTextSize(18);
        ((TextView) parent.getChildAt(0)).setPadding(0, 0, 0, 0);
        ((TextView) parent.getChildAt(0)).setGravity(Gravity.BOTTOM);
        ((TextView) parent.getChildAt(0)) .setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(RegistrationActivity.this, R.drawable.icon_faculty), null, null, null);
        ((TextView) parent.getChildAt(0)).setCompoundDrawablePadding((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        selectedFacultyId = position + idOffset;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void serviceSuccess(RegistrationStatusResponse response) {
        if (response.getStatus()) {
            Toast.makeText(RegistrationActivity.this, getString(R.string.message_registration_successful),
                    Toast.LENGTH_LONG).show();
            Intent mainIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
            RegistrationActivity.this.startActivity(mainIntent);
            RegistrationActivity.this.finish();
        }
    }

    @Override
    public void serviceSuccess(StatusResponse response) {
    }

    @Override
    public void serviceFailure(Exception e) {
        Toast.makeText(RegistrationActivity.this, R.string.error_user_already_exists,
                Toast.LENGTH_LONG).show();
    }
}
