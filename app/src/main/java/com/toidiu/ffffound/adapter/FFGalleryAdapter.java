package com.toidiu.ffffound.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.squareup.picasso.Picasso;
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;

import java.util.Random;

import static com.toidiu.ffffound.utils.Stuff.generateRandomColor;


public class FFGalleryAdapter extends ArrayAdapter<FFFFItem> {
    private static final String TAG = "FFGalleryAdapter";

    private Activity mActivity;
    private FFFetcherInterface mListener;
    private final Random mRandom;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public FFGalleryAdapter(Context ctx, FFFetcherInterface listener) {
        super( ctx, 0, FFData.getInstance().getItems());

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

        FFFFItem item = getItem(position);


        DynamicHeightImageView imgView = (DynamicHeightImageView) convertView.findViewById(R.id.imgView);
        double positionHeight = getPositionRatio(position);
        imgView.setHeightRatio(positionHeight);
        imgView.setBackgroundColor(generateRandomColor(Color.LTGRAY));


        String url = item.getMedUrl();
        Picasso.with(mActivity)
                .load(url)
//                    .centerCrop()
//                    .resize(120,150)
                .into(imgView);

        if ( position == FFData.getInstance().getSize()-4 ){
            mListener.FFFFItem();
        }

        return convertView;
    }


    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 1.5) + 1.0; // height will be 1.0 - 1.5
        // the width
    }

    //--------------------------------------INTERFACE---------------
    public interface FFFetcherInterface {
        public void FFFFItem();
    }

}
