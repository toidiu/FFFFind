package com.toidiu.ffffound.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;
import com.toidiu.ffffound.utils.FFFeedParser;
import com.toidiu.ffffound.utils.FFHttpRequest;
import com.toidiu.ffffound.utils.ThumbDownloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class FFFragment extends Fragment{
    private static final String TAG = "FFFragment";
    GridView mGridView;

    ThumbDownloader<ImageView> mThumbDownloader;
    String URLBASE = "http://ffffound.com/feed";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute();

        mThumbDownloader = new ThumbDownloader<ImageView>(new Handler());
        mThumbDownloader.setListener(new ThumbDownloader.Listener<ImageView>() {
            @Override
            public void onThumbDownloaded(ImageView imageView, Bitmap bitmap) {
                if (isVisible()) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        });
        mThumbDownloader.start();
        mThumbDownloader.getLooper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGridView = (GridView) v.findViewById(R.id.grid_view);

        setUpAdapter();

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbDownloader.quit();
    }

    void setUpAdapter(){
        if(getActivity() == null || mGridView == null) return;
        ArrayList<FFFFItem> items = FFData.getInstance().getItems();

        if (items != null){
            mGridView.setAdapter(new GalleryItemAdapter(items));
        }else{
            mGridView.setAdapter(null);
        }
    }

//--------------------------------------PRIVATE CLASS---------------
    private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<FFFFItem>>{

        @Override
        protected ArrayList<FFFFItem> doInBackground(Void... params) {
            try{
                String result = new FFHttpRequest().getUrl(URLBASE);

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
            FFData.getInstance().setItems(galleryItems);
            setUpAdapter();
        }
    }

//--------------------------------------PRIVATE CLASS---------------
    private class GalleryItemAdapter extends ArrayAdapter<FFFFItem> {
        public GalleryItemAdapter(ArrayList<FFFFItem> itemArr) {
            super(getActivity(), 0, itemArr);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.gallery_item, parent, false);
            }

            ImageView imgView = (ImageView) convertView.findViewById(R.id.gallery_item_imgView);
            FFFFItem item = getItem(position);

            imgView.setBackgroundColor(generateRandomColor( Color.LTGRAY ));
            String url = item.getBigUrl();
            if (url == ""){url = item.getSmallUrl(); }

            Picasso.with(getActivity()).load(
                    url
            ).into(imgView);
//            mThumbDownloader.queueThumb(imgView, item.getUrl());

            return convertView;
        }

        public int generateRandomColor(int mix) {
            Random random = new Random();
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);

            // mix the color
            if ( mix != 0 ) {
                red = (red + Color.red(mix)) / 2;
                green = (green + Color.green(mix)) / 2;
                blue = (blue + Color.blue(mix)) / 2;
            }

            int color = Color.argb(255, red, green, blue);
            return color;
        }


    }
}
