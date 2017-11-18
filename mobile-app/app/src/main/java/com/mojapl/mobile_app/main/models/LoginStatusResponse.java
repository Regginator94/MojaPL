package com.mojapl.mobile_app.main.models;

import com.google.gson.annotations.Expose;

public class LoginStatusResponse {

    @Expose
    private Boolean status;

    @Expose
    private String token;

    @Expose
    private Long userId;

    @Expose
    private String email;

    public LoginStatusResponse() {
        this.status = false;
        this.token = "";
        this.userId = null;
        this.email = null;
    }

    public LoginStatusResponse(Boolean status, String token, Long userId, String email) {
        this.status = status;
        this.token = token;
        this.userId = userId;
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
