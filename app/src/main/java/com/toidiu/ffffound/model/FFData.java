package com.toidiu.ffffound.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class FFData {
    private static FFData mFFData;
    private ArrayList<FFFFItem> mFFItemsList;
    private HashSet<String> mUsers;
    private int mTotalUsers;
    private URI nextUrl;
    private URI prevUrl;

    //----------instance
    private FFData() {
        mFFItemsList = new ArrayList<FFFFItem>();
        mUsers = new HashSet<String>();
    }
    public static FFData getInstance(){
        if (mFFData == null){
            mFFData = new FFData();
        }

        return mFFData;
    }

    //----------user hash
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

    //----------setter
    public void setItems(ArrayList<FFFFItem> ffArray) {
        mFFItemsList = ffArray;
    }
    public void setItems(FFFFItem items) {
        mFFItemsList.add(items);
    }
    public void setPrevUrl(URI prevUrl) {
        this.prevUrl = prevUrl;
    }
    public void setNextUrl(URI nextUrl) {
        this.nextUrl = nextUrl;
    }

    //----------getter
    public FFFFItem getItems(int idx) {
        return mFFItemsList.get(idx);
    }
    public ArrayList<FFFFItem> getItems() {
        return mFFItemsList;
    }
    public URI getPrevUrl() {
        return prevUrl;
    }
    public URI getNextUrl() {
        return nextUrl;
    }
}
