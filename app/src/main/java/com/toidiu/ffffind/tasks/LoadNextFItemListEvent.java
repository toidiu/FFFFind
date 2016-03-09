package com.toidiu.ffffind.tasks;
import android.content.Context;

import com.toidiu.ffffind.model.FFItem;
import com.toidiu.ffffind.model.FItemBuilder;
import com.toidiu.ffffind.network.FoundApi;
import com.toidiu.ffffind.network.FoundRestHelper;

import org.apache.commons.io.IOUtils;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit.client.Response;
import retrofit.mime.TypedInput;

/**
 * Created by toidiu on 11/29/14.
 */
public class LoadNextFItemListEvent extends Task
{
    private final Integer           offset;
    public        ArrayList<FFItem> items;

    public LoadNextFItemListEvent(Context context, final Integer offset)
    {
        super(context);
        this.offset = offset;
    }

    @Override
    protected void runTask()
    {
        Response response = FoundRestHelper.makeRequest().create(FoundApi.class).offsetFeed(offset);
        TypedInput body = response.getBody();

        InputStream in;
        try
        {
            in = body.in();
            String read = IOUtils.toString(in);
            FItemBuilder fItemBuilder = new FItemBuilder(read);
            items = fItemBuilder.parse();
            in.close();

        }
        catch(IOException e)
        {
        }

    }

    @Override
    protected void onComplete()
    {
        EventBus.getDefault().post(this);
    }
}

