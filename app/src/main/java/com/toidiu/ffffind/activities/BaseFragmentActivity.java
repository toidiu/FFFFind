package com.toidiu.ffffind.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import com.toidiu.ffffind.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by toidiu on 10/26/14.
 */
public abstract class BaseFragmentActivity extends FragmentActivity
{
    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Constants
    public static final String LIST_FRAGMENT_TAG = "LIST_FRAGMENT_TAG";

    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Views
    @InjectView(R.id.progress_bar)
    public RelativeLayout progressBar;

    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Field
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);
        ButterKnife.inject(this);

        switchFragment(createFragment());

    }

    public void switchFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.frag_container, fragment, LIST_FRAGMENT_TAG).commit();
    }

    protected abstract Fragment createFragment();

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        // Inflate the menu
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        configMenu(true, false, false, false);
        return super.onCreateOptionsMenu(this.menu);
    }

    protected void configMenu(boolean explore, boolean randUser, boolean fav, boolean clearFav)
    {
        if(menu != null)
        {
            menu.findItem(R.id.explore).setVisible(explore);
            //                    menu.findItem(R.id.randomUser).setVisible(randUser);
            menu.findItem(R.id.favorite).setVisible(fav);
            menu.findItem(R.id.clear_fav).setVisible(clearFav);
        }
    }

    public void enableProgressBar()
    {
        progressBar.setVisibility(View.VISIBLE);
        if(menu != null)
        {
            menu.setGroupEnabled(R.id.main_menu_group, false);
        }
    }

    public void disableProgressBar()
    {
        progressBar.setVisibility(View.GONE);
        if(menu != null)
        {
            menu.setGroupEnabled(R.id.main_menu_group, true);
        }
    }

}
