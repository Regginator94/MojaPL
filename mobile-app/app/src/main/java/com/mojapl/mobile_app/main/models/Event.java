package com.mojapl.mobile_app.main.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("organisationId")
    @Expose
    private int organisationId;
    @SerializedName("categoryId")
    @Expose
    private int categoryId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("fbPost")
    @Expose
    private boolean fbPost;

    public Event(String title, String content, String date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Event() {
    }

    public int getId() {
        return id;
    }

    public int getOrganisationId() {
        return organisationId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public boolean isFbPost() {
        return fbPost;
    }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", organisationId=" + organisationId +
                ", categoryId=" + categoryId +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", date='" + date + '\'' +
                ", url='" + url + '\'' +
                ", fbPost=" + fbPost +
                '}';
    }
}
