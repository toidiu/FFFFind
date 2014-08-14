package com.toidiu.ffffound.model;

public class FFFFItem {
    private String Artist;
    private String Title;
    private String SmallUrl;
    private String MedUrl;
    private String BigUrl;
    private String Descrip;

    private boolean Favorite;
    private boolean Download;

    public FFFFItem(){
        SmallUrl = "";
        MedUrl = "";
        BigUrl = "";
        Title = "";
        Artist = "";
        Favorite = false;
        Download = false;
    }

    public String getArtist() {
        return Artist;
    }
    public void setArtist(String user) {
        this.Artist = user;
        FFData.getInstance().addUser(user);
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String mTitle) {
        this.Title = mTitle;
    }


    public String getDescrip() {
        return Descrip;
    }
    public void setDescription(String descrip) {
        Descrip = descrip;
    }


    //URL
    public String getUrl() {
        return getMedUrl();
    }

    public String getSmallUrl() {
        return SmallUrl;
    }
    public void setSmallUrl(String smallUrl) {
        SmallUrl = smallUrl;
    }

    public String getMedUrl() {
        return MedUrl;
    }
    public void setMedUrl(String medUrl) {
        MedUrl = medUrl;
    }

    public String getBigUrl() {
        return BigUrl;
    }
    public void setBigUrl(String bigUrl) {
        BigUrl = bigUrl;
    }


    public boolean isFavorite() {
        return Favorite;
    }
    public void setFavorite(boolean favorite) {
        Favorite = favorite;
    }

    public boolean isDownload() {
        return Download;
    }
    public void setDownload(boolean download) {
        Download = download;
    }
}
