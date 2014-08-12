package com.toidiu.ffffound.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import static com.toidiu.ffffound.adapter.FFGalleryAdapter.FFFetcherInterface;


public class FFListFragment extends Fragment implements FFFetcherInterface,
        AbsListView.OnScrollListener, AbsListView.OnItemClickListener {
    private static final String TAG = "FFFragment";
    public static final String LIST_URL = "com.toidiu.list_url";
    public static final String ADAPTER_CHOICE = "com.toidiu.adapter";
    private boolean itemsShowing = false;


    public static String EVERYONEURL = "http://ffffound.com/feed";
    public static String USERURLBASE = "http://ffffound.com/home/";

    private String mUrl;
    private FFGalleryAdapter mGalleryAdapter;
    private StaggeredGridView mSGView;
    private boolean userAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mUrl = getArguments().getString(LIST_URL);
        userAdapter = getArguments().getBoolean(ADAPTER_CHOICE, false);
        Log.d("-------------ddsssss-----------",mUrl);
        if (userAdapter){

        }else{
            mGalleryAdapter = new FFGalleryAdapter( getActivity(), this);
        }

        testNetwork();
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
        new FetchItemsTask(FFData.getInstance().getNextUrl()).execute();
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

    private void setRetryListener() {
        Button retryBtn = (Button) getActivity().findViewById(R.id.retry);

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testNetwork();
            }
        });
    }

    private void testNetwork() {
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        TextView networkTxt = (TextView) getActivity().findViewById(R.id.network_state);
        Button retryBtn = (Button) getActivity().findViewById(R.id.retry);

        if (networkInfo != null) {
            networkTxt.setVisibility(View.INVISIBLE);
            retryBtn.setVisibility(View.INVISIBLE);
            new FetchItemsTask(mUrl).execute();
            itemsShowing = true;

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
                networkTxt.setText("you LLLLOST the internet!");
            }

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
            if (galleryItems!=null){
                FFData.getInstance().addItems(galleryItems);
                setUpAdapter();
            }else{
                testNetwork();
            }
        }
    }

}
