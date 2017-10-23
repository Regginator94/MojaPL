package com.mojapl.mobile_app.main.models;

public class DashboardItem {
    private int mResource;
    private String mTitle;

    public DashboardItem(int resource, String title) {
        this.mResource = resource;
        this.mTitle = title;
    }

    public int getUrl() {
        return mResource;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DashboardItem item = (DashboardItem) o;

        if (mResource != item.mResource) return false;
        return mTitle != null ? mTitle.equals(item.mTitle) : item.mTitle == null;

    }

    @Override
    public int hashCode() {
        int result = mResource;
        result = 31 * result + (mTitle != null ? mTitle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DashboardItem{" +
                "url=" + mResource +
                ", title='" + mTitle + '\'' +
                '}';
    }
}
