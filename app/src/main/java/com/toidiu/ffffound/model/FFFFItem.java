package com.toidiu.ffffound.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FFFFItem implements Parcelable{
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
    public FFFFItem(Parcel parcel){
        Artist = parcel.readString();
        Title = parcel.readString();
        SmallUrl = parcel.readString();
        MedUrl = parcel.readString();
        BigUrl = parcel.readString();
        Descrip = parcel.readString();

        boolean[] array = new boolean[2];
        parcel.readBooleanArray(array);
        Favorite = array[0];
        Download = array[1];
    }


    public String getArtist() {
        return Artist;
    }
    public void setArtist(String user) {
        this.Artist = user;
        FFFavData.getInstance().addUser(user);
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


    //mURL
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
    public boolean toggleFavorite() {
        Favorite = Favorite ^ true;
        return Favorite;
    }
    public boolean isDownload() {
        return Download;
    }
    public void setDownload(boolean download) {
        Download = download;
    }


    //--------------------------------------PARCELABLE---------------
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Artist);
        parcel.writeString(Title);
        parcel.writeString(SmallUrl);
        parcel.writeString(MedUrl);
        parcel.writeString(BigUrl);
        parcel.writeString(Descrip);

        boolean[] array = new boolean[]{Favorite, Download};
        parcel.writeBooleanArray(array);
    }
    public static final Parcelable.Creator<FFFFItem> CREATOR = new Parcelable.Creator<FFFFItem>() {
        public FFFFItem createFromParcel(Parcel in) { return new FFFFItem(in); }
        public FFFFItem[] newArray(int size) { return new FFFFItem[size]; }
    };
}
