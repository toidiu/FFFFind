package com.toidiu.ffffind.network;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;

/**
 * Created by toidiu on 11/29/14.
 */
public class FoundRestHelper
{
    public static RestAdapter makeRequest()
    {
        return getBuilder().build();
    }

    private static RestAdapter.Builder getBuilder()
    {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC).setLog(new AndroidLog("FoundRESTApi"))
                .setEndpoint("http://ffffound.com");
        return builder;
    }

}
