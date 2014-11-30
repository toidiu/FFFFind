package com.toidiu.ffffind.network;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.Path;

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
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog("FoundRESTApi"))
                .setEndpoint("http://ffffound.com");


        return builder;
    }

}
