package com.gymassistant;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by KamilH on 2016-05-03.
 */
public class DateConverter {
    public static long dateToTime(String date){
        if(date != null){
            DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
            DateTime dateTime = dateTimeFormat.parseDateTime(date);

            return dateTime.getMillis();
        }
        return -1;
    }

    public static String timeToDate(long time){
        if(time > 0){
            DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
            DateTime dateTime = new DateTime(time);
            return dateTimeFormat.print(dateTime);
        }
        return null;
    }

    public static String hasTwoLatters(int num){
        if(num < 10){
            return "0" + String.valueOf(num);
        } else {
            return String.valueOf(num);
        }
    }

    public static String today(){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
        DateTime dateTime = new DateTime(DateTime.now());
        return dateTimeFormat.print(dateTime);
    }

    public static String addDays(int days){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
        DateTime dateTime = new DateTime(DateTime.now());
        dateTime = dateTime.plusDays(days);
        return dateTimeFormat.print(dateTime);
    }

    private static long daysToMilis(int days){
        return (long) 86400000 * days;
    }
}