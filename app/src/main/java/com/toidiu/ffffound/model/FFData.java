package com.toidiu.ffffound.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class FFData {
//    private static FFData mFFData;

    public ArrayList<FFFFItem> mFFItemsList;
    private String nextUrl;
    private String prevUrl;

    //----------Instance
    public FFData() {
        mFFItemsList = new ArrayList<FFFFItem>();
    }
//    public static ArrayList<FFFetchItem> getInstance() {
//        if (mFFData == null) {
//            mFFData = new FFData();
//        }
//        return mFFItemsList;
//    }

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
    public void clearList(){ mFFItemsList.clear(); }

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

}





