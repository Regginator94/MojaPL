package com.mojapl.mobile_app.main.models;

import io.realm.RealmObject;


public class SettingElement extends RealmObject {
    private String name;
    private Boolean selected;
    private int organizationID;
    private int type;

    public SettingElement(String name, Boolean selected, int organizationID, int type) {
        this.name = name;
        this.selected = selected;
        this.organizationID = organizationID;
        this.type = type;
    }

    public SettingElement(){

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

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public void setSelected(Boolean value){
        this.selected = value;
    }

    public int getType() {
        return type;
    }

}
