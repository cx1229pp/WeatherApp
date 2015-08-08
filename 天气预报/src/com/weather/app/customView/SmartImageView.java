package com.weather.app.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.weather.app.util.HttpUtil;

import java.io.InputStream;

/**
 * 自定义ImageView，用于异步获取网络图片
 * Created by chenxue on 2015/5/15.
 */
public class SmartImageView extends ImageView{
    public SmartImageView(Context context) {
        super(context);
    }

    public SmartImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmartImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setUrl(String url){
        new WebImageTask().execute(url);
    }

    public void setImage(Bitmap bm){
        setImageBitmap(bm);
    }

    private class WebImageTask extends AsyncTask<String,Integer,Bitmap>{
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bm = null;
            try {
                InputStream is = HttpUtil.requestStream(params[0]);
                if(is != null){
                    bm = BitmapFactory.decodeStream(is);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bm) {
            setImage(bm);
        }
    }
}
