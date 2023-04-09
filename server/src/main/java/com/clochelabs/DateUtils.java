package com.clochelabs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    public static String dateToString(Date date){
        return format.format(date);
    }

    public static Date stringToDate(String date) {
        try {
            return format.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
