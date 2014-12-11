package com.toidiu.ffffind.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.etsy.android.grid.StaggeredGridView;
import com.toidiu.ffffind.BuildConfig;
import com.toidiu.ffffind.R;
import com.toidiu.ffffind.activities.BaseFragmentActivity;
import com.toidiu.ffffind.activities.DetailActivity;
import com.toidiu.ffffind.activities.ListActivity;
import com.toidiu.ffffind.adapter.ListAdapter;
import com.toidiu.ffffind.model.FFData;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.tasks.LoadNextItemListEvent;
import com.toidiu.ffffind.utils.Stuff;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class ListFragment extends Fragment implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener
{
    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Constants
    public static final String SHOW_FAV_EXTRA    = "com.toidiu.show_fav";
    public static final String LIST_OFFSET_EXTRA = "com.toidiu.list_offset";

    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=View/Adapter
    StaggeredGridView staggeredGridView;
    private ListAdapter adapter;

    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Fields
    private Integer nextOffset;
    private boolean running;

    public static ListFragment newInstance(Integer offset, boolean fav)
    {
        Bundle bundle = new Bundle();
        bundle.putBoolean(SHOW_FAV_EXTRA, fav);
        bundle.putInt(LIST_OFFSET_EXTRA, offset);

        ListFragment fragment = new ListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public static void injectNewList(FragmentActivity activity, Integer offset)
    {
        ListFragment fragment = (ListFragment) activity.getSupportFragmentManager()
                                                       .findFragmentByTag(
                                                               BaseFragmentActivity.LIST_FRAGMENT_TAG);
        if(fragment != null)
        {
            fragment.nextOffset = offset;
            FFData.getInstance().clearList();
            fragment.adapter.notifyDataSetInvalidated();

            Log.e("-inject-----------","");
            fragment.loadItems();
        }
        else
        {
            Crashlytics.log(Log.ERROR, "Log this error", "bad stuff happened!");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        EventBus.getDefault().register(this);

        adapter = new ListAdapter(getActivity());

        if(getArguments().containsKey(LIST_OFFSET_EXTRA))
        {
            nextOffset = getArguments().getInt(LIST_OFFSET_EXTRA, 0);
            Log.e("-create-----------","");
            loadItems();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("-0pause-----------", "");
    }

    @Override
    public void onDestroy()
    {
        Log.e("-destroy-----------","");

        super.onDestroy();
        FFData.getInstance().clearList();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        staggeredGridView = (StaggeredGridView) v.findViewById(R.id.list_view);
        setUpAdapter();
        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        adapter.notifyDataSetChanged();
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
            nextOffset = FFData.getInstance().getNextOffset();
            Log.e("-scroll-----------","");
            loadItems();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        DetailActivity.callMe(getActivity(), position);
    }

    public void setUpAdapter()
    {
        if(getActivity() == null || staggeredGridView == null)
        {
            return;
        }
        ArrayList<FFItem> items = FFData.getInstance().getItems();

        if(items != null && staggeredGridView.getAdapter() == null)
        {
            staggeredGridView.setAdapter(adapter);
            staggeredGridView.setOnScrollListener(this);
            staggeredGridView.setOnItemClickListener(this);
        }
        else if(items != null)
        {
            adapter.notifyDataSetChanged();
        }
        else
        {
            staggeredGridView.setAdapter(null);
        }
    }

    private void loadItems()
    {
        if(Stuff.isConnected(getActivity()))
        {
            ((ListActivity) getActivity()).enableProgressBar();

            new LoadNextItemListEvent(nextOffset);
        }
        else
        {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_wifi),
                           Toast.LENGTH_LONG).show();
        }
    }

    //----------------EVENTBUS----------------
    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(LoadNextItemListEvent event)
    {
        if (BuildConfig.DEBUG) {
            Toast.makeText(getActivity(), "got response", Toast.LENGTH_SHORT).show();
        }
        running = false;
        if(event.items != null)
        {
            FFData.getInstance().addItems(event.items);
            setUpAdapter();
        }

        ((ListActivity) getActivity()).disableProgressBar();

    }

}
