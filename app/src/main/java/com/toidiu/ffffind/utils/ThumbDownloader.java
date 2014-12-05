package com.toidiu.ffffind.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThumbDownloader <Token> extends HandlerThread
{

    private static final String TAG              = "ThumbDownloader";
    private static final int    MESSAGE_DOWNLOAD = 0;
    Handler         mHandler;
    Handler         mainHandler;
    Listener<Token> mListener;
    Map<Token, String> requestMap = Collections.synchronizedMap(new HashMap<Token, String>());

    public ThumbDownloader(Handler main)
    {
        super(TAG);
        mainHandler = main;
    }

    @Override
    protected void onLooperPrepared()
    {
        mHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                if(msg.what == MESSAGE_DOWNLOAD)
                {
                    Token token = (Token) msg.obj;
                    handleRequest(token);
                }
            }
        };
    }

    public void clearQueue()
    {
        mHandler.removeMessages(MESSAGE_DOWNLOAD);
        requestMap.clear();
    }

    public void setListener(Listener<Token> listener)
    {
        mListener = listener;
    }

    public void queueThumb(Token token, String url)
    {
        requestMap.put(token, url);

        mHandler.obtainMessage(MESSAGE_DOWNLOAD, token).sendToTarget();
    }

    private void handleRequest(final Token token)
    {
        try
        {
            final String url = requestMap.get(token);
            if(url == null)
            {
                return;
            }

            byte[] bitmapBytes = new HttpRequest().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);

            mainHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    if(requestMap.get(token) != url)
                    {
                        return;
                    }

                    requestMap.remove(token);
                    mListener.onThumbDownloaded(token, bitmap);
                }
            });
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    //---------------------INTERFACE--------------
    public interface Listener <Token>
    {
        void onThumbDownloaded(Token token, Bitmap bitmap);
    }

}
