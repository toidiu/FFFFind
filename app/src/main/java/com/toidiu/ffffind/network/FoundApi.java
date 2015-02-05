package com.toidiu.ffffind.network;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by toidiu on 11/29/14.
 */
public interface FoundApi
{
    @GET("/feed")
    Response mainFeed();
    //    public static final String EVERYONE_URL     = "http://ffffound.com/feed";


    @GET("/home/{user}/found/feed")
    Response userFeed(@Path("user") String user);
    //    public static final String USER_URL_BASE    = "http://ffffound.com/home/"; //+ user + SPAREUrlEnd


    @GET("/feed")
    Response offsetFeed(@Query("offset") int offset);
    //    public static final String EXPLORE_URL_BASE = "http://ffffound.com/feed?offset="; //+ number

}
