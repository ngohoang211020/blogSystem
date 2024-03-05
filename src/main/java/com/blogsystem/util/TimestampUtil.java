package com.blogsystem.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TimestampUtil {
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static String getCurrentTimeString(){
        return dtf.format(LocalDateTime.now());
    }
    public static void main(String[] args) {
//        var instance = new TimestampUtil();
//
//        var now = TimestampUtil.now();
//        var nowDefault = TimestampUtil.nowAtDefaultTimezone();
//
//        System.out.println(now);
//        System.out.println(adjustNumberDays(now, 3, false));
//        var timeStamp= parseDateStringToOffsetDateTime("1980-1-20",DATE_FORMAT);
//        System.out.println(timeStamp);
    }

}
