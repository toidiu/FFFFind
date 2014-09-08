package com.toidiu.ffffind.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.toidiu.ffffind.R;

public class MainGridActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_grid);

        setRecentListeners();
        setExploreListeners();
        setFavoriteListeners();
    }

    private void setFavoriteListeners() {
        Button btn = (Button) findViewById(R.id.splash_grid_fav);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void setExploreListeners() {
        Button btn = (Button) findViewById(R.id.splash_grid_explore);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setRecentListeners() {
        Button btn = (Button) findViewById(R.id.splash_grid_recent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
