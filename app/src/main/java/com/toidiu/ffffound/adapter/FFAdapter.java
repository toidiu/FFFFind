//package com.toidiu.ffffound.adapter;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//
//import com.toidiu.ffffound.R;
//import com.toidiu.ffffound.model.FFFFItem;
//import com.toidiu.ffffound.utils.ThumbDownloader;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//
////--------------------------------------PRIVATE CLASS---------------
//public class GalleryItemAdapter extends ArrayAdapter<FFFFItem> {
//    public GalleryItemAdapter(Context Ctx, ArrayList<FFFFItem> itemArr, ThumbDownloader thumb) {
//        super(getActivity(), 0, itemArr);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = getActivity().getLayoutInflater()
//                    .inflate(R.layout.gallery_item, parent, false);
//
//            ImageView imgView = (ImageView) convertView.findViewById(R.id.gallery_item_imgView);
////                imageView.setImageResource(R.drawable.ic_launcher);
//            imgView.setBackgroundColor(generateRandomColor( Color.LTGRAY ));
//
//            FFFFItem item = getItem(position);
//            mThumbDownloader.queueThumb(imgView, item.getUrl());
//        }
//        return convertView;
//    }
//
//    public int generateRandomColor(int mix) {
//        Random random = new Random();
//        int red = random.nextInt(256);
//        int green = random.nextInt(256);
//        int blue = random.nextInt(256);
//
//        // mix the color
//        if ( mix != 0 ) {
//            red = (red + Color.red(mix)) / 2;
//            green = (green + Color.green(mix)) / 2;
//            blue = (blue + Color.blue(mix)) / 2;
//        }
//
//        int color = Color.argb(255, red, green, blue);
//        return color;
//    }
//
//
//}
