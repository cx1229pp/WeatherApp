package com.weather.app.asyncTack;

import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by ldn on 2015/5/15.
 */
public class AsyncWebImageTask extends AsyncTask<ImageView,Integer,Boolean>{
    @Override
    protected Boolean doInBackground(ImageView... params) {
        params[0].setImageBitmap(null);
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

    }
}
