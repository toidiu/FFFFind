package com.toidiu.ffffind.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class FavData
{
    private static FavData FAV_DATA;

    public  HashMap<String, FFItem> mFFFavMap;
    private HashSet<String>         mUsersSet;


    FavData()
    {
        mFFFavMap = new HashMap<String, FFItem>();
        mUsersSet = new HashSet<String>();
    }

    //----------Instance
    public static FavData getInstance()
    {
        if(FAV_DATA == null)
        {
            FAV_DATA = new FavData();
        }
        return FAV_DATA;
    }

    public ArrayList<FFItem> getFav()
    {
        ArrayList<FFItem> mFFFavList = new ArrayList<FFItem>();
        for(FFItem item : mFFFavMap.values())
        {
            mFFFavList.add(item);
        }
        return mFFFavList;
    }

    //------------------Favorite List
    public void setFav(ArrayList<FFItem> list)
    {
        if(list != null)
        {
            for(int i = 0; i < list.size(); i++)
            {
                FFItem item = list.get(i);
                mFFFavMap.put(item.getMedUrl(), item);
            }
        }
    }

    public FFItem getFav(String midUrl)
    {
        return mFFFavMap.get(midUrl);
    }

    public void updateFav(FFItem item)
    {
        if(mFFFavMap.containsKey(item.getMedUrl()))
        {
            mFFFavMap.put(item.getMedUrl(), item);
        }
    }

    public void addFav(FFItem item)
    {
        item.setFavorite(true);
        mFFFavMap.put(item.getMedUrl(), item);
    }

    public void removeFav(FFItem item)
    {
        mFFFavMap.remove(item.getMedUrl());
    }

    public void clearFav()
    {
        mFFFavMap.clear();
    }


    //-----------------User Hash
    public void addUser(String user)
    {
        mUsersSet.add(user);
    }

    public String[] getUsers()
    {

        String[] userList = new String[mUsersSet.size()];
        Iterator<String> iterator = mUsersSet.iterator();
        for(int i = 0; i < mUsersSet.size(); i++)
        {
            userList[i] = iterator.next();
        }
        return userList;

    }

}
