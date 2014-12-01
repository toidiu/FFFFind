package com.toidiu.ffffind.fragments;

import android.content.Intent;
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
import com.toidiu.ffffind.R;
import com.toidiu.ffffind.activities.DetailActivity;
import com.toidiu.ffffind.activities.tasks.TestTask;
import com.toidiu.ffffind.adapter.ListAdapter;
import com.toidiu.ffffind.model.FFData;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FavData;
import com.toidiu.ffffind.network.FoundApi;
import com.toidiu.ffffind.network.FoundRestHelper;
import com.toidiu.ffffind.utils.FetchItemsAsync;
import com.toidiu.ffffind.utils.Stuff;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import retrofit.client.Response;


public class ListFragment extends Fragment implements ListAdapter.FFFetcherInterface,
        AbsListView.OnScrollListener, AbsListView.OnItemClickListener {
    private static final String TAG = "FFListFragment";

    public static final String SHOW_FAV_EXTRA = "com.toidiu.show_fav";
    public static final String LIST_URL_EXTRA = "com.toidiu.list_url";

    public static final String EVERYONE_URL     = "http://ffffound.com/feed";
    public static final String USER_URL_BASE    = "http://ffffound.com/home/"; //+ user + SPAREUrlEnd
    public static final String USER_URL_END     = "/found/feed";
    public static final String EXPLORE_URL_BASE = "http://ffffound.com/feed?offset="; //+ number

    private String mURL;
    private boolean itemsShowing = false;
    private StaggeredGridView mSGView;
    private ListAdapter       mListAdapter;
    public  FFData            mListData;
    private boolean           showFavs;

    public static ListFragment newInstance(String url, boolean fav)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean(SHOW_FAV_EXTRA, fav);
        bundle.putString(LIST_URL_EXTRA, url);

        ListFragment fragment = new ListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        EventBus.getDefault().register(this);

        mListData = FFData.getInstance();
        mListAdapter = new ListAdapter(getActivity(), this, mListData);

        mURL = getArguments().getString(LIST_URL_EXTRA, null);
        showFavs = getArguments().getBoolean(SHOW_FAV_EXTRA, false);
        if(showFavs)
        {
            mListData.addItems(FavData.getInstance().getFav());
            FFData.getInstance().setNextUrl("");
        }

        loadItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
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
            mListData.addItems( FFData.getInstance().getItems() );
            mListAdapter.notifyDataSetChanged();
            Log.d(TAG, "detail back" + mSGView.getDistanceToTop());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mListAdapter.notifyDataSetChanged();
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
            mSGView.setAdapter(mListAdapter);
            mSGView.setOnScrollListener(this);
            mSGView.setOnItemClickListener(this);
        }else if (items != null){
            mListAdapter.notifyDataSetChanged();
            itemsShowing = true;
        }else{
            mSGView.setAdapter(null);
        }
    }

    private void loadItems() {

        if ( Stuff.isConnected(getActivity()) ) {

            new Thread(new Runnable() {
                public void run() {
                    Response response = FoundRestHelper.makeRequest().create(FoundApi.class)
                            .offsetFeed(Stuff.getRandOffset());
                    EventBus.getDefault().post(new TestTask(response));
                }
            }).start();


//                        new FetchItemsAsync(mURL, this).execute();
        } else {
            if (itemsShowing) {
                Toast noWifi = Toast.makeText(getActivity(),
                        getResources().getString(R.string.no_wifi), Toast.LENGTH_LONG);
                noWifi.show();
            }else {
                Toast noWifi = Toast.makeText(getActivity(),
                        getResources().getString(R.string.no_wifi), Toast.LENGTH_LONG);

            }
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(TestTask event){
        Toast.makeText(getActivity(), "got response", Toast.LENGTH_SHORT).show();

        if (event.items!=null){
            mListData.addItems(event.items);
            setUpAdapter();
        }
    }

}
