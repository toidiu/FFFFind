package com.toidiu.ffffound.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.toidiu.ffffound.R;
import com.toidiu.ffffound.fragments.FFListFragment;

import java.util.Random;

public class SplashGridActivity extends ActionBarActivity {
    private static final String TAG = "asdf";

    private FragmentManager mFragManager;
    private Fragment mMainFragment;
    private String url;
    private Bundle bundle;
    private int mOffset;
    private static final String MAIN_LIST = "com.toidiu.main_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_grid);

        setExploreListener();
        setRecentListener();
        setFavListener();
    }

    private void setFavListener() {
        Button favBtn = (Button) findViewById(R.id.grid_fav);

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setRecentListener() {
        Button recentBtn = (Button) findViewById(R.id.grid_recent);

        recentBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Toast.makeText(this, "Explore", Toast.LENGTH_SHORT).show();
                mOffset = new Random().nextInt(getResources().getInteger(R.integer.rand_cnt));
                url = FFListFragment.EXPLORE_URL_BASE + mOffset;
                Log.d(TAG, url);
                setTitle("Explore");

                //set mURL
                bundle = new Bundle();
                bundle.putCharSequence(FFListFragment.LIST_URL, url);

                mFragManager = getSupportFragmentManager();
                mMainFragment = mFragManager.findFragmentByTag(MAIN_LIST);
                if (mMainFragment == null) {
                    mMainFragment = new FFListFragment();
                }
                mMainFragment.setArguments(bundle);
                mFragManager.beginTransaction()
                        .add(R.id.frag_container, mMainFragment)

                        .commit();

            }
        });
    }

    private void setExploreListener() {
        Button exploreBtn = (Button) findViewById(R.id.grid_explore);
        exploreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
