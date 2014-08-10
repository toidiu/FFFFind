package com.toidiu.ffffound.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.toidiu.ffffound.R;
import com.toidiu.ffffound.fragments.FFListFragment;


public class MainActivity extends ActionBarActivity {
    private FragmentManager mFragManager;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        setContentView(R.layout.activity_fragment);
        mFragManager = getSupportFragmentManager();
        mFragment = mFragManager.findFragmentById(R.id.frag_container);

        if(mFragment == null){
            mFragment = new FFListFragment();
            mFragManager.beginTransaction()
                    .add(R.id.frag_container, mFragment)
                    .commit();
        }

    }
}
