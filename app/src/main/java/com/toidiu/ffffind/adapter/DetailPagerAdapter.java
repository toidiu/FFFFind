package com.toidiu.ffffind.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.toidiu.ffffind.fragments.DetailFragment;
import com.toidiu.ffffind.model.FFItem;

import java.util.ArrayList;

/**
 * Created by toidiu on 12/7/14.
 */
public class DetailPagerAdapter extends FragmentStatePagerAdapter
{

    private ArrayList<FFItem> items;

    public DetailPagerAdapter(FragmentManager fm, ArrayList<FFItem> items)
    {
        super(fm);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position)
    {
        return DetailFragment.newInstance(items.get(position));

    }

    @Override
    public int getCount()
    {
        return items.size();
    }
}
