package com.mojapl.mobile_app.main.models;

import com.google.gson.annotations.Expose;

public class RegistrationStatusResponse {

    @Expose
    private Boolean status;

    @Expose
    private String message;

    public RegistrationStatusResponse() {
    }

    public RegistrationStatusResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
