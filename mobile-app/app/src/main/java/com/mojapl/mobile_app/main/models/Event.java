package com.mojapl.mobile_app.main.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class Event extends RealmObject {
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
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("fbPost")
    @Expose
    private boolean fbPost;
    @SerializedName("organisationName")
    @Expose
    private boolean organisationName;


    public Event() {
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
                ", startDate='" + startDate + '\'' +
                ", createDate='" + createDate + '\'' +
                ", url='" + url + '\'' +
                ", fbPost=" + fbPost +
                ", organisationName=" + organisationName +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFbPost() {
        return fbPost;
    }

    public void setFbPost(boolean fbPost) {
        this.fbPost = fbPost;
    }

    public boolean isOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(boolean organisationName) {
        this.organisationName = organisationName;
    }
}
