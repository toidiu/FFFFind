package com.toidiu.ffffound.model;

public class FFFFItem {
    private String Artist;
    private String Title;
    private String SmallUrl;
    private String MedUrl;
    private String BigUrl;
    private String Descrip;

    public FFFFItem(){
        SmallUrl = "";
        MedUrl = "";
        BigUrl = "";
        Title = "";
        Artist = "";
    }

    public String getArtist() {
        return Artist;
    }
    public void setArtist(String mUser) {
        this.Artist = mUser;
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

}
