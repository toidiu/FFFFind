package com.toidiu.ffffound.utils;


import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class FFFeedParser {
    private static final String TAG = "PARSER";
    String mXmlFeed;
    FFFFItem mffffItem;
    ArrayList<FFFFItem> retVal;

    enum modeEnum {
        TITLE,
        DESCRIP,
        SMALL,
        MEDIUM,
        BIG,
        AUTHOR;
    }
    modeEnum ItemMode;
    Boolean itemCreated = false;

    public FFFeedParser(String xmlFeed) {
        mXmlFeed = xmlFeed;
        retVal = new ArrayList<FFFFItem>();
    }

    public ArrayList<FFFFItem> parse() {

        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput( new StringReader(mXmlFeed) );
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if(eventType == XmlPullParser.START_TAG) {
                    tagModeSelector(xpp);
                }else if(eventType == XmlPullParser.END_TAG) {
                    ItemMode = null;
                    if (xpp.getName().equals("item")){ CloseItem(); }
                }else if(eventType == XmlPullParser.TEXT) {
                    buildItem(xpp.getText());
                }
                eventType = xpp.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retVal;
    }

    private void tagModeSelector(XmlPullParser xpp) {
        String name = xpp.getName();

        if(name.equals("item")){ CreateItem(); }
        else if(name.equals("title")){ ItemMode = modeEnum.TITLE; }
        else if(name.equals("description")){ ItemMode = modeEnum.DESCRIP; }
        else if(name.equals("author")){ ItemMode = modeEnum.AUTHOR; }
        else if(name.equals("thumbnail")){
            ItemMode = modeEnum.SMALL;
            String url = xpp.getAttributeValue(null, "url");
            mffffItem.setSmallUrl(url);
        } else if(name.equals("content")){
            ItemMode = modeEnum.MEDIUM;
            String url = xpp.getAttributeValue(null, "url");
            mffffItem.setMedUrl(url);
        } else if(name.equals("source")){
            ItemMode = modeEnum.BIG;
            String url = xpp.getAttributeValue(null, "url");
            mffffItem.setBigUrl(url);
        }

        //get the link for the next page
        else if ( name.equals("link") &&
                xpp.getAttributeCount() >= 2 ){
            String nextUrl = xpp.getAttributeValue(null, "href");
            FFData.getInstance().setNextUrl(nextUrl);
        }
    }


    private void CreateItem(){
//        Log.d("TAG","Start tag:------------- ");
        itemCreated = true;
        mffffItem = new FFFFItem();
    }
    private void CloseItem(){
        itemCreated = false;
        retVal.add(mffffItem);
    }

    private void buildItem(String text){
        if (itemCreated == false){return;}

        if( ItemMode == modeEnum.DESCRIP ){
            mffffItem.setDescription(text);
        }else if( ItemMode == modeEnum.TITLE ){
            mffffItem.setTitle(text);
        }else if( ItemMode == modeEnum.AUTHOR ){
            mffffItem.setArtist(text);
        }
    }


}
