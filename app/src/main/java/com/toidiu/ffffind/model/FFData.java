package com.toidiu.ffffind.model;

import java.util.ArrayList;

public class FFData
{
    private static FFData mFFData;

    private ArrayList<FFItem> mFFItemsList;
    private Integer           nextOffset;
    private Integer           prevOffset;

    //----------Instance
    public FFData()
    {
        mFFItemsList = new ArrayList<FFItem>();
    }

    public static FFData getInstance()
    {
        if(mFFData == null)
        {
            mFFData = new FFData();
        }
        return mFFData;
    }

    //------------------Item List
    public void addItems(ArrayList<FFItem> ffArray)
    {
        if(mFFItemsList == null)
        {
            mFFItemsList = ffArray;
        }
        else
        {
            mFFItemsList.addAll(ffArray);
        }
    }

    public void addItems(FFItem item)
    {
        mFFItemsList.add(item);
    }

    public void clearList()
    {
        mFFItemsList.clear();
    }

    public FFItem getItems(int idx)
    {
        return mFFItemsList.get(idx);
    }

    public ArrayList<FFItem> getItems()
    {
        return mFFItemsList;
    }

    public Integer getPrevOffset()
    {
        return prevOffset;
    }

    public void setPrevOffset(Integer prevOffset)
    {
        this.prevOffset = prevOffset;
    }

    public Integer getNextOffset()
    {
        return nextOffset;
    }

    public void setNextOffset(Integer nextOffset)
    {
        this.nextOffset = nextOffset;
    }

    public int getSize()
    {
        return mFFItemsList.size();
    }

    public int getIdx(FFItem item)
    {
        return mFFItemsList.indexOf(item);
    }

}





