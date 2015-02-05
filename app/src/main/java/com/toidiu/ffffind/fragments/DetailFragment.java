package com.toidiu.ffffind.fragments;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.toidiu.ffffind.R;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FavData;
import com.toidiu.ffffind.tasks.DownloadImageTask;
import com.toidiu.ffffind.utils.Stuff;

public class DetailFragment extends Fragment
{

    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=Constants
    public static final String FITEM_EXTRA = "com.toidiu.itemExtra";

    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=View
    private FFItem    item;
    private ImageView detailImage;
    private ImageView heartView;

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

        item = getArguments().getParcelable(FITEM_EXTRA);
        FFItem getFav = FavData.getInstance().getFavs(item.getMedUrl());
        item = getFav != null
                ? getFav
                : item;

        getActivity().setTitle(item.getArtist());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.detail_item, container, false);

        TextView artist = (TextView) view.findViewById(R.id.artist_name);
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.detail_back);
        ImageView download = (ImageView) view.findViewById(R.id.download);
        heartView = (ImageView) view.findViewById(R.id.favorite);
        detailImage = (ImageView) view.findViewById(R.id.detail_img);

        artist.setText(item.getArtist());
        rl.setBackgroundColor(Stuff.generateRandomColor(Color.WHITE));

        setFavStarListener();
        updateHeartView();

        downloadImgListener(detailImage, download);
        Picasso.with(getActivity()).load(item.getMedUrl()).config(Bitmap.Config.RGB_565).into(
                detailImage);

        return view;
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
                new DownloadImageTask(getActivity(), item.getMedUrl(), item.getArtist());
            }
        });

        detailImage.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View view)
            {
                item.setDownload(true);
                setFavStar(true);
                new DownloadImageTask(getActivity(), item.getMedUrl(), item.getArtist());
                return false;
            }
        });
    }

    void setFavStarListener()
    {
        heartView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setFavStar(FavData.getInstance().toggleFav(item));
                updateHeartView();
            }
        });
    }

    private void updateHeartView()
    {
        if(FavData.getInstance().hasFav(item))
        {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.heart_like));
        }
        else
        {
            heartView.setImageDrawable(getResources().getDrawable(R.drawable.heart));
        }
    }

    void setFavStar(boolean isFav)
    {
        ImageView heart = (ImageView) getActivity().findViewById(R.id.favorite);
        if(isFav)
        {
            heart.setImageDrawable(getResources().getDrawable(R.drawable.heart_like));
            FavData.getInstance().addFav(item);
        }
        else
        {
            heart.setImageDrawable(getResources().getDrawable(R.drawable.heart));
            FavData.getInstance().removeFav(item);
        }

    }

}
