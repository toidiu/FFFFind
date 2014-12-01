package com.toidiu.ffffind.utils;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Random;

public  class Stuff {

    private static final int MAX_RAND_OFFSET = 135000;

    public static int generateRandomColor(int mix) {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // mix the color
        if ( mix != 0 ) {
            red = (red + Color.red(mix)) / 2;
            green = (green + Color.green(mix)) / 2;
            blue = (blue + Color.blue(mix)) / 2;
        }

        int color = Color.argb(255, red, green, blue);
        return color;
    }

    public static int getRandOffset(){
        int randOffset = new Random().nextInt(MAX_RAND_OFFSET);
        return randOffset;
//        return ListFragment.EXPLORE_URL_BASE + randOffset;
    }

    public static boolean isConnected(Context ctx){
        ConnectivityManager cm = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null){
            return true;
        }else {
            return false;
        }
    }
}
