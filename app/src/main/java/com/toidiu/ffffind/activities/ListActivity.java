package com.toidiu.ffffind.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.google.gson.reflect.TypeToken;
import com.toidiu.ffffind.R;
import com.toidiu.ffffind.fragments.ListFragment;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FavData;
import com.toidiu.ffffind.utils.SaveLoadHandler;
import com.toidiu.ffffind.utils.Stuff;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class ListActivity extends BaseFragmentActivity
{
    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Constants
    private final static String SAVE_FILE = "fav.json";

    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Fields
    private static SaveLoadHandler<ArrayList<FFItem>> slh;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null)
        {
            //Save/Load handler
            File saveFile = new File(this.getFilesDir() + File.separator + SAVE_FILE);
            Type type = new TypeToken<ArrayList<FFItem>>()
            {
            }.getType();
            slh = new SaveLoadHandler(type, saveFile);
            final ArrayList<FFItem> list = slh.loadData();
            FavData.getInstance().setFav(list);
        }

    }

    @Override
    protected Fragment createFragment()
    {
        return ListFragment.newInstance(0, false);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if(isFinishing())
        {
            slh.saveData(FavData.getInstance().getFav());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.explore:
                ListFragment.injectNewList(ListActivity.this, Stuff.getRandOffset());
                break;
            default:
                break;
        }

        return true;
    }


}
