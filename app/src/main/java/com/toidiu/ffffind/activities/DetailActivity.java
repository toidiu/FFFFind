package com.toidiu.ffffind.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.toidiu.ffffind.R;
import com.toidiu.ffffind.adapter.DetailPagerAdapter;
import com.toidiu.ffffind.model.FFData;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FavData;

import java.util.ArrayList;

public class DetailActivity extends FragmentActivity
{
    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Constants
    public static final String ITEM_POS_EXTRA = "item_position_extra";
    public static final String FAV_DATA_EXTRA = "fav_data_extra";

    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Fields
    private FragmentStatePagerAdapter adapter;

    public static void callMe(Activity activity, int position, boolean fav)
    {
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(ITEM_POS_EXTRA, position);
        intent.putExtra(FAV_DATA_EXTRA, fav);
        activity.startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setId(R.id.view_pager);
        setContentView(viewPager);

        boolean fav = getIntent().getBooleanExtra(FAV_DATA_EXTRA, false);
        ArrayList<FFItem> items = fav
                ? FavData.getInstance().getFavs()
                : FFData.getInstance().getItems();
        adapter = new DetailPagerAdapter(getSupportFragmentManager(), items);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(getIntent().getIntExtra(ITEM_POS_EXTRA, 0));
    }

}
