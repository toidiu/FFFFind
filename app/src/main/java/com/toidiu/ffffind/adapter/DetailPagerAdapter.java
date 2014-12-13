package com.toidiu.ffffind.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.toidiu.ffffind.fragments.DetailFragment;
import com.toidiu.ffffind.model.FFData;

/**
 * Created by toidiu on 12/7/14.
 */
public class DetailPagerAdapter extends FragmentStatePagerAdapter
{
    public DetailPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return DetailFragment.newInstance(FFData.getInstance().getItems(position));

    }

    @Override
    public int getCount()
    {
        return FFData.getInstance().getSize();
    }
}
