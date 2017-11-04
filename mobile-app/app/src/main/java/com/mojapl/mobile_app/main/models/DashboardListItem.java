package com.mojapl.mobile_app.main.models;

public class DashboardListItem {
    private int mResource;
    private String mTitle;
    private String mContent;

    public DashboardListItem(int resource, String title, String content) {
        this.mResource = resource;
        this.mTitle = title;
        this.mContent = content;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DashboardListItem that = (DashboardListItem) o;

        if (mResource != that.mResource) return false;
        if (mTitle != null ? !mTitle.equals(that.mTitle) : that.mTitle != null) return false;
        return mContent != null ? mContent.equals(that.mContent) : that.mContent == null;

    }

    @Override
    public int hashCode() {
        int result = mResource;
        result = 31 * result + (mTitle != null ? mTitle.hashCode() : 0);
        result = 31 * result + (mContent != null ? mContent.hashCode() : 0);
        return result;
    }

    public int getUrl() {
        return mResource;
    }

    @Override
    public String toString() {
        return "DashboardListItem{" +
                "mResource=" + mResource +
                ", mTitle='" + mTitle + '\'' +
                ", mContent='" + mContent + '\'' +
                '}';
    }

    public int getResource() {
        return mResource;
    }

    public String getContent() {
        return mContent;
    }

    public String getTitle() {
        return mTitle;
    }

}
