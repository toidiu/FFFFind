package com.toidiu.ffffind.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;

import com.toidiu.ffffind.R;


/**
 * Created by toidiu on 10/26/14.
 */
public abstract class GenralFragmentActivity extends FragmentActivity {

    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.frag_container, createFragment())
                .commit();
    }


    protected abstract Fragment createFragment();


    public void switchFragment(Fragment fragment){
        FragmentManager mFragManager = getSupportFragmentManager();

        mFragManager.beginTransaction()
                .add(R.id.frag_container, fragment)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu
        mMenu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        configMenu(true, false, false, false);
        return super.onCreateOptionsMenu(mMenu);
    }

    protected void configMenu(boolean explore, boolean randUser, boolean fav, boolean clearFav){
        if (mMenu != null) {
            mMenu.findItem(R.id.explore).setVisible(explore);
//            mMenu.findItem(R.id.randomUser).setVisible(randUser);
            mMenu.findItem(R.id.favorite).setVisible(fav);
            mMenu.findItem(R.id.clear_fav).setVisible(clearFav);
        }
    }
}
