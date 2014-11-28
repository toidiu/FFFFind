package com.toidiu.ffffind.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.toidiu.ffffind.R;
import com.toidiu.ffffind.activities.ListActivity;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FavData;
import com.toidiu.ffffind.utils.Stuff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class DetailFragment extends Fragment {
    public static final String ITEM_EXTRA = "com.toidiu.itemExtra";
    public static final int DETAIL_TAB = 1;
    public static final int DETAIL_USER_LIST = 2;
    public static final int DETAIL_FAV_LIST = 3;
    public static final int DETAIL_RAND_USER_LIST = 4;
    public static final int DETAIL_BACK = 5;

    private static final String TAG = "FFDetailFragment";
    private FFItem item;
    private ImageView imgView;
    private ImageView downView;
    private ImageView starView;

    public static DetailFragment newInstance(FFItem item) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(ITEM_EXTRA, item);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        item = getArguments().getParcelable(ITEM_EXTRA);


        FFItem getFav = FavData.getInstance().getFav(item.getMedUrl());
        item = getFav != null ? getFav : item;
        getActivity().setTitle(item.getArtist());

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_item, container, false);
        artistClick(v);

        TextView title = (TextView) v.findViewById(R.id.pic_title);
        title.setText(item.getTitle());

        TextView artist = (TextView) v.findViewById(R.id.artist_name);
        artist.setText(item.getArtist());

        RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.detail_back);
        rl.setBackgroundColor(Stuff.generateRandomColor(Color.WHITE));

        starView = (ImageView) v.findViewById(R.id.favorite);
        setFavStarListener(starView);
        if (item.isFavorite()) {
            starView.setImageDrawable(getResources().getDrawable(R.drawable.fav));
        }else {
            starView.setImageDrawable(getResources().getDrawable(R.drawable.fav_add));
        }

        downView = (ImageView) v.findViewById(R.id.download);
        imgView = (ImageView) v.findViewById(R.id.detail_img);
        downloadImgListener(imgView, downView);
        new AttachDetailImg().execute(item.getMedUrl());

        return v;
    }

    private void returnResult(int code, Intent intent){
        getActivity().setResult(code, intent);
        getActivity().finish();
    }

    private void artistClick(View v) {
        TextView artist = (TextView) v.findViewById(R.id.artist_name);

        artist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = ListFragment.USER_URL_BASE + item.getArtist() + ListFragment.USER_URL_END;
                Log.d(TAG, url);

                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra(ListFragment.LIST_URL_EXTRA, url);
                intent.putExtra(ListActivity.LIST_TITLE, item.getArtist());
                returnResult(DETAIL_USER_LIST, intent);
            }
        });
    }

    private void downloadImgListener(View v1, View v2){
        imgView = (ImageView) v1.findViewById(R.id.detail_img);
        downView = (ImageView) v2.findViewById(R.id.download);

        downView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setDownload(true);
                FavData.getInstance().updateFav(item);
                new DownloadImg().execute(item.getBigUrl(), item.getMedUrl());
            }
        });

        imgView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                item.setDownload(true);
                setFavStar(true);
                new DownloadImg().execute(item.getBigUrl(), item.getMedUrl());
                return false;
            }
        });

    }

    void setFavStarListener(View v){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFavStar(item.toggleFavorite());
            }
        });
    }

    void setFavStar(boolean isFav){
        ImageView star = (ImageView) getActivity().findViewById(R.id.favorite);
        if (isFav) {
            star.setImageDrawable(getResources().getDrawable(R.drawable.fav));
            FavData.getInstance().addFav(item);
        }else {
            star.setImageDrawable(getResources().getDrawable(R.drawable.fav_add));
            FavData.getInstance().removeFav(item);
        }
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

    private class DownloadImg extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            try {
                bitmap = Picasso.with(getActivity())
                        .load(urls[1])
                        .get();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {
            if (image != null) {

                Calendar c = Calendar.getInstance();
                String m = String.valueOf(c.get(Calendar.MINUTE));
                String s = String.valueOf(c.get(Calendar.SECOND));
                String fileName = item.getArtist() + m + s + ".png";
                File folder = null;
                File output = null;


                FileOutputStream fos = null;
                try {
                    folder = new File(Environment.getExternalStorageDirectory(), "FFFFound");
                    if( !folder.exists() ) {
                        folder.mkdirs();
                    }
                    String file_path = folder.getPath();

                    output = new File(file_path, fileName);

                    // create outstream and write data
                    fos = new FileOutputStream(output);
                    image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();

                } catch (FileNotFoundException e) { // <10>
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Toast.makeText(getActivity(), output.getAbsolutePath(), Toast.LENGTH_LONG).show();

            }
        }

    }

}
