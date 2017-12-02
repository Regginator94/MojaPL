package com.mojapl.mobile_app.main.models;

import com.google.gson.annotations.Expose;

public class EditProfileRequest {

    @Expose
    private String email;

    @Expose
    private String password;

    public EditProfileRequest() {
    }

    public EditProfileRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
