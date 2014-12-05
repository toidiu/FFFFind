package com.toidiu.ffffind.activities.tasks;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FItemBuilder;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.mime.TypedInput;

/**
 * Created by toidiu on 11/29/14.
 */
public class BuildFItemsEvent
{
    public ArrayList<FFItem> items;

    public BuildFItemsEvent(Response response)
    {
        TypedInput body = response.getBody();
        InputStream in = null;
        try
        {
            in = body.in();
            String read = IOUtils.toString(in);
            FItemBuilder fItemBuilder = new FItemBuilder(read);
            items = fItemBuilder.parse();

        }
        catch(IOException e)
        {
        }

    }

    //    String result = new HttpRequest().getUrl(mUrl);
    //    fItemBuilder = new FItemBuilder(result);
    //    ArrayList<FFItem> ffGalleryItems = fItemBuilder.parse();
    //    return ffGalleryItems;


}

