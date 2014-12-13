package com.toidiu.ffffind.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.LayoutInflater;
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


public class ListAdapter extends ArrayAdapter<FFItem>
{
    //~=~=~=~=~=~=~=~=~=~=~=~=~=~=View
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public ListAdapter(Context ctx)
    {
        super(ctx, 0, FFData.getInstance().getItems());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater li = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.gallery_item, parent, false);
        }

        DynamicHeightImageView imgView = (DynamicHeightImageView) convertView
                .findViewById(R.id.imgView);
        imgView.setHeightRatio(getPositionRatio(position));
        imgView.setBackgroundColor(generateRandomColor(Color.LTGRAY));
        Picasso.with(getContext()).load(getItem(position).getMedUrl()).into(imgView);

        return convertView;
    }


    private double getPositionRatio(final int position)
    {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        if(ratio == 0)
        {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio()
    {
        return (new Random().nextDouble() / 1.5) + 1.0; // height will be 1.0 - 1.66
    }

}
