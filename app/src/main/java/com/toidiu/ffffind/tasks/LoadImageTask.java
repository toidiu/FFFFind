package com.toidiu.ffffind.tasks;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.greenrobot.event.EventBus;

/**
 * Created by toidiu on 12/13/14.
 */
public class LoadImageTask extends Task
{
    public final  String  url;
    private final Context context;
    public Bitmap bitmap;

    public LoadImageTask(Context context, String medUrl)
    {
        this.context = context;
        this.url = medUrl;
    }

    @Override
    protected void runTask()
    {
        //
        try
        {
            bitmap = Picasso.with(context).load(url).get();
        }
        catch(IOException e)
        {
            bitmap = null;
            Crashlytics.logException(e);
        }

    }

    @Override
    protected void onComplete()
    {
        EventBus.getDefault().post(this);
    }
}
