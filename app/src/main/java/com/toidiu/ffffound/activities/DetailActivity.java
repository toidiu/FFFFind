package com.toidiu.ffffound.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;
import com.toidiu.ffffound.utils.FFFeedParser;
import com.toidiu.ffffound.utils.FFHttpRequest;
import com.toidiu.ffffound.utils.Stuff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DetailActivity extends Activity implements View.OnLongClickListener{
    public static final String ITEM_IDX = "com.toidiu.itemIdx";
    private static final String TAG = "DetailView";
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_test);
        setTitle("Detail View");
        Intent intent = getIntent();
        int idx = intent.getIntExtra(ITEM_IDX, -1);
        final FFFFItem item = FFData.getInstance().getItems(idx);

        TextView title = (TextView) findViewById(R.id.pic_title);
        title.setText(item.getTitle());

        TextView artist = (TextView) findViewById(R.id.artist_name);
        artist.setText(item.getArtist());

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.detail_back);
        rl.setBackgroundColor(Stuff.generateRandomColor(Color.WHITE));

        imgView = (ImageView) findViewById(R.id.detail_img);
        Log.d(TAG, item.getBigUrl());
        Picasso.with(this)
                .load(item.getBigUrl())
                .into(imgView, new Callback() {


                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "BIG");
                    }
                    @Override
                    public void onError() {
                        Picasso.with(getApplicationContext())
                                .load(item.getMedUrl())
                                .into(imgView);
                        Log.d(TAG, "MID");
                    }
                });

    }

    @Override
    public boolean onLongClick(View view) {
        return false;


//        Picasso.with(this)
//                .load(currentUrl)
//                .into(saveFileTarget);

//        saveFileTarget = new Target() {
//            @Override
//            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + FILEPATH);
//                        try {
//                            file.createNewFile();
//                            FileOutputStream ostream = new FileOutputStream(file);
//                            bitmap.compress(CompressFormat.JPEG, 75, ostream);
//                            ostream.close();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//            }
//        }
    }


}
