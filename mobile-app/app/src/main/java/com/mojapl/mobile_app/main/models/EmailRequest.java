package com.mojapl.mobile_app.main.models;

import com.google.gson.annotations.Expose;

public class EmailRequest {

    @Expose
    private String email;

    public EmailRequest() {
    }

    public EmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
