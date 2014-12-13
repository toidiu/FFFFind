package com.toidiu.ffffind.tasks;
import de.greenrobot.event.EventBus;

/**
 * Created by toidiu on 12/13/14.
 */
public class DownloadImageTask extends Task
{
    @Override
    protected void runTask()
    {

    }

    @Override
    protected void onComplete()
    {
        EventBus.getDefault().post(this);
    }
}
