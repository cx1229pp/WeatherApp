package com.weather.app.model;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by ldn on 2015/5/16.
 */
public class WebImage implements  SmartImage{
    private String url;

    public WebImage(String url) {
        this.url = url;
    }

    @Override
    public Bitmap getBitmap(Context context) {


        return null;
    }
}
