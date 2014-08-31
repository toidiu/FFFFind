package com.toidiu.ffffound.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.toidiu.ffffound.R;
import com.toidiu.ffffound.fragments.FFDetailFragment;
import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;

public class FFDetailActivity extends ActionBarActivity{
    private static final String TAG = "DetailView";
    public static final String ITEM_POS = "com.toidiu.detail_item_position";


    private FragmentManager mFragManager;
    private FFDetailFragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);


        Intent intent = getIntent();
        int pos = intent.getIntExtra(ITEM_POS, 0);
        FFFFItem item = FFData.getInstance().getItems(pos);
        mFragManager = getSupportFragmentManager();
        mFragment = FFDetailFragment.newInstance(item);


//        mFragment = (FFDetailFragment) mFragManager.findFragmentById(R.id.frag_container);
//        if(mFragment == null){
//            mFragment = new FFDetailFragment();

            mFragManager.beginTransaction()
                .add(R.id.frag_container, mFragment)
                .commit();
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.clear_fav).setVisible(false);
        menu.findItem(R.id.explore).setVisible(false);
//        menu.findItem(R.id.randomUser).setVisible(true);
        menu.findItem(R.id.favorite).setVisible(true);
//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
//            case R.id.randomUser:
//                Toast.makeText(this, "Random user", Toast.LENGTH_SHORT).show();
//                setResult(FFDetailFragment.DETAIL_RAND_USER_LIST);
//                finish();
//                break;
            case R.id.favorite:
                Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
                setResult(FFDetailFragment.DETAIL_FAV_LIST);
                finish();
                break;
            default:
                break;
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        setResult(FFDetailFragment.DETAIL_BACK);
        finish();
    }
}
