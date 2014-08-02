package com.toidiu.ffffound;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


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
            mFragment = new AwwFragment();
            mFragManager.beginTransaction()
                    .add(R.id.frag_container, mFragment)
                    .commit();
        }

    }


}
