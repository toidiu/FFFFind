package com.toidiu.ffffound.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.toidiu.ffffound.R;

public class MainGridActivity extends ActionBarActivity {
    private static final String TAG = "SplashGrid";
    private static final String MAIN_LIST = "com.toidiu.main_list";

    private FragmentManager mFragManager;
    private Fragment mMainFragment;
    private String url;
    private Bundle bundle;
    private int mOffset;
    private Menu mMenu;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_grid);

        setExploreListener();
        setRecentListener();
        setFavListener();
    }

    private void setFavListener() {
        final Context Ctx = this;

        Button favBtn = (Button) findViewById(R.id.grid_fav);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Ctx, FFListActivity.class);
                intent.putExtra(FFListActivity.LIST_TYPE, FFListActivity.FAV);
                startActivity(intent);
            }
        });
    }

    private void setRecentListener() {
        final Context Ctx = this;

        Button recentBtn = (Button) findViewById(R.id.grid_recent);
        recentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Ctx, FFListActivity.class);
                intent.putExtra(FFListActivity.LIST_TYPE, FFListActivity.RECENT);
                startActivity(intent);
            }
        });
    }

    private void setExploreListener() {
        final Context Ctx = this;

        Button exploreBtn = (Button) findViewById(R.id.grid_explore);
        exploreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Ctx, FFListActivity.class);
                intent.putExtra(FFListActivity.LIST_TYPE, FFListActivity.EXPLORE);
                startActivity(intent);
            }
        });
    }



//    @Override
//    public boolean onCreateOptionsMenu(final Menu menu) {
//        // Inflate the menu
//        mMenu = menu;
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        configMenu(true, true, true, false);
////        getActionBar().setHomeButtonEnabled(true);
////        getActionBar().setHomeButtonEnabled(true);
//        return super.onCreateOptionsMenu(mMenu);
//    }
//    private void configMenu(boolean randOff, boolean randUser, boolean fav, boolean clearFav){
//        if (mMenu != null) {
//            mMenu.findItem(R.id.explore).setVisible(randOff);
////            mMenu.findItem(R.id.randomUser).setVisible(randUser);
//            mMenu.findItem(R.id.favorite).setVisible(fav);
//            mMenu.findItem(R.id.clear_fav).setVisible(clearFav);
//        }
//    }

}
