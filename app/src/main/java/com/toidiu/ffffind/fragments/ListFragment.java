package com.toidiu.ffffind.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.toidiu.ffffind.R;
import com.toidiu.ffffind.activities.DetailActivity;
import com.toidiu.ffffind.adapter.GalleryAdapter;
import com.toidiu.ffffind.model.FFData;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FavData;
import com.toidiu.ffffind.utils.FetchItemsAsync;
import com.toidiu.ffffind.utils.Stuff;

import java.util.ArrayList;


public class ListFragment extends Fragment implements GalleryAdapter.FFFetcherInterface,
        AbsListView.OnScrollListener, AbsListView.OnItemClickListener, FetchItemsAsync.OnAsyncComplete {
    private static final String TAG = "FFListFragment";

    public static final String LIST_URL = "com.toidiu.list_url";
    public static final String SHOW_FAV = "com.toidiu.show_fav";
    public static final String EVERYONE_URL = "http://ffffound.com/feed";
    public static final String USER_URL_BASE = "http://ffffound.com/home/"; //+ user + SPAREUrlEnd
    public static final String USER_URL_END = "/found/feed";
    public static final String EXPLORE_URL_BASE = "http://ffffound.com/feed?offset="; //+ number

    public static String mURL;
    private boolean itemsShowing = false;
    private StaggeredGridView mSGView;
    private GalleryAdapter mGalleryAdapter;
    public FFData mListData;
    private boolean showFavs;

    public static ListFragment newInstance(String url, boolean fav){
        FFData.getInstance().clearList();
        mURL = url;

        Bundle bundle = new Bundle();
        bundle.putBoolean(SHOW_FAV, fav);

        ListFragment fragment = new ListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mListData = FFData.getInstance();
        mGalleryAdapter = new GalleryAdapter(getActivity(), this, mListData);

        showFavs = getArguments().getBoolean(SHOW_FAV, false);
        if (showFavs) {
            mListData.addItems(FavData.getInstance().getFav());
            FFData.getInstance().setNextUrl("");
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
        if(resultCode == DetailFragment.DETAIL_TAB){
            //handle
            Log.d(TAG, "detail_tab");
        }else if(resultCode == DetailFragment.DETAIL_BACK) {
//            mListData.clearList();

//            if (showFavs) {
//                mListData.addItems(FFFavData.getInstance().getFav());
//            }else {
                mListData.addItems( FFData.getInstance().getItems() );
//            }
            mGalleryAdapter.notifyDataSetChanged();
            Log.d(TAG, "detail back" + mSGView.getDistanceToTop());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (showFavs) {
            mListData.clearList();
            mListData.addItems(FavData.getInstance().getFav());
        }
        mGalleryAdapter.notifyDataSetChanged();
    }

    @Override
    public void FFFetchItem() {
        if (showFavs) {
            return;
        }
        mURL = mListData.getNextUrl();
        loadItems();
    }
    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
//        Log.d(TAG, "onScrollStateChanged:" + scrollState);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.ITEM_POS, position);
//        FFFFItem item = mListData.getItems(position);
//        intent.putExtra(FFDetailFragment.ITEM_EXTRA, item);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onScroll(final AbsListView view, final int un, final int deux, final int trois) {
//        Log.d(TAG, "onScroll firstVisibleItem:" + un + " visibleItemCount:" + deux + " totalItemCount:" + trois);
    }

    public void setUpAdapter(){
        if(getActivity() == null || mSGView == null) return;
        ArrayList<FFItem> items = mListData.getItems();

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
            new FetchItemsAsync(mURL, this).execute();
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
    public static String getURL(){
        if (mURL == null){
            return "";
        }
        return mURL;
    }

    @Override
    public void onAsyncComplete(ArrayList<FFItem> itemList) {
        mListData.addItems(itemList);
        setUpAdapter();
    }

}
