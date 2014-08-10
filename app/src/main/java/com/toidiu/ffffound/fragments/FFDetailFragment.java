package com.toidiu.ffffound.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.adapter.FFGalleryAdapter;

public class FFDetailFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

//        mGalleryAdapter = new FFGalleryAdapter( getActivity(), this);
//        new FetchItemsTask(URLBASE).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
//        mSGView = (StaggeredGridView) v.findViewById(R.id.grid_view);
//
//        setUpAdapter();
        return v;
    }

}
