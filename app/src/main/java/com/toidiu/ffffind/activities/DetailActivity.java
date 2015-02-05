package com.toidiu.ffffind.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.toidiu.ffffind.R;
import com.toidiu.ffffind.adapter.DetailPagerAdapter;
import com.toidiu.ffffind.fragments.DetailFragment;
import com.toidiu.ffffind.model.FFData;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.utils.FetchItemsAsync;

import java.util.ArrayList;

public class DetailActivity extends FragmentActivity implements FetchItemsAsync.OnAsyncComplete
{
    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Constants
    public static final String ITEM_POS = "com.toidiu.detail_item_position";
    public static final int    DETAIL_BACK           = 5523;

    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Fields
    private FragmentStatePagerAdapter adapter;

    public static void callMe(Activity activity, int position)
    {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailActivity.ITEM_POS, position);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setId(R.id.view_pager);
        setContentView(viewPager);

        adapter = new DetailPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getIntent().getIntExtra(ITEM_POS, 0));
    }

    @Override
    public void onBackPressed()
    {
        setResult(DETAIL_BACK);
        finish();
    }

    @Override
    public void onAsyncComplete(ArrayList<FFItem> itemList)
    {
        FFData.getInstance().addItems(itemList);
        adapter.notifyDataSetChanged();
    }

}
