package com.toidiu.ffffound.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;
import com.toidiu.ffffound.utils.Stuff;

public class FFDetailFragment extends Fragment {
    public static final String ITEM_IDX = "com.toidiu.itemIdx";
    private static final String TAG = "FFDetailFragment";
    FFFFItem item;
    ImageView imgView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        getActivity().setTitle("Detail View");

        Intent intent = getActivity().getIntent();
        int idx = intent.getIntExtra(ITEM_IDX, -1);
        item = FFData.getInstance().getItems(idx);


//        mGalleryAdapter = new FFGalleryAdapter( getActivity(), this);
//        new FetchItemsTask(URLBASE).execute();
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
        Log.d(TAG, item.getBigUrl());
        Picasso.with(getActivity())
            .load(item.getBigUrl())
            .into(imgView, new Callback() {


        @Override
        public void onSuccess() {
            Log.d(TAG, "BIG");
        }

        @Override
        public void onError() {
            Picasso.with(getActivity())
                    .load(item.getMedUrl())
                    .into(imgView);
            Log.d(TAG, "MID");
        }
    });


        return v;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
////        mSGView = (StaggeredGridView) v.findViewById(R.id.grid_view);
////
////        setUpAdapter();
//        return v;
//    }


























}
