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
import com.toidiu.ffffound.fragments.FFDetailFragment;
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
    public static final String LIST_TITLE = "com.toidiu.artist_name";
    private static final String MAIN_LIST = "com.toidiu.main_list";
    public static final String SPARE_LIST = "com.toidiu.spare_list";

    private FragmentManager mFragManager;
    private Fragment mMainFragment;
    private Fragment mSpareFragment;
    private String url;
    private Bundle bundle;

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
        mMainFragment = mFragManager.findFragmentByTag(MAIN_LIST);
        if(mMainFragment == null){
            mMainFragment = new FFListFragment();
            mMainFragment.setArguments(bundle);
            mFragManager.beginTransaction()
                .add(R.id.frag_container, mMainFragment)
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
        int mOffset;

        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.randomMenu:
                Toast.makeText(this, "Random offset", Toast.LENGTH_SHORT).show();
                mOffset = new Random().nextInt(10000);
                url = FFListFragment.RANDOM_URL_BASE + mOffset;
                Log.d(TAG, url);
                setTitle("Offset: " + mOffset);

                //set URL
                bundle = new Bundle();
                bundle.putCharSequence(FFListFragment.LIST_URL, url);

                mFragManager = getSupportFragmentManager();
                mSpareFragment = mFragManager.findFragmentByTag(SPARE_LIST);
                if(mSpareFragment == null){
                    mSpareFragment = new FFListFragment();
                    mSpareFragment.setArguments(bundle);
                    mFragManager.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.frag_container, mSpareFragment)
                            .commit();
                }else {
                    mSpareFragment.setArguments(bundle);
                    mFragManager.beginTransaction()
                            .replace(R.id.frag_container, mSpareFragment)
                            .commit();
                }
                break;
            case R.id.randomUser:
                Toast.makeText(this, "Random user", Toast.LENGTH_SHORT).show();
                String[] randUserList = FFFavData.getInstance().getUsers();
                int rand = new Random().nextInt(randUserList.length);
                String randUser = randUserList[rand];

                url = FFListFragment.SPARE_URL_BASE + randUser + FFListFragment.SPARE_URL_END;
                Log.d(TAG, url);
                setTitle(randUser);

                bundle = new Bundle();
                bundle.putCharSequence(FFListFragment.LIST_URL, url);

                mFragManager = getSupportFragmentManager();
                mSpareFragment = mFragManager.findFragmentByTag(SPARE_LIST);
                if(mSpareFragment == null){
                    mSpareFragment = new FFListFragment();
                    mSpareFragment.setArguments(bundle);
                    mFragManager.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.frag_container, mSpareFragment)
                            .commit();
                }else {
                    mSpareFragment.setArguments(bundle);
                    mFragManager.beginTransaction()
                            .replace(R.id.frag_container, mSpareFragment)
                            .commit();
                }
                break;
            case R.id.favorite:
                Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
                setTitle("Favorites");
                //set URL
                bundle = new Bundle();
                bundle.putCharSequence(FFListFragment.LIST_URL, "");
                bundle.putBoolean(FFListFragment.SHOW_FAV, true);

                mFragManager = getSupportFragmentManager();
                mSpareFragment = mFragManager.findFragmentByTag(SPARE_LIST);
                if(mSpareFragment == null){
                    mSpareFragment = new FFListFragment();
                    mSpareFragment.setArguments(bundle);
                    mFragManager.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.frag_container, mSpareFragment)
                            .commit();
                }else {
                    mSpareFragment.setArguments(bundle);
                    mFragManager.beginTransaction()
                            .replace(R.id.frag_container, mSpareFragment)
                            .commit();
                }
                break;
            case R.id.clear_fav:
                Toast.makeText(this, "Favorites Cleared!", Toast.LENGTH_SHORT).show();
                FFFavData.getInstance().clearFav();
                break;
            default:
                break;
        }

        return true;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (resultCode == FFDetailFragment.DETAIL_USER_LIST) {
//            String title = data.getStringExtra(MainActivity.LIST_TITLE);
//            url = data.getStringExtra(FFListFragment.LIST_URL);
//            setTitle(title);
//
//            bundle = new Bundle();
//            bundle.putCharSequence(FFListFragment.LIST_URL, url);
//
//            mSpareFragment = mFragManager.findFragmentByTag(SPARE_LIST);
//            if (mSpareFragment == null) {
//                mSpareFragment = new FFListFragment();
//                mSpareFragment.setArguments(bundle);
//                mFragManager.beginTransaction()
//                        .addToBackStack(null)
//                        .replace(R.id.frag_container, mSpareFragment)
//                        .commitAllowingStateLoss();
////                        .commit();
//            }else {
//                mSpareFragment.setArguments(bundle);
//                mFragManager.beginTransaction()
//                        .replace(R.id.frag_container, mSpareFragment)
//                        .commit();
//            }
//        }else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
}
