package com.weather.app.test;

import android.os.Looper;

import java.util.Calendar;

/**
 * Created by ldn on 2015/5/16.
 */
public class Test {
    public static void main(String[] args){
        Calendar cal = Calendar.getInstance();
        int today = cal.get(cal.DAY_OF_MONTH);
        int week = cal.get(cal.DAY_OF_WEEK);
        System.out.println("today="+today);
        System.out.println("week="+week);

    }

}
