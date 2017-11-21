package com.mojapl.mobile_app.main.models;

import com.google.gson.annotations.Expose;

public class FiltersUpdateResponse {
    @Expose
    private Boolean status;

    @Expose
    private String message;


    public FiltersUpdateResponse() {
        this.status = false;
        this.message = "";
    }

    public FiltersUpdateResponse(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Status: " + status + " Message: " + message;
    }
}
