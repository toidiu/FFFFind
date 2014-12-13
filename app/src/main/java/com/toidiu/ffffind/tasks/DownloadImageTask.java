package com.toidiu.ffffind.tasks;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

/**
 * Created by toidiu on 12/13/14.
 */
public class DownloadImageTask extends Task
{
    private final String  url;
    private final Context context;
    private final String  artistName;
    private String absolutePath;

    public DownloadImageTask(Context context, String medUrl, String artistName)
    {
        super(context);
        this.context = context;
        this.url = medUrl;
        this.artistName = artistName;
    }

    @Override
    protected void runTask()
    {
        Bitmap bitmap = null;
        try
        {
            bitmap = Picasso.with(context).load(url).get();
        }
        catch(IOException e)
        {
            error = true;
            Crashlytics.logException(e);
        }

        Calendar c = Calendar.getInstance();
        String m = String.valueOf(c.get(Calendar.MINUTE));
        String s = String.valueOf(c.get(Calendar.SECOND));
        String fileName = artistName + m + s + ".png";
        File folder = null;
        File output = null;


        FileOutputStream fos = null;
        try
        {
            folder = new File(Environment.getExternalStorageDirectory(), "FFFFound");
            if(! folder.exists())
            {
                folder.mkdirs();
            }
            String file_path = folder.getPath();

            output = new File(file_path, fileName);

            // create outstream and write data
            fos = new FileOutputStream(output);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

        }
        catch(FileNotFoundException e)
        { // <10>
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        absolutePath = output.getAbsolutePath();

    }

    @Override
    protected void onComplete()
    {
        if(error)
        {
            Toast.makeText(context, "error downloading image", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, absolutePath, Toast.LENGTH_LONG).show();
        }
    }
}
