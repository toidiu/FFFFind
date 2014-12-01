package com.toidiu.ffffind.activities.tasks;
import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FItemBuilder;
import com.toidiu.ffffind.network.FoundApi;
import com.toidiu.ffffind.network.FoundRestHelper;
import com.toidiu.ffffind.utils.HttpRequest;
import com.toidiu.ffffind.utils.Stuff;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

/**
 * Created by toidiu on 11/29/14.
 */
public class TestTask
{
    public ArrayList<FFItem> items;

    public TestTask(Response response)
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

