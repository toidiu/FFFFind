package com.toidiu.ffffound.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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


public class ListActivity extends ActionBarActivity {
    private static final String TAG = "Main Activity";
    public static final String LIST_TITLE = "com.toidiu.artist_name";
    private static final String MAIN_LIST = "com.toidiu.main_list";
    private static String MAIN_URL;

    private FragmentManager mFragManager;
    private Fragment mMainFragment;
    private String url;
    private Bundle bundle;
    private Menu mMenu;

    private static boolean IS_MAIN_LIST = true;
    private static SaveLoadHandler<ArrayList<FFFFItem>> slh;
    private final static String SAVE_FILE = "fav.json";
    public File FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            //orientation change so just set view
            setContentView(R.layout.activity_fragment);
        } else {
            setContentView(R.layout.activity_fragment);

            //Save/Load handler
            FILE = new File(this.getFilesDir() + File.separator + SAVE_FILE);
            Type type = new TypeToken<ArrayList<FFFFItem>>() {
            }.getType();
            slh = new SaveLoadHandler(type, FILE);
            final ArrayList<FFFFItem> list = slh.loadData();
            FFFavData.getInstance().setFav(list);

            //pass Everyone mURL
            MAIN_URL = FFListFragment.EVERYONE_URL;
            bundle = new Bundle();
            bundle.putCharSequence(FFListFragment.LIST_URL, MAIN_URL);

            mFragManager = getSupportFragmentManager();
            mMainFragment = mFragManager.findFragmentByTag(MAIN_LIST);
            if (mMainFragment == null) {
                mMainFragment = new FFListFragment();
                mMainFragment.setArguments(bundle);
                mFragManager.beginTransaction()
                        .add(R.id.frag_container, mMainFragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            slh.saveData(FFFavData.getInstance().getFav());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu
        mMenu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        configMenu(true, true, true, false);
//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setHomeButtonEnabled(true);
        return super.onCreateOptionsMenu(mMenu);
    }

    @Override
    public void onBackPressed() {
        if (IS_MAIN_LIST) {
            super.onBackPressed();
        }else {
            configMenu(true, true, true, false);

            IS_MAIN_LIST = true;
            setTitle(getResources().getString(R.string.app_name));
            //pass Everyone mURL
            bundle = new Bundle();
            bundle.putCharSequence(FFListFragment.LIST_URL, MAIN_URL);

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mOffset;
        if (IS_MAIN_LIST && FFListFragment.getURL() != ""){
            MAIN_URL = FFListFragment.getURL();
        }

        IS_MAIN_LIST = false;
        configMenu(true, true, true, false);

        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.explore:
                Toast.makeText(this, "Explore", Toast.LENGTH_SHORT).show();
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
                break;
//            case R.id.randomUser:
//                Toast.makeText(this, "Random User", Toast.LENGTH_SHORT).show();
//                String[] randUserList = FFFavData.getInstance().getUsers();
//                int rand = new Random().nextInt(randUserList.length);
//                String randUser = randUserList[rand];
//
//                url = FFListFragment.USER_URL_BASE + randUser + FFListFragment.USER_URL_END;
//                Log.d(TAG, url);
//                setTitle(randUser);
//
//                bundle = new Bundle();
//                bundle.putCharSequence(FFListFragment.LIST_URL, url);
//
//                mFragManager = getSupportFragmentManager();
//                mMainFragment = mFragManager.findFragmentByTag(MAIN_LIST);
//                if (mMainFragment == null) {
//                    mMainFragment = new FFListFragment();
//                }
//                mMainFragment.setArguments(bundle);
//                mFragManager.beginTransaction()
//                        .add(R.id.frag_container, mMainFragment)
//                        .commit();
//                break;
            case R.id.favorite:
                configMenu(true, true, false, true);

                Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
                setTitle("Favorites");
                //set mURL
                bundle = new Bundle();
                bundle.putCharSequence(FFListFragment.LIST_URL, "");
                bundle.putBoolean(FFListFragment.SHOW_FAV, true);

                mFragManager = getSupportFragmentManager();
                mMainFragment = mFragManager.findFragmentByTag(MAIN_LIST);
                if (mMainFragment == null) {
                    mMainFragment = new FFListFragment();
                }
                mMainFragment.setArguments(bundle);
                mFragManager.beginTransaction()
                        .add(R.id.frag_container, mMainFragment)
                        .commit();
                break;
            case R.id.clear_fav:
                Toast.makeText(this, "Favorites Cleared!", Toast.LENGTH_SHORT).show();



                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // set dialog message
                builder.setTitle("Clear your Favorites?")
                    .setNegativeButton("Naa jk", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //nothing
                        }
                    })
                    .setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FFFavData.getInstance().clearFav();
                        }
                    });
                // create alert dialog
                AlertDialog d = builder.create();
                d.show();
                d.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.RED);

                configMenu(true, true, false, true);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        configMenu(true, true, true, false);
        if (IS_MAIN_LIST && FFListFragment.getURL() != ""){
            MAIN_URL = FFListFragment.getURL();
        }

        if (resultCode == FFDetailFragment.DETAIL_USER_LIST) {
            Toast.makeText(this, "Detail User", Toast.LENGTH_SHORT).show();
            String title = data.getStringExtra(ListActivity.LIST_TITLE);
            url = data.getStringExtra(FFListFragment.LIST_URL);
            setTitle(title);

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
                    .commitAllowingStateLoss();
            IS_MAIN_LIST = false;

        }else if(resultCode == FFDetailFragment.DETAIL_FAV_LIST){
            configMenu(true, true, false, true);

            Toast.makeText(this, "Detail Favorites", Toast.LENGTH_SHORT).show();
            setTitle("Favorites");
            //set mURL
            bundle = new Bundle();
            bundle.putCharSequence(FFListFragment.LIST_URL, "");
            bundle.putBoolean(FFListFragment.SHOW_FAV, true);

            mFragManager = getSupportFragmentManager();
            mMainFragment = mFragManager.findFragmentByTag(MAIN_LIST);
            if (mMainFragment == null) {
                mMainFragment = new FFListFragment();
            }
            mMainFragment.setArguments(bundle);
            mFragManager.beginTransaction()
                    .add(R.id.frag_container, mMainFragment)
                    .commitAllowingStateLoss();
            IS_MAIN_LIST = false;

        }else if(resultCode == FFDetailFragment.DETAIL_RAND_USER_LIST){
            Toast.makeText(this, "Detail Rand User", Toast.LENGTH_SHORT).show();
            String[] randUserList = FFFavData.getInstance().getUsers();
            int rand = new Random().nextInt(randUserList.length);
            String randUser = randUserList[rand];

            url = FFListFragment.USER_URL_BASE + randUser + FFListFragment.USER_URL_END;
            Log.d(TAG, url);
            setTitle(randUser);

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
                    .commitAllowingStateLoss();
            IS_MAIN_LIST = false;

        }else if(resultCode == FFDetailFragment.DETAIL_BACK){
            super.onActivityResult(requestCode, resultCode, data);
        }else {
            IS_MAIN_LIST = true;
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void configMenu(boolean randOff, boolean randUser, boolean fav, boolean clearFav){
        if (mMenu != null) {
            mMenu.findItem(R.id.explore).setVisible(randOff);
//            mMenu.findItem(R.id.randomUser).setVisible(randUser);
            mMenu.findItem(R.id.favorite).setVisible(fav);
            mMenu.findItem(R.id.clear_fav).setVisible(clearFav);
        }
    }
}