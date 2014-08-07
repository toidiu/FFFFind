package com.toidiu.ffffound.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.adapter.FFGalleryAdapter;
import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;
import com.toidiu.ffffound.utils.FFFeedParser;
import com.toidiu.ffffound.utils.FFHttpRequest;

import java.io.IOException;
import java.util.ArrayList;


public class FFFragment extends Fragment implements FFGalleryAdapter.FFFetcherInterface, AbsListView.OnScrollListener, AbsListView.OnItemClickListener {
    private static final String TAG = "FFFragment";
    String URLBASE = "http://ffffound.com/feed";

    private FFGalleryAdapter mGalleryAdapter;
    private StaggeredGridView mSGView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mGalleryAdapter = new FFGalleryAdapter( getActivity(), this);
        new FetchItemsTask(URLBASE).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        mSGView = (StaggeredGridView) v.findViewById(R.id.grid_view);

        setUpAdapter();
        return v;
    }

    @Override
    public void FFFFItem() {
        new FetchItemsTask(FFData.getInstance().getNextUrl()).execute();
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        Log.d(TAG, "onScrollStateChanged:" + scrollState);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem,
                         final int visibleItemCount, final int totalItemCount) {
        Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem +
                " visibleItemCount:" + visibleItemCount +
                " totalItemCount:" + totalItemCount);

    }

    public void setUpAdapter(){
        if(getActivity() == null || mSGView == null) return;
        ArrayList<FFFFItem> items = FFData.getInstance().getItems();

        if (items != null && mSGView.getAdapter() == null){
            mSGView.setAdapter(mGalleryAdapter);
            mSGView.setOnScrollListener(this);
            mSGView.setOnItemClickListener(this);
        }else if (items != null){
            mGalleryAdapter.notifyDataSetChanged();
        }else{
            mSGView.setAdapter(null);
        }
    }

    //--------------------------------------PRIVATE CLASS---------------
    private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<FFFFItem>> {

        private String mUrl;
        private FetchItemsTask(String url) {
            mUrl = url;
        }

        @Override
        protected ArrayList<FFFFItem> doInBackground(Void... params) {
            try{
                String result = new FFHttpRequest().getUrl(mUrl);

                FFFeedParser ffFeedParser = new FFFeedParser(result);
                ArrayList<FFFFItem> ffGalleryItems = ffFeedParser.parse();

                return ffGalleryItems;

            } catch (IOException e) {
                Log.d(TAG, "Failed");
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<FFFFItem> galleryItems) {
            FFData.getInstance().addItems(galleryItems);
            setUpAdapter();
        }
    }

}
