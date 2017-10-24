package com.mojapl.mobile_app.main.models;

/**
 * Created by Klaudia on 23.10.2017.
 */

public class Event {
    private int id;
    private String title;
    private  String data;
    private String preview;
    private String image;
    private String href;

    public Event() {
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "id: " + id + " name: " + preview;
    }

    public String getTitle() {
        return title;
    }

    public String getData() {
        return data;
    }

    public String getHref() {
        return href;
    }

    public String getImage() {
        return image;
    }

    public  String getPreview(){
        return preview;
    }
}
