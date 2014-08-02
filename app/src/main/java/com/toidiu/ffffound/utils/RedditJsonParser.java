package aww.toidiu.com.redditgallery.utils;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class RedditJsonParser implements JsonSerializer{

    public void parseRedditJson(String json){
        Gson gson = new Gson();
        Object json1 = gson.fromJson(json, RedditJson.class);
    }

    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {


        return null;
    }


    private class RedditJson{
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