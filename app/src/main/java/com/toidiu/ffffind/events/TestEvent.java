package com.toidiu.ffffind.events;
import retrofit.client.Response;

/**
 * Created by toidiu on 11/29/14.
 */
public class TestEvent
{
    public Response response;

    public TestEvent(Response response)
    {
        this.response = response;
    }
}
