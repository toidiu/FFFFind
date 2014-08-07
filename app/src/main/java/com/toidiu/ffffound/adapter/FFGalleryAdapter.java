package com.toidiu.ffffound.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.toidiu.ffffound.R;
import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;

import static com.toidiu.ffffound.utils.Stuff.generateRandomColor;


public class FFGalleryAdapter extends ArrayAdapter<FFFFItem> {
    private static final String TAG = "FFGalleryAdapter";

    private Activity mActivity;
    private FFFetcherInterface mListener;

    public FFGalleryAdapter(Context ctx, FFFetcherInterface listener) {
        super( ctx, 0, FFData.getInstance().getItems());

        mListener = listener;
        mActivity = (Activity) getContext();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mActivity.getLayoutInflater()
                    .inflate(R.layout.gallery_item, parent, false);
        }

        ImageView imgView = (ImageView) convertView.findViewById(R.id.imgView);
        FFFFItem item = getItem(position);

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




    //--------------------------------------INTERFACE---------------
    public interface FFFetcherInterface {
        public void FFFFItem();
    }

}
