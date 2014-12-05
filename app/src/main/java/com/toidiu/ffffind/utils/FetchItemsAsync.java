package com.toidiu.ffffind.utils;


import android.os.AsyncTask;
import android.util.Log;

import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FItemBuilder;

import java.io.IOException;
import java.util.ArrayList;

public class FetchItemsAsync extends AsyncTask<Void, Void, ArrayList<FFItem>>
{
    private static final String TAG = "test tag";
    FItemBuilder fItemBuilder;
    private String          mUrl;
    private OnAsyncComplete mListener;

    public FetchItemsAsync(String url, OnAsyncComplete listener)
    {
        mUrl = url;
        mListener = listener;
    }

    @Override
    protected ArrayList<FFItem> doInBackground(Void... params)
    {
        try
        {
            Log.d(TAG, mUrl);
            String result = new HttpRequest().getUrl(mUrl);
            fItemBuilder = new FItemBuilder(result);
            ArrayList<FFItem> ffGalleryItems = fItemBuilder.parse();
            return ffGalleryItems;
        }
        catch(IOException e)
        {
            Log.d(TAG, "Failed to get xml feed");
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<FFItem> galleryItems)
    {
        if(galleryItems != null)
        {
            mListener.onAsyncComplete(galleryItems);
        }
    }


    //--------------------------------------PRIVATE INTERFACE---------------
    public interface OnAsyncComplete
    {
        void onAsyncComplete(ArrayList<FFItem> itemList);
    }

}
