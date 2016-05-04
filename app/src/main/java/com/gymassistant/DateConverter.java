package com.gymassistant;

import org.joda.time.DateTime;

/**
 * Created by KamilH on 2016-05-03.
 */
public class DateConverter {
    public static long dateToTime(DateTime dateTime){
        return dateTime.getMillis();
    }

    public static DateTime timeToDate(long time){
        return new DateTime(time);
    }
}
