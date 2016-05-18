package com.gymassistant;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by KamilH on 2016-05-03.
 */
public class DateConverter {
    public static long dateToTime(String date){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
        DateTime dateTime = dateTimeFormat.parseDateTime(date);

        return dateTime.getMillis();
    }

    public static String timeToDate(long time){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
        DateTime dateTime = new DateTime(time);
        return dateTimeFormat.print(dateTime);
    }

    public static String hasTwoLatters(int num){
        if(num < 10){
            return "0" + String.valueOf(num);
        } else {
            return String.valueOf(num);
        }
    }
}
