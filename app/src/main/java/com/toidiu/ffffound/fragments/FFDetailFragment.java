package com.toidiu.ffffound.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;
import com.toidiu.ffffound.utils.Stuff;

import java.io.IOException;

public class FFDetailFragment extends Fragment {
    public static final String ITEM_IDX = "com.toidiu.itemIdx";
    private static final String TAG = "FFDetailFragment";
    private FFFFItem item;
    private ImageView imgView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        getActivity().setTitle("Detail View");

        Intent intent = getActivity().getIntent();
        int idx = intent.getIntExtra(ITEM_IDX, -1);
        item = FFData.getInstance().getItems(idx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_item, container, false);

        TextView title = (TextView) v.findViewById(R.id.pic_title);
        title.setText(item.getTitle());

        TextView artist = (TextView) v.findViewById(R.id.artist_name);
        artist.setText(item.getArtist());

        RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.detail_back);
        rl.setBackgroundColor(Stuff.generateRandomColor(Color.WHITE));

        imgView = (ImageView) v.findViewById(R.id.detail_img);
        new AttachDetailImg().execute(item.getMedUrl());

        return v;
    }

    //--------------------------------------PRIVATE CLASS---------------
    private class AttachDetailImg extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            try {
                bitmap = Picasso.with(getActivity())
                        .load(urls[0])
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                return bitmap;
            }
        }

        protected void onPostExecute(Bitmap result) {
            Drawable d = getResources().getDrawable(R.drawable.ffffound);
            if (result != null) {
                d = new BitmapDrawable(getResources(), result);
            }
            Picasso.with(getActivity())
                    .load(item.getBigUrl())
                    .placeholder(d)
                    .into(imgView);
        }
    }




}
