package com.toidiu.ffffind;

import android.app.Application;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by toidiu on 10/26/14.
 */
public class FFApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        if(!BuildConfig.DEBUG)
        {
            Fabric.with(this, new Crashlytics());
        }

        if(BuildConfig.DEBUG)
        {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                                                                            .detectDiskWrites().detectNetwork()   // or .detectAll() for all detectable problems
                                               .penaltyLog().penaltyFlashScreen().build());
            //            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
            //                    .detectLeakedSqlLiteObjects()
            //                    .detectLeakedClosableObjects()
            //                    .penaltyLog()
            //                    .penaltyDeath()
            //                    .build());
        }

    }
}
