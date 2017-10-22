package com.mojapl.mobile_app.main.models;

/**
 * Created by Klaudia on 22.10.2017.
 */

public class SettingElement {
    private String name;
    private  Boolean selected;


    public SettingElement(String name, Boolean selected) {
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSelected() {
        return selected;
    }
}
