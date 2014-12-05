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
import com.toidiu.ffffind.activities.tasks.BuildFItemsEvent;
import com.toidiu.ffffind.adapter.ListAdapter;
import com.toidiu.ffffind.model.FFData;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.network.FoundApi;
import com.toidiu.ffffind.network.FoundRestHelper;
import com.toidiu.ffffind.utils.Stuff;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import retrofit.client.Response;


public class ListFragment extends Fragment implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener
{
    public static final String SHOW_FAV_EXTRA    = "com.toidiu.show_fav";
    public static final String LIST_OFFSET_EXTRA = "com.toidiu.list_offset";

    private static final String TAG = "FFListFragment";
    public  FFData  mListData;
    private Integer nextOffset;
    private boolean itemsShowing = false;
    private StaggeredGridView mSGView;
    private ListAdapter       mListAdapter;
    private boolean           running;

    public static ListFragment newInstance(Integer offset, boolean fav)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean(SHOW_FAV_EXTRA, fav);
        bundle.putInt(LIST_OFFSET_EXTRA, offset);

        ListFragment fragment = new ListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == DetailFragment.DETAIL_TAB)
        {
            //handle
            Log.d(TAG, "detail_tab");
        }
        else if(resultCode == DetailFragment.DETAIL_BACK)
        {
            mListData.addItems(FFData.getInstance().getItems());
            mListAdapter.notifyDataSetChanged();
            Log.d(TAG, "detail back" + mSGView.getDistanceToTop());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        EventBus.getDefault().register(this);

        mListData = FFData.getInstance();
        mListAdapter = new ListAdapter(getActivity(), mListData);

        nextOffset = getArguments().getInt(LIST_OFFSET_EXTRA, 0);
        if(nextOffset != 0)
        {
            nextOffset = Stuff.getRandOffset();
            loadItems();
        }
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
    public void onResume()
    {
        super.onResume();
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState)
    {
        //                Log.d(TAG, "onScrollStateChanged:" + scrollState);
    }

    @Override
    public void onScroll(final AbsListView view, final int un, final int deux, final int trois)
    {
        //        Log.d(TAG, "onScroll firstVisibleItem:" + un + " visibleItemCount:" + deux + " totalItemCount:" + trois);
        if(trois - un <= 10 && trois > 0 && running == false)
        {
            running = true;
            //            if(showFavs)
            //            {
            //                return;
            //            }
            nextOffset = mListData.getNextOffset();
            loadItems();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.ITEM_POS, position);
        //        FFFFItem item = mListData.getItems(position);
        //        intent.putExtra(FFDetailFragment.ITEM_EXTRA, item);
        startActivityForResult(intent, 0);
    }

    public void setUpAdapter()
    {
        if(getActivity() == null || mSGView == null)
        {
            return;
        }
        ArrayList<FFItem> items = mListData.getItems();

        if(items != null && mSGView.getAdapter() == null)
        {
            mSGView.setAdapter(mListAdapter);
            mSGView.setOnScrollListener(this);
            mSGView.setOnItemClickListener(this);
        }
        else if(items != null)
        {
            mListAdapter.notifyDataSetChanged();
            itemsShowing = true;
        }
        else
        {
            mSGView.setAdapter(null);
        }
    }

    private void loadItems()
    {

        if(Stuff.isConnected(getActivity()))
        {

            new Thread(new Runnable()
            {
                public void run()
                {
                    Response response = FoundRestHelper.makeRequest().create(FoundApi.class)
                            .offsetFeed(nextOffset);
                    EventBus.getDefault().post(new BuildFItemsEvent(response));
                }
            }).start();


            //                        new FetchItemsAsync(mURL, this).execute();
        }
        else
        {
            if(itemsShowing)
            {
                Toast.makeText(getActivity(), getResources().getString(R.string.no_wifi),
                               Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity(), getResources().getString(R.string.no_wifi),
                               Toast.LENGTH_LONG).show();

            }
        }
    }

    //----------------EVENTBUS----------------
    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(BuildFItemsEvent event)
    {
        Toast.makeText(getActivity(), "got response", Toast.LENGTH_SHORT).show();

        running = false;
        if(event.items != null)
        {
            mListData.addItems(event.items);
            setUpAdapter();
        }
    }

}
