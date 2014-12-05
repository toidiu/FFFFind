package com.toidiu.ffffind.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.toidiu.ffffind.R;
import com.toidiu.ffffind.fragments.DetailFragment;
import com.toidiu.ffffind.model.FFData;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.utils.FetchItemsAsync;

import java.util.ArrayList;

public class DetailActivity extends FragmentActivity implements FetchItemsAsync.OnAsyncComplete
{
    public static final  String ITEM_POS = "com.toidiu.detail_item_position";
    private static final String TAG      = "DetailView";
    private FragmentManager           mFragManager;
    private DetailFragment            mFragment;
    private ViewPager                 mViewPager;
    private ArrayList<FFItem>         mItemList;
    private FFItem                    mItem;
    private Integer                   nextOffset;
    private FragmentStatePagerAdapter adapter;

    private void pageChangeListener()
    {
        final DetailActivity detailActivity = this;

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                if(mItemList.size() - 5 == position)
                {
                    nextOffset = FFData.getInstance().getNextOffset();
//                    new FetchItemsAsync(nextOffset, detailActivity).execute();
                }
                setTitle(mItem.getArtist());
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.clear_fav).setVisible(false);
        menu.findItem(R.id.explore).setVisible(false);
        //        menu.findItem(R.id.randomUser).setVisible(true);
        menu.findItem(R.id.favorite).setVisible(true);
        //        getActionBar().setHomeButtonEnabled(true);
        //        getActionBar().setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            // action with ID action_refresh was selected
            //            case R.id.randomUser:
            //                Toast.makeText(this, "Random user", Toast.LENGTH_SHORT).show();
            //                setResult(FFDetailFragment.DETAIL_RAND_USER_LIST);
            //                finish();
            //                break;
            case R.id.favorite:
                Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
                setResult(DetailFragment.DETAIL_FAV_LIST);
                finish();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onBackPressed()
    {
        setResult(DetailFragment.DETAIL_BACK);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.view_pager);
        setContentView(mViewPager);

        mItemList = FFData.getInstance().getItems();
        int pos = getIntent().getIntExtra(ITEM_POS, 0);
        mItem = mItemList.get(pos);

        mFragManager = getSupportFragmentManager();
        adapter = new FragmentStatePagerAdapter(mFragManager)
        {
            @Override
            public Fragment getItem(int position)
            {
                mItem = mItemList.get(position);
                mFragment = DetailFragment.newInstance(mItem);
                return mFragment;
            }

            @Override
            public int getCount()
            {
                return mItemList.size();
            }
        };


        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(pos);
        pageChangeListener();
    }

    @Override
    public void onAsyncComplete(ArrayList<FFItem> itemList)
    {
        FFData.getInstance().addItems(itemList);
        adapter.notifyDataSetChanged();
    }

}
