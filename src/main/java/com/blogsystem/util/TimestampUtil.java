package com.blogsystem.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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
    public static String stringifyDatetime(OffsetDateTime datetime, boolean includeTime) {
        if (datetime == null) {
            return "";
        }
        if (includeTime) {
            var offset = datetime.getOffset().toString().equals("Z")
                    ? "+00:00" : datetime.getOffset().toString();

            return String.format("%s-%s-%sT%s:%s:%s",
                    datetime.getYear(),
                    StringUtils.leftPad(String.valueOf(datetime.getMonthValue()), 2, '0'),
                    StringUtils.leftPad(String.valueOf(datetime.getDayOfMonth()), 2, '0'),
                    StringUtils.leftPad(String.valueOf(datetime.getHour()), 2, '0'),
                    StringUtils.leftPad(String.valueOf(datetime.getMinute()), 2, '0'),
                    StringUtils.leftPad(String.valueOf(datetime.getSecond()), 2, '0') + offset);
        } else {
            return String.format("%s-%s-%s",
                    datetime.getYear(),
                    StringUtils.leftPad(String.valueOf(datetime.getMonthValue()), 2, '0'),
                    StringUtils.leftPad(String.valueOf(datetime.getDayOfMonth()), 2, '0'));
        }
    }

}
