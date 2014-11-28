package com.toidiu.ffffind.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.squareup.picasso.Picasso;
import com.toidiu.ffffind.R;
import com.toidiu.ffffind.model.FFData;
import com.toidiu.ffffind.model.FFItem;

import java.util.Random;

import static com.toidiu.ffffind.utils.Stuff.generateRandomColor;


public class GalleryAdapter extends ArrayAdapter<FFItem> {
    private static final String TAG = "FFGalleryAdapter";

    private Activity mActivity;
    private FFFetcherInterface mListener;
    private final Random mRandom;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    private FFData mData;

    public GalleryAdapter(Context ctx, FFFetcherInterface listener, FFData data) {
        super( ctx, 0, data.getItems());

        mData = data;
        mListener = listener;
        mActivity = (Activity) getContext();
        mRandom = new Random();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater()
                .inflate(R.layout.gallery_item, parent, false);
        }

        FFItem item = getItem(position);

        DynamicHeightImageView imgView = (DynamicHeightImageView) convertView.findViewById(R.id.imgView);
        double positionHeight = getPositionRatio(position);
        imgView.setHeightRatio(positionHeight);
        imgView.setBackgroundColor(generateRandomColor(Color.LTGRAY));


        String url = item.getMedUrl();
//        Log.d(TAG, url);
        Picasso.with(mActivity)
                .load(url)
                .into(imgView);

        if ( position == mData.getSize() - getContext().getResources().getInteger(R.integer.fetch_offset) ){
            mListener.FFFetchItem();
        }

        return convertView;
    }


    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 1.5) + 1.0; // height will be 1.0 - 1.5
        // the width
    }

    //--------------------------------------INTERFACE---------------
    public interface FFFetcherInterface {
        public void FFFetchItem();
    }

}
