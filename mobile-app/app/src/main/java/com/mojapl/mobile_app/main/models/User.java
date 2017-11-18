package com.mojapl.mobile_app.main.models;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;

public class User extends RealmObject {

    @Expose
    private String email;

    @Expose
    private String password;

    @Expose
    private Long facultyId;

    public User(String email, String password, Long facultyId) {
        this.email = email;
        this.password = password;
        this.facultyId = facultyId;
    }

    public User() {
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

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }
}
