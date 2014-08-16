package com.toidiu.ffffound.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.toidiu.ffffound.R;
import com.toidiu.ffffound.fragments.FFListFragment;

public class FFListActivity extends FragmentActivity{
    private FragmentManager mFragManager;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

//        Intent intent = getIntent();
//        String url = intent.getStringExtra(FFListFragment.LIST_URL);

        //set base URL
        Bundle bundle = new Bundle();
        bundle.putCharSequence(FFListFragment.LIST_URL, FFListFragment.SPARE_URL_BASE);

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
