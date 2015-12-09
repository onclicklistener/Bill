package com.davesla.bill.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param sec
     * @return
     */
    public static String getTimeFormatText(long sec) {
        if (sec == 0) {
            return "";
        }
        long diff = System.currentTimeMillis() - sec;
        long r = 0;
        if (diff > day) {
            return "" + longToString(sec);
        }
        if (diff > hour) {
            r = (diff / hour);
            return "" + r + "小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return "" + r + "分钟前";
        }
        return "刚刚";
    }

    public static String intToString(int msec) {
        msec = (msec + 500) / 1000;
        int sec = msec % 60;
        msec /= 60;
        int min = msec % 60;
        msec /= 60;
        int hour = msec;
        String ret = "";
        if (hour > 0)
            ret = String.format(Locale.ENGLISH, "%02d:", hour);
        ret += String.format(Locale.ENGLISH, "%02d:%02d", min, sec);
        return ret;
    }

    public static int scaleTime(int time) {
        return Integer.parseInt(new BigDecimal((float) time / 1000).setScale(0, BigDecimal.ROUND_HALF_UP).toString()) * 1000;
    }


    public static String longToString(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String hms = formatter.format(time);
        return hms;
    }

    public static String getDate(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        String hms = formatter.format(time);
        return hms;
    }

    public static String getMonthAndDay(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd", Locale.CHINA);
        String hms = formatter.format(time);
        return hms;
    }

    public static String formatSecond(int sec) {
        if (sec <= 60) {
            return sec + "秒";
        } else {
            if (sec % 60 >= 10) {
                return (sec / 60) + "分" + (sec % 60) + "秒";
            } else {
                return (sec / 60) + "分" + "0" + (sec % 60) + "秒";
            }

        }
    }

    public static long DateToTimeStamp(String date) {
        long timeStamp = 0;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = simpleDateFormat.parse(date);
            timeStamp = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timeStamp;
    }

    public static String formatDuration(int duration) {
        int minute = duration / 60;
        int sec = duration % 60;
        return String.format("%02d",minute)+"'"+String.format("%02d",sec)+"\"";
    }

}
