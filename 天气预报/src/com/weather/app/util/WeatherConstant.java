package com.weather.app.util;

import com.weather.app.R;

/**
 * Created by ldn on 2015/5/16.
 */
public class WeatherConstant {
    public static final int MONDAY_ZH = R.string.week1;
    public static final int TUESDAY_ZH = R.string.week2;
    public static final int WEDNESDAY_ZH = R.string.week3;
    public static final int THURSDAY_ZH = R.string.week4;
    public static final int FRIDAY_ZH = R.string.week5;
    public static final int SATURDAY_ZH = R.string.week6;
    public static final int SUNDAY_ZH = R.string.week7;

    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    public static final int SUNDAY = 7;

    //百度天气接口
    public static final String GET_WEATHER_URL = "http://api.map.baidu.com/telematics/v3/weather?location=LOCATION&output=json&ak=AK";

    /**
     * 获取星期数
     * @param week
     * @return
     */
    public static int getWeekZH(int week){
        int weekStrId = 0;
        switch (week){
            case MONDAY :
                weekStrId = MONDAY_ZH;
            break;

            case 8 :
                weekStrId = MONDAY_ZH;
                break;

            case TUESDAY :
                weekStrId = TUESDAY_ZH;
                break;

            case 9 :
                weekStrId = TUESDAY_ZH;
                break;

            case WEDNESDAY :
                weekStrId = WEDNESDAY_ZH;
                break;

            case 10 :
                weekStrId = WEDNESDAY_ZH;
                break;

            case THURSDAY :
                weekStrId = THURSDAY_ZH;
                break;

            case FRIDAY :
                weekStrId = FRIDAY_ZH;
                break;

            case SATURDAY :
                weekStrId = SATURDAY_ZH;
                break;

            case SUNDAY :
                weekStrId = SUNDAY_ZH;
                break;

            case 0 :
                weekStrId = SUNDAY_ZH;
                break;
        }

        return weekStrId;
    }
}
