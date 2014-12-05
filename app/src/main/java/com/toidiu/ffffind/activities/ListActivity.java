package com.toidiu.ffffind.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.toidiu.ffffind.R;
import com.toidiu.ffffind.fragments.ListFragment;
import com.toidiu.ffffind.model.FFData;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FavData;
import com.toidiu.ffffind.utils.SaveLoadHandler;
import com.toidiu.ffffind.utils.Stuff;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class ListActivity extends BaseFragmentActivity
{
    public static final String LIST_TITLE = "com.toidiu.artist_name";
    private final static String SAVE_FILE = "fav.json";
    private static SaveLoadHandler<ArrayList<FFItem>> slh;
    public File FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null)
        {
            //Save/Load handler
            FILE = new File(this.getFilesDir() + File.separator + SAVE_FILE);
            Type type = new TypeToken<ArrayList<FFItem>>()
            {
            }.getType();
            slh = new SaveLoadHandler(type, FILE);
            final ArrayList<FFItem> list = slh.loadData();
            FavData.getInstance().setFav(list);
        }
    }

    @Override
    protected Fragment createFragment()
    {
        return ListFragment.newInstance(Stuff.getRandOffset(), false);
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
//                ListFragment listFragment = ListFragment.newInstance(Stuff.getRandOffset(), false);
//                switchFragment(listFragment);

                break;
            default:
                break;
        }

        return true;
    }


}
