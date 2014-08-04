package com.toidiu.ffffound.model;

public class FFFFItem {
    private String mUser;
    private String mTitle;
    private String mUrl;

    public FFFFItem(){
        mUrl = "";
        mTitle = "";
        mUser = "";
    }
    public FFFFItem(String url){
        mUrl = url;
        mTitle = "";
        mUser = "";
    }
    public FFFFItem(String url, String title, String user){
        mUrl = url;
        mTitle = title;
        mUser = user;
    }


    public String getUser() {
        return mUser;
    }
    public void setUser(String mUser) {
        this.mUser = mUser;
    }

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
