package com.toidiu.ffffound.utils;


import com.google.gson.Gson;
import com.toidiu.ffffound.model.FFGalleryItem;

import java.util.ArrayList;

public class FFFeedParser {
    String mxmlFeed;

    public FFFeedParser(String xmlFeed) {
        mxmlFeed = xmlFeed;
    }

    public ArrayList<FFGalleryItem> parse() {
        return null;
    }


    private class FFItem {
        private String Thumbnail;
        private String Title;
        private int score;

        public String getThumbnail() {
            return Thumbnail;
        }
        public void setThumbnail(String thumbnail) {
            Thumbnail = thumbnail;
        }

        public String getTitle() {
            return Title;
        }
        public void setTitle(String title) {
            Title = title;
        }

        public int getScore() {
            return score;
        }
        public void setScore(int score) {
            this.score = score;
        }
    }
}