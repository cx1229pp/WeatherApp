package com.weather.app.customLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weather.app.R;
import com.weather.app.customView.SmartImageView;
import com.weather.app.model.Temperature;
import com.weather.app.model.Weather;
import com.weather.app.util.WeatherConstant;

import java.util.Calendar;
import java.util.List;

/**
 * 自定义天气布局
 * Created by chenxue on 2015/5/11.
 */
public class WeatherLaytout extends LinearLayout{
    private Context context;
    private LinearLayout dayWeatherLayout1;
    private LinearLayout dayWeatherLayout2;
    private LinearLayout dayWeatherLayout3;
    private LinearLayout dayWeatherLayout4;
    private TextView tvDay1;
    private TextView tvDay2;
    private TextView tvDay3;
    private TextView tvDay4;
    private SmartImageView sivDayImg1;
    private SmartImageView sivDayImg2;
    private SmartImageView sivDayImg3;
    private SmartImageView sivDayImg4;
    private TextView tvDayWeather1;
    private TextView tvDayWeather2;
    private TextView tvDayWeather3;
    private TextView tvDayWeather4;
    private TextView tvDayTemperature1;
    private TextView tvDayTemperature2;
    private TextView tvDayTemperature3;
    private TextView tvDayTemperature4;
    private TextView tvDayWind1;
    private TextView tvDayWind2;
    private TextView tvDayWind3;
    private TextView tvDayWind4;
    private SmartImageView sivDayImgN1;
    private SmartImageView sivDayImgN2;
    private SmartImageView sivDayImgN3;
    private SmartImageView sivDayImgN4;
    private TextView tvWeek1;
    private TextView tvWeek2;
    private TextView tvWeek3;
    private TextView tvWeek4;
    private static final int GET_IMG = 1;

    public WeatherLaytout(Context context) {
        this(context,null);
    }

    public WeatherLaytout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public void init(){
        LayoutInflater.from(context).inflate(R.layout.weather_layout, this);
        tvDay1 = (TextView)findViewById(R.id.tv_day_1);
        tvDay2 = (TextView)findViewById(R.id.tv_day_2);
        tvDay3 = (TextView)findViewById(R.id.tv_day_3);
        tvDay4 = (TextView)findViewById(R.id.tv_day_4);

        sivDayImg1 = (SmartImageView)findViewById(R.id.iv_day_img_1);
        sivDayImg2 = (SmartImageView)findViewById(R.id.iv_day_img_2);
        sivDayImg3 = (SmartImageView)findViewById(R.id.iv_day_img_3);
        sivDayImg4 = (SmartImageView)findViewById(R.id.iv_day_img_4);

        sivDayImgN1 = (SmartImageView)findViewById(R.id.iv_day_img_n_1);
        sivDayImgN2 = (SmartImageView)findViewById(R.id.iv_day_img_n_2);
        sivDayImgN3 = (SmartImageView)findViewById(R.id.iv_day_img_n_3);
        sivDayImgN4 = (SmartImageView)findViewById(R.id.iv_day_img_n_4);

        tvDayTemperature1 = (TextView)findViewById(R.id.tv_day_temperature_1);
        tvDayTemperature2 = (TextView)findViewById(R.id.tv_day_temperature_2);
        tvDayTemperature3 = (TextView)findViewById(R.id.tv_day_temperature_3);
        tvDayTemperature4 = (TextView)findViewById(R.id.tv_day_temperature_4);

        tvDayWeather1 = (TextView)findViewById(R.id.tv_day_weather_1);
        tvDayWeather2 = (TextView)findViewById(R.id.tv_day_weather_2);
        tvDayWeather3 = (TextView)findViewById(R.id.tv_day_weather_3);
        tvDayWeather4 = (TextView)findViewById(R.id.tv_day_weather_4);

        tvDayWind1 = (TextView)findViewById(R.id.tv_day_wind_1);
        tvDayWind2 = (TextView)findViewById(R.id.tv_day_wind_2);
        tvDayWind3 = (TextView)findViewById(R.id.tv_day_wind_3);
        tvDayWind4 = (TextView)findViewById(R.id.tv_day_wind_4);

        tvWeek1 = (TextView)findViewById(R.id.tv_week_1);
        tvWeek2 = (TextView)findViewById(R.id.tv_week_2);
        tvWeek3 = (TextView)findViewById(R.id.tv_week_3);
        tvWeek4 = (TextView)findViewById(R.id.tv_week_4);
    }

    public void setData(Weather weather){
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(calendar.DAY_OF_WEEK);
        String day = context.getString(R.string.day);
        tvDay3.setText(today + 2 + day);
        tvDay4.setText(today + 3 + day);

        tvWeek1.setText(context.getString(WeatherConstant.getWeekZH(dayOfWeek - 1)));
        tvWeek2.setText(context.getString(WeatherConstant.getWeekZH(dayOfWeek)));
        tvWeek3.setText(context.getString(WeatherConstant.getWeekZH(dayOfWeek + 1)));
        tvWeek4.setText(context.getString(WeatherConstant.getWeekZH(dayOfWeek + 2)));

        List<Temperature> temperatureList = weather.getTemperatureList();
        if(temperatureList != null && temperatureList.size() > 0){
            Temperature t1 = temperatureList.get(0);
            Temperature t2 = temperatureList.get(1);
            Temperature t3 = temperatureList.get(2);
            Temperature t4 = temperatureList.get(3);

            tvDayWeather1.setText(t1.getWeather());
            tvDayWeather2.setText(t2.getWeather());
            tvDayWeather3.setText(t3.getWeather());
            tvDayWeather4.setText(t4.getWeather());

            tvDayTemperature1.setText(t1.getTemperature());
            tvDayTemperature2.setText(t2.getTemperature());
            tvDayTemperature3.setText(t3.getTemperature());
            tvDayTemperature4.setText(t4.getTemperature());

            tvDayWind1.setText(t1.getWind());
            tvDayWind2.setText(t2.getWind());
            tvDayWind3.setText(t3.getWind());
            tvDayWind4.setText(t4.getWind());

            sivDayImg1.setUrl(t1.getDayPictureUrl());
            sivDayImg2.setUrl(t2.getDayPictureUrl());
            sivDayImg3.setUrl(t3.getDayPictureUrl());
            sivDayImg4.setUrl(t4.getDayPictureUrl());

            sivDayImgN1.setUrl(t1.getNightPictureUrl());
            sivDayImgN2.setUrl(t2.getNightPictureUrl());
            sivDayImgN3.setUrl(t3.getNightPictureUrl());
            sivDayImgN4.setUrl(t4.getNightPictureUrl());
        }
    }
}
