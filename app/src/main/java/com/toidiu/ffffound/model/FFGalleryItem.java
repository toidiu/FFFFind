package com.toidiu.ffffound.model;

public class FFGalleryItem {
    private String mCaption;
    private String mId;
    private String mUrl;

    public FFGalleryItem(String url){
        mUrl = url;
        mId = null;
        mCaption = null;
    }

    public String getCaption() {
        return mCaption;
    }
    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public String getId() {
        return mId;
    }
    public void setId(String mId) {
        this.mId = mId;
    }

    public String getUrl() {
        return mUrl;
    }
    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    public String toString() {
        return mUrl;
    }
}
