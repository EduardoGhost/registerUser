package com.eduardo.cadastro.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    public static long getTimestampFromDateString(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            Date date = sdf.parse(dateString);
            return date != null ? date.getTime() : 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String formatDateFromTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        Date date = new Date(timestamp);
        return sdf.format(date);
    }
}
