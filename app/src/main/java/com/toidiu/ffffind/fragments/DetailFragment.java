package com.toidiu.ffffind.fragments;


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
        FFItem getFav = FavData.getInstance().getFav(item.getMedUrl());
        item = getFav != null
                ? getFav
                : item;

        getActivity().setTitle(item.getArtist());
        setHasOptionsMenu(true);
        Log.w("---------dd-d-", "create");
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
        Picasso.with(getActivity()).load(item.getMedUrl()).into(detailImage);

        Log.w("---------dd-d-", "view");
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
        Log.w("---------dd-d-", "download");
    }

    void setFavStarListener(View v)
    {
        v.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Log.w("---------dd-d-", "favStar");
                setFavStar(item.toggleFavorite());
            }
        });
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
        Log.w("---------dd-d-", "setFav");

    }

}
