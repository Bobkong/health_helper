package com.example.bob.health_helper.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.example.bob.health_helper.Local.LocalBean.Reminder;
import com.example.bob.health_helper.Me.ReminderReceiver;
import java.util.Calendar;

public class AlarmManagerUtil {
    public static final String ALARM_ACTION="com.example.bob.health_helper.alarm";

    public static void cancelAlarm(Context context,int id){
        Intent intent=new Intent(ALARM_ACTION);
        intent.putExtra("id",id);
        intent.setClass(context, ReminderReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pi);
    }

    public static void setAlarm(Context context,int id, int hour,int minute,int weekday,String tips){
        AlarmManager manager=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar=cacluteNextAlarm(hour,minute,weekday);
        if(calendar.getTimeInMillis()<System.currentTimeMillis())//设置时间小于当前系统时间
            return;
        Intent intent=new Intent(ALARM_ACTION);
        if (android.os.Build.VERSION.SDK_INT >= 12) {
            intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        }
        intent.putExtra("id",id);
        intent.putExtra("hour",hour);
        intent.putExtra("minute",minute);
        intent.putExtra("weekday",weekday);
        intent.putExtra("tips",tips);
        intent.setClass(context, ReminderReceiver.class);
        PendingIntent sender=PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
    }

    //计算闹钟
    public static Calendar cacluteNextAlarm(int hour,int minute,int dayOfweek){
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        mCalendar.set(Calendar.HOUR_OF_DAY,hour);
        mCalendar.set(Calendar.MINUTE, minute);
        int differDays = getNextAlarmDifferDays(dayOfweek,mCalendar.get(Calendar.DAY_OF_WEEK), mCalendar.getTimeInMillis());
        int nextYear = getNextAlarmYear(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.DAY_OF_YEAR), mCalendar.getActualMaximum(Calendar.DAY_OF_YEAR), differDays);
        int nextMonth = getNextAlarmMonth(mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH), mCalendar.getActualMaximum(Calendar.DATE), differDays);
        int nextDay = getNextAlarmDay(mCalendar.get(Calendar.DAY_OF_MONTH), mCalendar.getActualMaximum(Calendar.DATE), differDays);
        mCalendar.set(Calendar.YEAR,nextYear);
        mCalendar.set(Calendar.MONTH, nextMonth % 12);//月份从0开始
        mCalendar.set(Calendar.DAY_OF_MONTH, nextDay);
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);
        return mCalendar;
    }


    //获取下次闹钟相差的天数
    private static int getNextAlarmDifferDays(int data, int currentDayOfWeek,long timeInMills){
        int nextDayOfWeek =  getNextDayOfWeek(data, currentDayOfWeek,timeInMills);
        return currentDayOfWeek<=nextDayOfWeek?(nextDayOfWeek-currentDayOfWeek):(7 - currentDayOfWeek + nextDayOfWeek);
    }


    //考虑年进位的情况
    private static int getNextAlarmYear(int year,int dayOfYears, int actualMaximum, int differDays) {
        int temp = actualMaximum-dayOfYears-differDays;
        return temp >= 0?year:year+1;
    }

    //考虑月进位的情况
    private static int getNextAlarmMonth(int month,int dayOfMonth,int actualMaximum, int differDays) {
        int temp = actualMaximum-dayOfMonth-differDays;
        return temp >= 0?month:month+1;
    }

    //获取下次闹钟的day
    private static int getNextAlarmDay(int thisDayOfMonth, int actualMaximum, int differDays) {
        int temp = actualMaximum - thisDayOfMonth-differDays;
        if (temp<0){
            return thisDayOfMonth + differDays - actualMaximum;
        }
        return thisDayOfMonth + differDays;
    }

    //获取下次显示是星期几
    private static int getNextDayOfWeek(int data, int cWeek,long timeInMillis) {
        int tempBack = data >> cWeek - 1;
        int tempFront = data ;

        if(tempBack%2==1){
            if(System.currentTimeMillis()<timeInMillis)  return cWeek;
        }
        tempBack = tempBack>>1;
        int m=1,n=0;
        while (tempBack != 0) {
            if (tempBack % 2 == 1 ) return cWeek + m;
            tempBack = tempBack / 2;
            m++;
        }
        while(n<cWeek){
            if (tempFront % 2 == 1)  return n+1;
            tempFront =tempFront/2;
            n++;
        }
        return 0;
    }
}
