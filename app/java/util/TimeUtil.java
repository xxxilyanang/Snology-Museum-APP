package com.example.gxcg.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    static Date result;
    public static String getCurrDate()
    {

        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
        Date date =new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
        try {
            result=dft.parse(dft.format(calendar.getTime()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return dft.format(result);
    }
    public static Date getFultureDate(int distanceday) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date begindate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begindate);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + distanceday);
        try {
            result= dft.parse(dft.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println(distanceday + "天后是：" + dft.format(enddate));
        return result;

    }
    public static Date getCurrWeek()
    {

        SimpleDateFormat dft = new SimpleDateFormat("E");
        Date date =new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK));
        try {
            result=dft.parse(dft.format(calendar.getTime()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;

    }

    public static String getFultureWeek(int distanceday) {
        SimpleDateFormat dft = new SimpleDateFormat("E");
        Date begindate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begindate);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.get(Calendar.DAY_OF_WEEK) + distanceday);
        Date enddate = null;
        try {
            enddate = dft.parse(dft.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(distanceday + "天后是：" + dft.format(enddate));
        return dft.format(enddate);

    }
}
