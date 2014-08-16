package com.toidiu.ffffound.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.google.gson.reflect.TypeToken;
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.fragments.FFListFragment;
import com.toidiu.ffffound.model.FFFFItem;
import com.toidiu.ffffound.model.FFFavData;
import com.toidiu.ffffound.utils.SaveLoadHandler;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private FragmentManager mFragManager;
    private Fragment mFragment;

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

}
