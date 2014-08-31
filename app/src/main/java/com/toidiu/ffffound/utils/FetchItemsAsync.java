package com.toidiu.ffffound.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;

import java.io.IOException;
import java.util.ArrayList;

public class FetchItemsAsync extends AsyncTask<Void,Void,ArrayList<FFFFItem>> {
    FFFeedParser ffFeedParser;

    private static final String TAG = "test tag";
    private String mUrl;
    private OnAsyncComplete mListener;

    public FetchItemsAsync(String url, OnAsyncComplete listener) {
        mUrl = url;
        mListener = listener;
    }
    @Override
    protected ArrayList<FFFFItem> doInBackground(Void... params) {
        try{
            Log.d(TAG, mUrl);
            String result = new FFHttpRequest().getUrl(mUrl);
            ffFeedParser = new FFFeedParser(result);
            ArrayList<FFFFItem> ffGalleryItems = ffFeedParser.parse();
            return ffGalleryItems;
        } catch (IOException e) { Log.d(TAG, "Failed to get xml feed"); }
        return null;
    }
    @Override
    protected void onPostExecute(ArrayList<FFFFItem> galleryItems) {
        if (galleryItems!=null){
            mListener.onAsyncComplete(galleryItems);
        }
    }


    //--------------------------------------PRIVATE INTERFACE---------------
    public interface OnAsyncComplete{
        void onAsyncComplete(ArrayList<FFFFItem> itemList);
    }

}
