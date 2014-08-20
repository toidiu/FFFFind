package com.toidiu.ffffound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.fragments.FFListFragment;
import com.toidiu.ffffound.model.FFFFItem;
import com.toidiu.ffffound.model.FFFavData;
import com.toidiu.ffffound.utils.SaveLoadHandler;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "Main Activity";
    private FragmentManager mFragManager;
    private Fragment mFragment;
    int mOffset;

    private SaveLoadHandler<ArrayList<FFFFItem>> slh;
    private final static String SAVE_FILE = "fav.json";
    public File FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //Save/Load handler
        FILE = new File(this.getFilesDir() + File.separator + SAVE_FILE);
        Type type = new TypeToken<ArrayList<FFFFItem>>(){}.getType();
        slh = new SaveLoadHandler(type, FILE);
        final ArrayList<FFFFItem> list = slh.loadData();
        FFFavData.getInstance().setFav(list);

        //pass Everyone URL
        Bundle bundle = new Bundle();
        bundle.putCharSequence(FFListFragment.LIST_URL, FFListFragment.EVERYONE_URL);

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
    protected void onPause() {
        super.onPause();
        slh.saveData(FFFavData.getInstance().getFav() );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent mIntent;
        String url;

        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.randomMenu:
                Toast.makeText(this, "Random selected", Toast.LENGTH_SHORT).show();
                mOffset = new Random().nextInt(10000);
                url = FFListFragment.RANDOM_URL_BASE + mOffset;
                Log.d(TAG, url);

                mIntent = new Intent(this, FFListActivity.class);
                mIntent.putExtra(FFListFragment.LIST_URL, url);
                mIntent.putExtra(FFListActivity.LIST_TITLE, "Offset: " + mOffset);
                startActivity(mIntent);
                break;
            case R.id.randomUser:
                Toast.makeText(this, "Random user", Toast.LENGTH_SHORT).show();
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
