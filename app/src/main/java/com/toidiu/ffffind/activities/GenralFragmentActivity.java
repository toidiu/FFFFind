package com.toidiu.ffffind.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.toidiu.ffffind.R;
import com.toidiu.ffffind.fragments.FFListFragment;


/**
 * Created by toidiu on 10/26/14.
 */
public abstract class GenralFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.frag_container, createFragment())
                .commit();
    }


    protected abstract Fragment createFragment();


    public void switchFragment(Fragment fragment){
        FragmentManager mFragManager = getSupportFragmentManager();

        mFragManager.beginTransaction()
                .add(R.id.frag_container, fragment)
                .commit();

    }
}
