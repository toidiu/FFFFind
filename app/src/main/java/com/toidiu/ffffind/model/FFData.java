package com.toidiu.ffffind.model;

import java.util.ArrayList;

public class FFData {
    private static FFData mFFData;

    private ArrayList<FFItem> mFFItemsList;
    private String nextUrl;
    private String prevUrl;

    //----------Instance
    public FFData() {
        mFFItemsList = new ArrayList<FFItem>();
    }
    public static FFData getInstance() {
        if (mFFData == null) {
            mFFData = new FFData();
        }
        return mFFData;
    }

    //------------------Item List
    public void addItems(ArrayList<FFItem> ffArray) {
        if (mFFItemsList == null) {
            mFFItemsList = ffArray;
        }else {
            mFFItemsList.addAll(ffArray);
        }
    }
    public void addItems(FFItem item) {
        mFFItemsList.add(item);
    }
    public void setPrevUrl(String prevUrl) {
        this.prevUrl = prevUrl;
    }
    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }
    public void clearList(){ mFFItemsList.clear(); }

    public FFItem getItems(int idx) {
        return mFFItemsList.get(idx);
    }
    public ArrayList<FFItem> getItems() {
        return mFFItemsList;
    }
    public String getPrevUrl() {
        return prevUrl;
    }
    public String getNextUrl() {
        return nextUrl;
    }
    public int getSize() { return mFFItemsList.size(); }
    public int getIdx(FFItem item){
        return mFFItemsList.indexOf(item);
    }

}





