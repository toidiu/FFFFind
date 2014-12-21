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


    public static FavData getInstance()
    {
        if(FAV_DATA == null)
        {
            FAV_DATA = new FavData();
        }
        return FAV_DATA;
    }

    FavData()
    {
        mFFFavMap = new HashMap<String, FFItem>();
        mUsersSet = new HashSet<String>();
    }

    //-----------------------------------User Hash
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

    //------------------------------------Favorite List
    public ArrayList<FFItem> getFavs()
    {
        ArrayList<FFItem> mFFFavList = new ArrayList<FFItem>();
        for(FFItem item : mFFFavMap.values())
        {
            mFFFavList.add(item);
        }
        return mFFFavList;
    }

    public FFItem getFavs(String midUrl)
    {
        return mFFFavMap.get(midUrl);
    }

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

    public void addFav(FFItem item)
    {
        mFFFavMap.put(item.getMedUrl(), item);
    }

    public void removeFav(FFItem item)
    {
        mFFFavMap.remove(item.getMedUrl());
    }

    public void updateFav(FFItem item)
    {
        if(mFFFavMap.containsKey(item.getMedUrl()))
        {
            mFFFavMap.put(item.getMedUrl(), item);
        }
    }


    public boolean hasFav(FFItem item)
    {
        return mFFFavMap.containsKey(item.getMedUrl());
    }

    public boolean toggleFav(FFItem item)
    {
        if(hasFav(item))
        {
            removeFav(item);
            return false;
        }
        else
        {
            addFav(item);
            return true;
        }
    }
}
