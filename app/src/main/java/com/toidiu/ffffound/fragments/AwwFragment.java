package aww.toidiu.com.redditgallery.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import aww.toidiu.com.redditgallery.R;
import aww.toidiu.com.redditgallery.model.GalleryItem;
import aww.toidiu.com.redditgallery.utils.HttpRequest;
import aww.toidiu.com.redditgallery.utils.ThumbDownloader;

public class AwwFragment extends Fragment{
    private static final String TAG = "AwwFragment";
    GridView mGridView;
    ArrayList<GalleryItem> mItems;
    ThumbDownloader<ImageView> mThumbDownloader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchItemsTask().execute();

        mThumbDownloader = new ThumbDownloader<ImageView>(new Handler());
        mThumbDownloader.setListener(new ThumbDownloader.Listener<ImageView>() {
            @Override
            public void onThumbDownloaded(ImageView imageView, Bitmap bitmap) {
                if (isVisible()) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        });
        mThumbDownloader.start();
        mThumbDownloader.getLooper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_aww_gallery, container, false);
        mGridView = (GridView) v.findViewById(R.id.aww_grid);

        setUpAdapter();

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mThumbDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbDownloader.quit();
    }

    void setUpAdapter(){
        if(getActivity() == null || mGridView == null) return;

        if (mItems != null){
            mGridView.setAdapter(new GalleryItemAdapter(mItems));
        }else{
            mGridView.setAdapter(null);
        }
    }

//--------------------------------------PRIVATE CLASS---------------
    private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<GalleryItem>>{

        @Override
        protected ArrayList<GalleryItem> doInBackground(Void... params) {
            try{
                String result = new HttpRequest().getUrl("http://www.reddit.com/r/aww.json");

                ArrayList<GalleryItem> r = new ArrayList<GalleryItem>();

//                r.add(new GalleryItem(getActivity().getString(R.string.url1)));
                r.add(new GalleryItem(getActivity().getString(R.string.url2)));
                r.add(new GalleryItem(getActivity().getString(R.string.url3)));
                r.add(new GalleryItem(getActivity().getString(R.string.url4)));
                r.add(new GalleryItem(getActivity().getString(R.string.url5)));
                r.add(new GalleryItem(getActivity().getString(R.string.url6)));
                r.add(new GalleryItem(getActivity().getString(R.string.url7)));
                r.add(new GalleryItem(getActivity().getString(R.string.url8)));
r.add(new GalleryItem(getActivity().getString(R.string.url2)));
                r.add(new GalleryItem(getActivity().getString(R.string.url3)));
                r.add(new GalleryItem(getActivity().getString(R.string.url4)));
                r.add(new GalleryItem(getActivity().getString(R.string.url5)));
                r.add(new GalleryItem(getActivity().getString(R.string.url6)));
                r.add(new GalleryItem(getActivity().getString(R.string.url7)));
                r.add(new GalleryItem(getActivity().getString(R.string.url8)));
r.add(new GalleryItem(getActivity().getString(R.string.url2)));
                r.add(new GalleryItem(getActivity().getString(R.string.url3)));
                r.add(new GalleryItem(getActivity().getString(R.string.url4)));
                r.add(new GalleryItem(getActivity().getString(R.string.url5)));
                r.add(new GalleryItem(getActivity().getString(R.string.url6)));
                r.add(new GalleryItem(getActivity().getString(R.string.url7)));
                r.add(new GalleryItem(getActivity().getString(R.string.url8)));
r.add(new GalleryItem(getActivity().getString(R.string.url2)));
                r.add(new GalleryItem(getActivity().getString(R.string.url3)));
                r.add(new GalleryItem(getActivity().getString(R.string.url4)));
                r.add(new GalleryItem(getActivity().getString(R.string.url5)));
                r.add(new GalleryItem(getActivity().getString(R.string.url6)));
                r.add(new GalleryItem(getActivity().getString(R.string.url7)));
                r.add(new GalleryItem(getActivity().getString(R.string.url8)));

//                for (int i = 0; i < 20; i++) {
//                    r.add(new GalleryItem(getActivity().getString(R.string.url8)));
//                }
//                Log.d(TAG, s[0].toString());
                return r;

            } catch (IOException e) {
                Log.d(TAG, "Failed");
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<GalleryItem> galleryItems) {
            mItems = galleryItems;
            setUpAdapter();
        }
    }

//--------------------------------------PRIVATE CLASS---------------
    private class GalleryItemAdapter extends ArrayAdapter<GalleryItem> {
        public GalleryItemAdapter(ArrayList<GalleryItem> itemArr) {
            super(getActivity(), 0, itemArr);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.gallery_item, parent, false);

                ImageView imgView = (ImageView) convertView.findViewById(R.id.gallery_item_imgView);
//                imageView.setImageResource(R.drawable.ic_launcher);
                imgView.setBackgroundColor(generateRandomColor( Color.LTGRAY ));

                GalleryItem item = getItem(position);
                mThumbDownloader.queueThumb(imgView, item.getUrl());
            }
            return convertView;
        }

        public int generateRandomColor(int mix) {
            Random random = new Random();
            int red = random.nextInt(256);
            int green = random.nextInt(256);
            int blue = random.nextInt(256);

            // mix the color
            if ( mix != 0 ) {
                red = (red + Color.red(mix)) / 2;
                green = (green + Color.green(mix)) / 2;
                blue = (blue + Color.blue(mix)) / 2;
            }

            int color = Color.argb(255, red, green, blue);
            return color;
        }


    }
}
