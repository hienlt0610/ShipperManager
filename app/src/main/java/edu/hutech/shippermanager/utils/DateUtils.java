package edu.hutech.shippermanager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by hienl on 11/5/2016.
 */

public class DateUtils {
    private DateUtils(){}

    /**
     * Convert Datetime to format String
     * @param date
     * @param pattern
     * @return String
     * @throws Exception
     */
    public static String dateToString(Date date, String pattern)
            throws Exception {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date stringToDate(String dateStr, String pattern)
            throws Exception {
        return new SimpleDateFormat(pattern).parse(dateStr);
    }

    /**
     * Get year from date
     * @param date
     * @return int year
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * Get month from date
     * @param date
     * @return int month
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;

    }

    /**
     * Get day from date
     * @param date
     * @return int day
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /****
     * Parses date string and return a {@link java.util.Date} object
     *
     * @return The ISO formatted date object
     *****/
    public static Date parseDate(String date) {

        if (date == null) {
            return null;
        }

        StringBuffer sbDate = new StringBuffer();
        sbDate.append(date);
        String newDate = null;
        Date dateDT = null;

        try {
            newDate = sbDate.substring(0, 19).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String rDate = newDate.replace("T", " ");
        String nDate = rDate.replaceAll("-", "/");

        try {
            dateDT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).parse(nDate);
            // Log.v( TAG, "#parseDate dateDT: " + dateDT );
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateDT;
    }

    /**
     * Gets the name of the day of the week.
     *
     * @param date
     *            ISO format date
     * @return The name of the day of the week
     **/
    public static String getDayOfWeek(String date) {
        // TODO: move to DateUtils
        Date dateDT = DateUtils.parseDate(date);

        if (dateDT == null) {
            return null;
        }

        // Get current date
        Calendar c = Calendar.getInstance();
        // it is very important to
        // set the date of
        // the calendar.
        c.setTime(dateDT);
        int day = c.get(Calendar.DAY_OF_WEEK);

        String dayStr = null;

        switch (day) {

            case Calendar.SUNDAY:
                dayStr = "Sunday";
                break;

            case Calendar.MONDAY:
                dayStr = "Monday";
                break;

            case Calendar.TUESDAY:
                dayStr = "Tuesday";
                break;

            case Calendar.WEDNESDAY:
                dayStr = "Wednesday";
                break;

            case Calendar.THURSDAY:
                dayStr = "Thursday";
                break;

            case Calendar.FRIDAY:
                dayStr = "Friday";
                break;

            case Calendar.SATURDAY:
                dayStr = "Saturday";
                break;
        }

        return dayStr;
    }

    /**
     * calculate difference form two dates Note: both dates are in same format.
     *
     * @param mDate1 date 1
     * @param mDate2 date 2
     * @return date difference in long
     */
    public static long calculateDays(Date mDate1, Date mDate2) {
        return Math.abs((mDate1.getTime() - mDate2.getTime()) / (24 * 60 * 60 * 1000) + 1);
    }

    /**
     * get Current time in milliseconds
     *
     * @return current time in milliseconds
     */
    public static long getCurrentTimeInMiliseconds() {
        return TimeUnit.MILLISECONDS.toMillis(Calendar.getInstance()
                .getTimeInMillis());
    }

    /**
     * get Current time in seconds
     *
     * @return current time in seconds
     */
    public static long getCurrentTimeInSeconds() {
        return TimeUnit.SECONDS.toSeconds(Calendar.getInstance()
                .getTimeInMillis());

    }
}
