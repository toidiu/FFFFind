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

public class DetailView extends Activity implements View.OnLongClickListener{
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
        FFFFItem item = FFData.getInstance().getItems(idx);

        TextView title = (TextView) findViewById(R.id.pic_title);
        title.setText(item.getTitle());

        TextView artist = (TextView) findViewById(R.id.artist_name);
        artist.setText(item.getArtist());

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.detail_back);
        rl.setBackgroundColor(Stuff.generateRandomColor(Color.WHITE));

        imgView = (ImageView) findViewById(R.id.detail_img);
        Log.d(TAG, item.getBigUrl());
        Picasso.with(this).load(item.getBigUrl())
                .error(R.drawable.llllost)
                .into(imgView);

    }

//    @Override
//    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//
//        imgView = (ImageView) parent.findViewById(R.id.detailImg);
//
//        return imgView;
//    }


    public void fetchMore(){

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }


    //--------------------------------------PRIVATE CLASS---------------
    private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<FFFFItem>> {

        private String mUrl;
        private FetchItemsTask(String url) {
            mUrl = url;
        }

        @Override
        protected ArrayList<FFFFItem> doInBackground(Void... params) {
            try{
                String result = new FFHttpRequest().getUrl(mUrl);

                FFFeedParser ffFeedParser = new FFFeedParser(result);
                ArrayList<FFFFItem> ffGalleryItems = ffFeedParser.parse();

                return ffGalleryItems;

            } catch (IOException e) {
                Log.d(TAG, "Failed");
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<FFFFItem> galleryItems) {
            FFData.getInstance().addItems(galleryItems);
//            setUpAdapter();
        }

    }

}
