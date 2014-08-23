package com.toidiu.ffffound.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class FFFavData {
    private static FFFavData FAV_DATA;

    public ArrayList<FFFFItem> mFFFavList;
    private HashSet<String> mUsers;


    FFFavData(){
        mFFFavList = new ArrayList<FFFFItem>();
        mUsers = new HashSet<String>();
    }
    //----------Instance
    public static FFFavData getInstance() {
        if (FAV_DATA == null) {
            FAV_DATA = new FFFavData();
        }
        return FAV_DATA;
    }



    //------------------Favorite List
    public void setFav(ArrayList<FFFFItem> list){
        if (list != null) {
            mFFFavList = list;
        }
    }
    public ArrayList<FFFFItem> getFav(){ return mFFFavList; }
    public void updateFav(FFFFItem item){
//        mFFFavList.remove(item);
        int idx = mFFFavList.indexOf(item);
        if (idx == -1){
            addFav(item);
        }else {
            mFFFavList.remove(idx);
        }
//        mFFFavList.set(idx, item);
    }
    public void addFav(FFFFItem item){ mFFFavList.add(item); }
    public void removeFav(FFFFItem item){ mFFFavList.remove(item); }
    public void clearFav(){ mFFFavList.clear(); }
//    public void applyFavs(){ mFFItemsList = mFFFavList; }



    //-----------------User Hash
    public void addUser(String user){
        mUsers.add(user);
    }
    public String[] getUsers(){

        String[] userList = new String[ mUsers.size() ];
        Iterator<String> iterator = mUsers.iterator();
        for (int i = 0; i < mUsers.size(); i++) {
            userList[i] = iterator.next();
        }
        return userList;

    }
}
