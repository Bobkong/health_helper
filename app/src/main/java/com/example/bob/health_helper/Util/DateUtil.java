package com.example.bob.health_helper.Util;

import android.content.Context;

import com.example.bob.health_helper.R;
import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {


    public static String dateTransform(String str){
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA);
        try{
            date=sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return sdf2.format(date);
    }

    public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    // 日期格式化
    private static DateFormat dateTimeFormat = null;


    static {
        dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);

    }


    public static String getDisplayString(Context context, Date date) {
        long delta = new Date().getTime() - date.getTime();
        if (delta < ONE_MINUTE) {
            return context.getString(R.string.just);
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return String.format(context.getString(R.string.minute_ago), minutes <= 0 ? 1 : minutes);
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return String.format(context.getString(R.string.hour_ago), hours <= 0 ? 1 : hours);
        }
        if (delta < 48L * ONE_HOUR) {
            return context.getString(R.string.yesterday);
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return String.format(context.getString(R.string.day_ago), days <= 0 ? 1 : days);
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return String.format(context.getString(R.string.month_ago), months <= 0 ? 1 : months);
        } else {
            long years = toYears(delta);
            return String.format(context.getString(R.string.year_ago), years <= 0 ? 1 : years);
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }


    /**
     * 日期格式化yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getDateTimeFormat(Date date) {
        return dateTimeFormat.format(date);
    }

}