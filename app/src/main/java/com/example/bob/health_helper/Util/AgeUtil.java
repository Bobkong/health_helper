package com.example.bob.health_helper.Util;

import java.util.Calendar;
import java.util.Date;

public class AgeUtil {
    public static int getAgeFromDate(Date date){
        if(date!=null){
            //当前时间的年月日
            Calendar calendar=Calendar.getInstance();
            int yearNow=calendar.get(Calendar.YEAR);
            int monthNow=calendar.get(Calendar.MONTH)+1;
            int dayNow=calendar.get(Calendar.DAY_OF_MONTH);
            //输入时间的年月日
            calendar.setTime(date);
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH)+1;
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            //计算年龄
            int yearMinus=yearNow-year;
            int monthMinus=monthNow-month;
            int dayMinus=dayNow-day;
            int age=yearMinus;
            if(yearMinus<=0) return 0;
            else{
                if(monthMinus<0)
                    age-=1;
                else if(monthMinus==0){
                    if(dayMinus<0)
                        age-=1;
                }
                return age;
            }
        }
        return 0;
    }
}
