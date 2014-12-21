package com.toidiu.ffffind.fragments;

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
import com.toidiu.ffffind.BuildConfig;
import com.toidiu.ffffind.R;
import com.toidiu.ffffind.activities.DetailActivity;
import com.toidiu.ffffind.activities.ListActivity;
import com.toidiu.ffffind.adapter.ListAdapter;
import com.toidiu.ffffind.model.FFData;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FavData;
import com.toidiu.ffffind.tasks.LoadNextFItemListEvent;
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
    private boolean fav;

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
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        EventBus.getDefault().register(this);

        fav = getArguments().getBoolean(SHOW_FAV_EXTRA, false);
        if(fav == true)
        {
            nextOffset = null;
            adapter = new ListAdapter(getActivity(), FavData.getInstance().getFav());
        }
        else
        {
            adapter = new ListAdapter(getActivity(), FFData.getInstance().getItems());
            nextOffset = getArguments().getInt(LIST_OFFSET_EXTRA, 0);
            loadItems();
        }

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
    public void onDestroy()
    {
        super.onDestroy();
        FFData.getInstance().clearList();
        EventBus.getDefault().unregister(this);
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
        if(trois - un <= 10 && trois > 0 && running == false && !fav)
        {
            running = true;
            nextOffset = FFData.getInstance().getNextOffset();
            Log.e("-scroll-----------", "");
            loadItems();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        DetailActivity.callMe(getActivity(), position, fav);
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
            new LoadNextFItemListEvent(getActivity(), nextOffset);
        }
        else
        {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_wifi),
                           Toast.LENGTH_LONG).show();
        }
    }

    //----------------EVENTBUS----------------
    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(LoadNextFItemListEvent event)
    {
        if(BuildConfig.DEBUG)
        {
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
