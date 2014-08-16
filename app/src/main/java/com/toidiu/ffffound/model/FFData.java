package com.toidiu.ffffound.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class FFData {
    private static FFData mFFData;

    public ArrayList<FFFFItem> mFFItemsList;

    public ArrayList<FFFFItem> mFFFavList;
    private HashSet<String> mUsers;

    private int mTotalUsers;
    private String nextUrl;
    private String prevUrl;

    //----------Instance
    private FFData() {
        mFFItemsList = new ArrayList<FFFFItem>();

        mFFFavList = new ArrayList<FFFFItem>();
        mUsers = new HashSet<String>();
    }
    public static FFData getInstance() {
        if (mFFData == null) {
            mFFData = new FFData();
        }

        return mFFData;
    }

    //------------------Item List
    public void addItems(ArrayList<FFFFItem> ffArray) {
        if (mFFItemsList == null) {
            mFFItemsList = ffArray;
        }else {
            mFFItemsList.addAll(ffArray);
        }
    }
    public void addItems(FFFFItem item) {
        mFFItemsList.add(item);
    }
    public void setPrevUrl(String prevUrl) {
        this.prevUrl = prevUrl;
    }
    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public FFFFItem getItems(int idx) {
        return mFFItemsList.get(idx);
    }
    public ArrayList<FFFFItem> getItems() {
        return mFFItemsList;
    }
    public String getPrevUrl() {
        return prevUrl;
    }
    public String getNextUrl() {
        return nextUrl;
    }
    public int getSize() { return mFFItemsList.size(); }
    public int getIdx(FFFFItem item){
        return mFFItemsList.indexOf(item);
    }

    //-----------------User Hash
    public void addUser(String user){
        mUsers.add(user);
        mTotalUsers = mUsers.size();
    }
    public String[] getUsers(){
        String[] userList = new String[mTotalUsers];
        Iterator<String> iterator = mUsers.iterator();

        for (int i = 0; i < mTotalUsers; i++) {
            userList[i] = iterator.next();
        }
        return userList;
    }

    //------------------Favorite List
    public ArrayList<FFFFItem> getFav(){ return mFFFavList; }
    public void setFav(ArrayList<FFFFItem> list){
        if (list != null) {
            mFFFavList = list;
        }
    }
    public void addFav(FFFFItem item){ mFFFavList.add(item); }
    public void removeFav(FFFFItem item){ mFFFavList.remove(item); }
    public void applyFavs(){ mFFItemsList = mFFFavList; }

}





