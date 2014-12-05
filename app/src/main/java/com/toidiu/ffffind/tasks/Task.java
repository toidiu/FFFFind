package com.toidiu.ffffind.tasks;
import android.os.Handler;


import static android.os.Looper.getMainLooper;

/**
 * Created by toidiu on 12/5/14.
 */
public abstract class Task
{
    public Task()
    {

        new Thread(new Runnable()
        {
            public void run()
            {
                runTask();

                new Handler(getMainLooper()).post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        onComplete();
                    }
                });
            }
        }).start();


    }

    protected abstract void runTask();

    protected abstract void onComplete();
}
