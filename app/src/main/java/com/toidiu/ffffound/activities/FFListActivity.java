package com.toidiu.ffffound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.toidiu.ffffound.R;
import com.toidiu.ffffound.fragments.FFListFragment;

public class FFListActivity extends FragmentActivity{
    public static final String ARTIST_NAME = "com.toidiu.artist_name";

    private FragmentManager mFragManager;
    private Fragment mFragment;
    private String url;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        setTitle( getIntent().getStringExtra(ARTIST_NAME) );

        //set URL
        url = getIntent().getStringExtra(FFListFragment.LIST_URL);
        Bundle bundle = new Bundle();
        bundle.putCharSequence(FFListFragment.LIST_URL, url);

        mFragManager = getSupportFragmentManager();
        mFragment = mFragManager.findFragmentById(R.id.frag_container);
        if(mFragment == null){
            mFragment = new FFListFragment();
            mFragment.setArguments(bundle);
            mFragManager.beginTransaction()
                .add(R.id.frag_container, mFragment)
                .commit();
        }
    }
}
