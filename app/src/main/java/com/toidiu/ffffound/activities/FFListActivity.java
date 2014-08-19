package com.toidiu.ffffound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.toidiu.ffffound.R;
import com.toidiu.ffffound.fragments.FFListFragment;
import com.toidiu.ffffound.model.FFFavData;

import java.util.Random;

public class FFListActivity extends FragmentActivity{
    public static final String LIST_TITLE = "com.toidiu.artist_name";
    private static final String TAG = "List Activity";

    private FragmentManager mFragManager;
    private Fragment mFragment;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        setTitle(getIntent().getStringExtra(LIST_TITLE));

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mOffset;
        Intent mIntent;

        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.randomMenu:
                Toast.makeText(this, "Random offset", Toast.LENGTH_SHORT)
                        .show();
                mOffset = new Random().nextInt(10000);
                url = FFListFragment.RANDOM_URL_BASE + mOffset;
                Log.d(TAG, url);

                mIntent = new Intent(this, FFListActivity.class);
                mIntent.putExtra(FFListFragment.LIST_URL, url);
                mIntent.putExtra(FFListActivity.LIST_TITLE, "Random: " + mOffset);
                startActivity(mIntent);

                break;
            case R.id.randomUser:
                Toast.makeText(this, "Random user", Toast.LENGTH_SHORT)
                        .show();

                String[] randUserList = FFFavData.getInstance().getUsers();
                int rand = new Random().nextInt(randUserList.length);
                String randUser = randUserList[rand];

                url = FFListFragment.SPARE_URL_BASE + randUser + FFListFragment.SPARE_URL_END;
                Log.d(TAG, url);

                mIntent = new Intent(this, FFListActivity.class);
                mIntent.putExtra(FFListFragment.LIST_URL, url);
                mIntent.putExtra(FFListActivity.LIST_TITLE, randUser);
                startActivity(mIntent);

                break;
            default:
                break;
        }

        return true;
    }

}
