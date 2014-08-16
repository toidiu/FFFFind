//package com.toidiu.ffffound.model;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Iterator;
//
//public class FFFavData {
//    public ArrayList<FFFFItem> mFFFavList;
//    private HashSet<String> mUsers;
//    private int mTotalUsers;
//
//
//    FFFavData(){
//        mFFFavList = new ArrayList<FFFFItem>();
//        mUsers = new HashSet<String>();
//    }
//
//    //-----------------User Hash
//    public void addUser(String user){
//        mUsers.add(user);
//        mTotalUsers = mUsers.size();
//    }
//    public String[] getUsers(){
//        String[] userList = new String[mTotalUsers];
//        Iterator<String> iterator = mUsers.iterator();
//
//        for (int i = 0; i < mTotalUsers; i++) {
//            userList[i] = iterator.next();
//        }
//        return userList;
//    }
//
//    //------------------Favorite List
//    public ArrayList<FFFFItem> getFav(){ return mFFFavList; }
//    public void setFav(ArrayList<FFFFItem> list){
//        if (list != null) {
//            mFFFavList = list;
//        }
//    }
//    public void addFav(FFFFItem item){ mFFFavList.add(item); }
//    public void removeFav(FFFFItem item){ mFFFavList.remove(item); }
//
//
//
////    public void applyFavs(){ mFFItemsList = mFFFavList; }
//
//}
