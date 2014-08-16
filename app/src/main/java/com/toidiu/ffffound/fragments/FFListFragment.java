package com.toidiu.ffffound.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.activities.FFDetailActivity;
import com.toidiu.ffffound.adapter.FFGalleryAdapter;
import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;
import com.toidiu.ffffound.utils.FFFeedParser;
import com.toidiu.ffffound.utils.FFHttpRequest;
import com.toidiu.ffffound.utils.Stuff;

import java.io.IOException;
import java.util.ArrayList;



public class FFListFragment extends Fragment implements FFGalleryAdapter.FFFetcherInterface,
        AbsListView.OnScrollListener, AbsListView.OnItemClickListener {
    private static final String TAG = "FFFragment";
    private boolean itemsShowing = false;

    public static final String LIST_URL = "com.toidiu.list_url";
    public static final String EVERYONEUrl = "http://ffffound.com/feed";
    public static final String SPAREUrlBase = "http://ffffound.com/home/"; //+ user + SPAREUrlEnd
    public static final String SPAREUrlEnd = "/found/feed";

    private String mUrl;
    private FFGalleryAdapter mGalleryAdapter;
    private StaggeredGridView mSGView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mUrl = getArguments().getCharSequence(LIST_URL).toString();
        if (mUrl == EVERYONEUrl){
            mGalleryAdapter = new FFGalleryAdapter( getActivity(), this);
        }else{
            mGalleryAdapter = new FFGalleryAdapter( getActivity(), this);
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
    public void FFFFItem() {
        mUrl = FFData.getInstance().getNextUrl();
        loadItems();
    }
    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        Log.d(TAG, "onScrollStateChanged:" + scrollState);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//        Toast.makeText(getActivity(), "Item Clicked: " + position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), FFDetailActivity.class);
        intent.putExtra(FFDetailFragment.ITEM_IDX, position);
        startActivity(intent);
    }
    @Override
    public void onScroll(final AbsListView view, final int un, final int deux, final int trois) {
        Log.d(TAG, "onScroll firstVisibleItem:" + un + " visibleItemCount:" + deux +
                " totalItemCount:" + trois);
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
            itemsShowing = true;
        }else{
            mSGView.setAdapter(null);
        }
    }
    private void loadItems() {
        TextView networkTxt = (TextView) getActivity().findViewById(R.id.network_state);
        Button retryBtn = (Button) getActivity().findViewById(R.id.retry);

        if (Stuff.isConnected(getActivity())) {
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
            if (galleryItems!=null){
                FFData.getInstance().addItems(galleryItems);
                setUpAdapter();
            }
        }
    }

}
