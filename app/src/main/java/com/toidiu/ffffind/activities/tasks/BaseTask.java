package com.toidiu.ffffind.activities.tasks;
/**
 * Created by toidiu on 12/1/14.
 */
public abstract class BaseTask
{
    protected BaseTask()
    {
        new Thread(new Runnable()
        {
            public void run()
            {
                run();
            }
        }).start();

    }

    public abstract void runBackground();

}
