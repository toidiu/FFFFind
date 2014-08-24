package com.toidiu.ffffound.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.activities.FFDetailActivity;
import com.toidiu.ffffound.activities.MainActivity;
import com.toidiu.ffffound.adapter.FFGalleryAdapter;
import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;
import com.toidiu.ffffound.model.FFFavData;
import com.toidiu.ffffound.utils.FFFeedParser;
import com.toidiu.ffffound.utils.FFHttpRequest;
import com.toidiu.ffffound.utils.Stuff;

import java.io.IOException;
import java.util.ArrayList;



public class FFListFragment extends Fragment implements FFGalleryAdapter.FFFetcherInterface,
        AbsListView.OnScrollListener, AbsListView.OnItemClickListener {
    private static final String TAG = "FFListFragment";

    public static final String LIST_URL = "com.toidiu.list_url";
    public static final String SHOW_FAV = "com.toidiu.show_fav";
    public static final String EVERYONE_URL = "http://ffffound.com/feed";
    public static final String SPARE_URL_BASE = "http://ffffound.com/home/"; //+ user + SPAREUrlEnd
    public static final String SPARE_URL_END = "/found/feed";
    public static final String RANDOM_URL_BASE = "http://ffffound.com/feed?offset="; //+ number

    private String mUrl;
    private boolean itemsShowing = false;
    private StaggeredGridView mSGView;
    private FFGalleryAdapter mGalleryAdapter;
    public FFData mListData;
    private FFFeedParser ffFeedParser;
    private boolean showFavs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mListData = new FFData();
        mGalleryAdapter = new FFGalleryAdapter(getActivity(), this, mListData);

        mUrl = getArguments().getCharSequence(LIST_URL).toString();
        showFavs = getArguments().getBoolean(SHOW_FAV, false);
        if (showFavs) {
            mListData.addItems(FFFavData.getInstance().getFav());
        }

        loadItems();
        setRetryListener();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        mSGView = (StaggeredGridView) v.findViewById(R.id.grid_view);
        setUpAdapter();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == FFDetailFragment.DETAIL_NEW_LIST){
            String title = data.getStringExtra(MainActivity.LIST_TITLE);
            mUrl = data.getStringExtra(FFListFragment.LIST_URL);
            getActivity().setTitle(title);

            //clear current list, update view and fetch new items
            mListData.clearList();
            mGalleryAdapter.notifyDataSetChanged();
            loadItems();
        }else if(resultCode == FFDetailFragment.DETAIL_TAB){
            //handle
        }else if(resultCode == FFDetailFragment.DETAIL_FAV_LIST){
            mUrl = "";
            getActivity().setTitle("Favorites");
            mListData.clearList();
            mListData.addItems(FFFavData.getInstance().getFav());
            mGalleryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (showFavs) {
            mListData.clearList();
            mListData.addItems(FFFavData.getInstance().getFav());
        }
        mGalleryAdapter.notifyDataSetChanged();
    }

    @Override
    public void FFFetchItem() {
        if (showFavs) {
            return;
        }
        mUrl = mListData.getNextUrl();
        loadItems();
    }
    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
//        Log.d(TAG, "onScrollStateChanged:" + scrollState);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), FFDetailActivity.class);
//        intent.putExtra(FFDetailFragment.ITEM_EXTRA, position);
        FFFFItem item = mListData.getItems(position);
        intent.putExtra(FFDetailFragment.ITEM_EXTRA, item);
        startActivityForResult(intent, FFDetailFragment.DETAIL_REQUEST);
    }

    @Override
    public void onScroll(final AbsListView view, final int un, final int deux, final int trois) {
//        Log.d(TAG, "onScroll firstVisibleItem:" + un + " visibleItemCount:" + deux + " totalItemCount:" + trois);
    }

    public void setUpAdapter(){
        if(getActivity() == null || mSGView == null) return;
        ArrayList<FFFFItem> items = mListData.getItems();

        if (items != null && mSGView.getAdapter() == null){
            mSGView.setAdapter(mGalleryAdapter);
            mSGView.setOnScrollListener(this);
            mSGView.setOnItemClickListener(this);
        }else if (items != null){
            mGalleryAdapter.notifyDataSetChanged();
            itemsShowing = true;
        }else{
            mSGView.setAdapter(null);
        }
    }
    private void loadItems() {
        TextView networkTxt = (TextView) getActivity().findViewById(R.id.network_state);
        Button retryBtn = (Button) getActivity().findViewById(R.id.retry);

        if ( Stuff.isConnected(getActivity()) ) {
            networkTxt.setVisibility(View.INVISIBLE);
            retryBtn.setVisibility(View.INVISIBLE);
            new FetchItemsAsync(mUrl).execute();
        } else {
            if (itemsShowing) {
                networkTxt.setVisibility(View.INVISIBLE);
                retryBtn.setVisibility(View.INVISIBLE);
                Toast noWifi = Toast.makeText(getActivity(),
                        getResources().getString(R.string.no_wifi), Toast.LENGTH_LONG);
                noWifi.show();
            }else {
                retryBtn.setBackgroundColor(Stuff.generateRandomColor(Color.WHITE));
                networkTxt.setTextColor(Stuff.generateRandomColor(Color.DKGRAY));
                networkTxt.setVisibility(View.VISIBLE);
                retryBtn.setVisibility(View.VISIBLE);
                networkTxt.setText(getResources().getString(R.string.lostWifi));
            }
        }
    }
    private void setRetryListener() {
        Button retryBtn = (Button) getActivity().findViewById(R.id.retry);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadItems();
            }
        });
    }


    //--------------------------------------PRIVATE CLASS---------------
    class FetchItemsAsync extends AsyncTask<Void,Void,ArrayList<FFFFItem>> {
        private String mUrl;

        private FetchItemsAsync(String url) {
            mUrl = url;
        }
        @Override
        protected ArrayList<FFFFItem> doInBackground(Void... params) {
            try{
                Log.d(TAG, mUrl);
                String result = new FFHttpRequest().getUrl(mUrl);
                ffFeedParser = new FFFeedParser(result, mListData);
                ArrayList<FFFFItem> ffGalleryItems = ffFeedParser.parse();
                return ffGalleryItems;
            } catch (IOException e) { Log.d(TAG, "Failed to get xml feed"); }
            return null;
        }
        @Override
        protected void onPostExecute(ArrayList<FFFFItem> galleryItems) {
            if (galleryItems!=null){
                mListData.addItems(galleryItems);
                setUpAdapter();
            }
        }
    }

}
