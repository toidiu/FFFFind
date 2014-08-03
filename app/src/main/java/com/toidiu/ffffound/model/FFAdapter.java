package com.toidiu.ffffound.model;

import java.util.ArrayList;

public class FFAdapter {
    private static FFAdapter mFFAdapter;
    private ArrayList<FFGalleryItem> mItems;

    private FFAdapter() {
        mItems = new ArrayList<FFGalleryItem>();
    }

    public static FFAdapter getInstance(){
        if (mFFAdapter == null){
            mFFAdapter = new FFAdapter();
        }

        return mFFAdapter;
    }

    //setter
    public void setItems(ArrayList<FFGalleryItem> ffArray) {
        mItems = ffArray;
    }
    public void setItems(FFGalleryItem items) {
        mItems.add(items);
    }

    //getter
    public FFGalleryItem getItems(int idx) {
        return mItems.get(idx);
    }
    public ArrayList<FFGalleryItem> getItems() {
        return mItems;
    }

}
