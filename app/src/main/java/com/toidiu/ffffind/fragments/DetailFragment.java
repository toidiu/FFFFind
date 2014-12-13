package com.toidiu.ffffind.fragments;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.toidiu.ffffind.R;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FavData;
import com.toidiu.ffffind.tasks.LoadImageTask;
import com.toidiu.ffffind.utils.Stuff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

public class DetailFragment extends Fragment
{

    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Constants
    public static final String FITEM_EXTRA = "com.toidiu.itemExtra";

    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=View
    private FFItem    item;
    private ImageView detailImage;

    public static DetailFragment newInstance(FFItem item)
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(FITEM_EXTRA, item);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        item = getArguments().getParcelable(FITEM_EXTRA);
        FFItem getFav = FavData.getInstance().getFav(item.getMedUrl());
        item = getFav != null
                ? getFav
                : item;

        getActivity().setTitle(item.getArtist());
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.detail_item, container, false);

        TextView artist = (TextView) v.findViewById(R.id.artist_name);
        artist.setText(item.getArtist());

        RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.detail_back);
        rl.setBackgroundColor(Stuff.generateRandomColor(Color.WHITE));

        ImageView heartView = (ImageView) v.findViewById(R.id.favorite);
        setFavStarListener(heartView);
        if(item.isFavorite())
        {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.heart_like));
        }
        else
        {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.heart));
        }

        detailImage = (ImageView) v.findViewById(R.id.detail_img);
        ImageView download = (ImageView) v.findViewById(R.id.download);
        downloadImgListener(detailImage, download);
        new LoadImageTask(getActivity(), item.getMedUrl());
        return v;
    }

    private void downloadImgListener(ImageView detailImage, ImageView download)
    {
        download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                item.setDownload(true);
                FavData.getInstance().updateFav(item);
                new DownloadImg().execute(item.getBigUrl(), item.getMedUrl());
            }
        });

        detailImage.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                item.setDownload(true);
                setFavStar(true);
                new DownloadImg().execute(item.getBigUrl(), item.getMedUrl());
                return false;
            }
        });

    }

    void setFavStarListener(View v)
    {
        v.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setFavStar(item.toggleFavorite());
            }
        });
    }

    void setFavStar(boolean isFav)
    {
        ImageView star = (ImageView) getActivity().findViewById(R.id.favorite);
        if(isFav)
        {
            star.setImageDrawable(getResources().getDrawable(R.drawable.heart_like));
            FavData.getInstance().addFav(item);
        }
        else
        {
            star.setImageDrawable(getResources().getDrawable(R.drawable.heart));
            FavData.getInstance().removeFav(item);
        }
    }

    //--------------------------------------PRIVATE CLASS---------------
    private class DownloadImg extends AsyncTask<String, Void, Bitmap>
    {
        protected Bitmap doInBackground(String... urls)
        {
            Bitmap bitmap = null;
            try
            {
                bitmap = Picasso.with(getActivity()).load(urls[1]).get();

            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap image)
        {
            if(image != null)
            {

                Calendar c = Calendar.getInstance();
                String m = String.valueOf(c.get(Calendar.MINUTE));
                String s = String.valueOf(c.get(Calendar.SECOND));
                String fileName = item.getArtist() + m + s + ".png";
                File folder = null;
                File output = null;


                FileOutputStream fos = null;
                try
                {
                    folder = new File(Environment.getExternalStorageDirectory(), "FFFFound");
                    if(! folder.exists())
                    {
                        folder.mkdirs();
                    }
                    String file_path = folder.getPath();

                    output = new File(file_path, fileName);

                    // create outstream and write data
                    fos = new FileOutputStream(output);
                    image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();

                }
                catch(FileNotFoundException e)
                { // <10>
                    e.printStackTrace();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }


                Toast.makeText(getActivity(), output.getAbsolutePath(), Toast.LENGTH_LONG).show();

            }
        }

    }

    //----------------EVENTBUS----------------
    @SuppressWarnings("UnusedDeclaration")
    public void onEventMainThread(LoadImageTask event)
    {
        if(event.bitmap == null)
        {
            Toast.makeText(getActivity(), "error loading image", Toast.LENGTH_SHORT).show();
            return;
        }

        if(event.url.equals(item.getMedUrl()))
        {
            detailImage.setImageBitmap(event.bitmap);
        }
    }
}
