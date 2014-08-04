package com.toidiu.ffffound.utils;


import android.util.Log;

import com.toidiu.ffffound.model.FFData;
import com.toidiu.ffffound.model.FFFFItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class FFFeedParser {
    String mXmlFeed;
    FFData mFFData;
    FFFFItem mffffItem;

    int ItemMode = 0;
    enum modeEnum {
        START(1),
        END(2);
        private int intValue;
        private modeEnum(int toInt) {
            intValue = toInt;
        }
        public int value() { return intValue; }
    }

    public FFFeedParser(String xmlFeed) {
        mXmlFeed = xmlFeed;
        mFFData = FFData.getInstance();
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
                    //item start
                    if (xpp.getName() == "item"){ CreateItem();}
                    //get the link for the next page
                    if (xpp.getName() == "link") { getNextUrl(xpp); }
                } else if(eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName() == "item"){ CloseItem(); }
                    Log.d("TAG","End tag:--- "+xpp.getName());
                } else if(eventType == XmlPullParser.TEXT) {
                    buildItem(xpp.getText());
                }
                eventType = xpp.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void getNextUrl(XmlPullParser xpp) {
        //GET THE NEXT URL
        int b = xpp.getAttributeCount();
        if (b > 2) {
            String a = xpp.getAttributeName(1);
            Log.d("TADAAaaa-------", String.valueOf(b));
        }
    }

    private void CreateItem(){
        ItemMode = modeEnum.START.value();
        mffffItem = new FFFFItem();
    }
    private void CloseItem(){
        ItemMode = modeEnum.END.value();
        mFFData.setItems(mffffItem);
        mffffItem = null;
    }

    private void buildItem(String text){
                    Log.d("TAG",text);
    }


}
