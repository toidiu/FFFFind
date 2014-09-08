package com.toidiu.ffffind.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class FFFavData {
    private static FFFavData FAV_DATA;

    public HashMap<String, FFFFItem> mFFFavMap;
    private HashSet<String> mUsersSet;


    FFFavData(){
        mFFFavMap = new HashMap<String, FFFFItem>();
        mUsersSet = new HashSet<String>();
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
            for (int i = 0; i < list.size(); i++) {
                FFFFItem item = list.get(i);
                mFFFavMap.put(item.getMedUrl(), item);
            }
        }
    }
    public ArrayList<FFFFItem> getFav(){
        ArrayList<FFFFItem> mFFFavList = new ArrayList<FFFFItem>();
        for (FFFFItem item : mFFFavMap.values() ){
            mFFFavList.add(item);
        }
        return mFFFavList;
    }
    public FFFFItem getFav(String midUrl){
        return mFFFavMap.get(midUrl);
    }
    public void updateFav(FFFFItem item) {
        if ( mFFFavMap.containsKey(item.getMedUrl()) ){
            mFFFavMap.put(item.getMedUrl(), item);
        }
    }
    public void addFav(FFFFItem item){
        item.setFavorite(true);
        mFFFavMap.put(item.getMedUrl(), item);
    }
    public void removeFav(FFFFItem item){ mFFFavMap.remove(item.getMedUrl()); }
    public void clearFav(){ mFFFavMap.clear(); }


    //-----------------User Hash
    public void addUser(String user){
        mUsersSet.add(user);
    }
    public String[] getUsers(){

        String[] userList = new String[ mUsersSet.size() ];
        Iterator<String> iterator = mUsersSet.iterator();
        for (int i = 0; i < mUsersSet.size(); i++) {
            userList[i] = iterator.next();
        }
        return userList;

    }

}
