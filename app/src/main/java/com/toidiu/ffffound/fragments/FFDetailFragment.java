package com.toidiu.ffffound.fragments;


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
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.activities.FFListActivity;
import com.toidiu.ffffound.model.FFFFItem;
import com.toidiu.ffffound.model.FFFavData;
import com.toidiu.ffffound.utils.Stuff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Calendar;

public class FFDetailFragment extends Fragment{
    public static final String ITEM_EXTRA = "com.toidiu.itemExtra";
    public static final int DETAIL_REQUEST = 0;
    public static final int DETAIL_TAB = 1;
    public static final int DETAIL_NEW_LIST = 2;
    public static final int DETAIL_FAV_LIST = 3;

    private static final String TAG = "FFDetailFragment";
    private FFFFItem item;
    private ImageView imgView;
    private ImageView downView;
    private ImageView starView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Intent intent = getActivity().getIntent();
        item = intent.getParcelableExtra(ITEM_EXTRA);
        FFFFItem getFav = FFFavData.getInstance().getFav(item.getMedUrl());
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
            starView.setImageDrawable(getResources().getDrawable(R.drawable.on));
        }else {
            starView.setImageDrawable(getResources().getDrawable(R.drawable.off));
        }

        downView = (ImageView) v.findViewById(R.id.download);
        if (item.isDownload()) {
            downView.setImageDrawable(getResources().getDrawable(R.drawable.down_done));
        }else {
            downView.setImageDrawable(getResources().getDrawable(R.drawable.down_start));
        }

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
                String url = FFListFragment.SPARE_URL_BASE + item.getArtist() + FFListFragment.SPARE_URL_END;
                Log.d(TAG, url);

                Intent intent = new Intent(getActivity(), FFListActivity.class);
                intent.putExtra(FFListFragment.LIST_URL, url);
                intent.putExtra(FFListActivity.LIST_TITLE, item.getArtist());
                returnResult(DETAIL_NEW_LIST, intent);
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
                FFFavData.getInstance().updateFav(item);
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
            star.setImageDrawable(getResources().getDrawable(R.drawable.on));
            FFFavData.getInstance().addFav(item);
        }else {
            star.setImageDrawable(getResources().getDrawable(R.drawable.off));
            FFFavData.getInstance().removeFav(item);
        }
    }

    private void setDownImg(boolean setFlag) {
        ImageView down = (ImageView) getActivity().findViewById(R.id.download);
        if (setFlag) {
            down.setImageDrawable(getResources().getDrawable(R.drawable.down_done));
        }else {
            down.setImageDrawable(getResources().getDrawable(R.drawable.down_start));
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

//                if (bitmap==null){
//                    bitmap = Picasso.with(getActivity())
//                            .load(urls[1])
//                            .get();
//                }

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

                    setDownImg(true);

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
