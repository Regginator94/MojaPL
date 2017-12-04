package com.mojapl.mobile_app.main.models;

import com.google.gson.annotations.Expose;

public class StatusResponse {

    @Expose
    private Boolean status;

    @Expose
    private String token;

    @Expose
    private Long userId;

    @Expose
    private String email;

    @Expose
    private String lastLogin;

    @Expose
    private String userFilters;

    public StatusResponse() {
        this.status = false;
        this.token = "";
        this.userId = null;
        this.email = null;
        this.lastLogin = null;
        this.userFilters = null;
    }

    public StatusResponse(Boolean status, String token, Long userId, String email, String lastLogin, String userFilters) {
        this.status = status;
        this.token = token;
        this.userId = userId;
        this.email = email;
        this.lastLogin = lastLogin;
        this.userFilters = userFilters;
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

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getUserFilters() {
        return userFilters;
    }

    public void setUserFilters(String userFilters) {
        this.userFilters = userFilters;
    }
}
