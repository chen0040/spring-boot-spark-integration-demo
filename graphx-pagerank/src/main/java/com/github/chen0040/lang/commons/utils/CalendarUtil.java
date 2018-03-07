package com.github.chen0040.lang.commons.utils;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by xschen on 8/16/16.
 */
public class CalendarUtil implements Serializable {
   private static final long serialVersionUID = 4209172099706072215L;

   public static int toCalendarDoW(DayOfWeek dow) {
      if (dow == DayOfWeek.MONDAY)
         return Calendar.MONDAY;
      if (dow == DayOfWeek.TUESDAY)
         return Calendar.TUESDAY;
      if (dow == DayOfWeek.WEDNESDAY)
         return Calendar.WEDNESDAY;
      if (dow == DayOfWeek.THURSDAY)
         return Calendar.THURSDAY;
      if (dow == DayOfWeek.FRIDAY)
         return Calendar.FRIDAY;
      if (dow == DayOfWeek.SATURDAY)
         return Calendar.SATURDAY;
      return Calendar.SUNDAY;
   }


   public static DayOfWeek toDoW(int cdow) {
      if (cdow == Calendar.MONDAY)
         return DayOfWeek.MONDAY;
      if (cdow == Calendar.TUESDAY)
         return DayOfWeek.TUESDAY;
      if (cdow == Calendar.WEDNESDAY)
         return DayOfWeek.WEDNESDAY;
      if (cdow == Calendar.THURSDAY)
         return DayOfWeek.THURSDAY;
      if (cdow == Calendar.FRIDAY)
         return DayOfWeek.FRIDAY;
      if (cdow == Calendar.SATURDAY)
         return DayOfWeek.SATURDAY;
      return DayOfWeek.SUNDAY;
   }

   public static DayOfWeek getDayOfWeek(Date thedate) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(thedate);
      int calendarDow = cal.get(Calendar.DAY_OF_WEEK);
      return toDoW(calendarDow);
   }

   public static List<DayOfWeek> listDoW(){
      List<DayOfWeek> dow = new ArrayList<>();
      for(int i=1; i <= 7; ++i){
         dow.add(toDoW(i));
      }
      return dow;
   }



   public static DayOfWeek getDayOfWeek(long time) {
      return getDayOfWeek(new Date(time));
   }


//   public static long getSundayStartTime(long time) {
//      DayOfWeek dayOfWeek = CalendarUtil.getDayOfWeek(time);
//
//      // calculates number of days to last Sunday (i.e. first day of given week)
//      int dowOffset = (CalendarUtil.toCalendarDoW(dayOfWeek) - 1);
//      long timeOffset = dowOffset * 24 * 3600000L;
//
//      // converts given date to same day with time=00:00:00 (i.e. start of given day)
//      long dayStartTime = startOfDay(time);
//
//      // returns Sunday 00:00:00 of the given week
//      return ((dayStartTime - timeOffset));
//   }




//   public static long getDaysInMilliseconds(int days) {
//      return 3600000L * 24 * days;
//   }

   public static long startOfDay(long time) {
      Calendar c = Calendar.getInstance();
      c.setTime(new Date(time));
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 0);
      return c.getTimeInMillis();
   }

   public static long endOfDay(long time) {
      Calendar c = Calendar.getInstance();
      c.setTime(new Date(time));
      c.set(Calendar.HOUR_OF_DAY, 23);
      c.set(Calendar.MINUTE, 59);
      c.set(Calendar.SECOND, 59);
      c.set(Calendar.MILLISECOND, 999);
      return c.getTimeInMillis();
   }


   public static long getStartTimeOfWeek(long time) {

//      System.out.println("givenTime = " + new Date(time));

      DayOfWeek startDayOfWeek = DayOfWeek.SUNDAY;
      int startDayOfWeekInInt = CalendarUtil.toCalendarDoW(startDayOfWeek);

      DayOfWeek givenDayOfWeek = CalendarUtil.getDayOfWeek(time);
      int givenDayOfWeekInInt = CalendarUtil.toCalendarDoW(givenDayOfWeek);

      // calculates number of days to first day of given week (as specified in startDayOfWeek)
      // E.g. if startDayOfWeek = Sunday, then first day of given week = last Sunday, or today if today is Sunday)
      int dowOffset;
      if (givenDayOfWeekInInt >= startDayOfWeekInInt) {
         dowOffset = givenDayOfWeekInInt - startDayOfWeekInInt;
      }
      else {
         dowOffset = (givenDayOfWeekInInt + 7) - startDayOfWeekInInt;
      }

      long timeOffset = dowOffset * 24 * 3600000L;

      // converts given date to same day with time=00:00:00 (i.e. start of given day)
      long giveDayStartTime = startOfDay(time);

      // returns Sunday 00:00:00 of the given week
//      System.out.println("startTimeOfWeek = " + new Date(giveDayStartTime - timeOffset));
      return ((giveDayStartTime - timeOffset));
   }

   public static long getEndTimeOfWeek(long time) {

      long startTimeOfWeek = getStartTimeOfWeek(time);
      long endTimeOfWeek = startTimeOfWeek + (7 * 24 * 3600000L - 1000);

//      System.out.println("endTimeOfWeek = " + new Date(endTimeOfWeek));
      return endTimeOfWeek;
   }

   public static Date getEndTimeOfWeek(int year, int week) {
      Calendar c = Calendar.getInstance();
      c.set(Calendar.YEAR, year);
      c.set(Calendar.WEEK_OF_YEAR, week);
      c.set(Calendar.DAY_OF_WEEK, 1);
      c.set(Calendar.HOUR_OF_DAY, 0);
      c.set(Calendar.MINUTE, 0);
      c.set(Calendar.SECOND, 0);

      return c.getTime();
   }



//   public static long getStartTimeOfDayOfWeek(long currentTime, DayOfWeek dayOfWeek) {
//      return getSundayStartTime(currentTime) + toCalendarDoW(dayOfWeek) * 24 * 3600000L;
//   }


//   public static List<TupleTwo<Long, Long>> getTimeWindowsByDayOfWeek(long startTime, int size, DayOfWeek dayOfWeek) {
//      long firstStartTimeOfDayOfWeek = getStartTimeOfDayOfWeek(startTime, dayOfWeek);
//      List<TupleTwo<Long, Long>> results = new ArrayList<>();
//      for(int i=0; i < size; ++i){
//         long startTimeOfDayOfWeek = firstStartTimeOfDayOfWeek + i * 7 * 24 * 3600000L;
//         long endTimeOfDayOfWeek = startTimeOfDayOfWeek + 24 * 3600000L - 1L;
//
//         results.add(new TupleTwo<>(startTimeOfDayOfWeek, endTimeOfDayOfWeek));
//      }
//      return results;
//   }


   public static long getYearsInMilliseconds(int yearCount) {
      return 3600000L * 24 * 365 * yearCount;
   }

   public static long getMonthsInMilliseconds(int monthCount) {
      return 3600000L * 24 * 30 * monthCount;
   }

   public static long getWeeksInMilliseconds(int weekCount) {
      return 3600000L * 24 * 7 * weekCount;
   }

   public static long getDaysInMilliseconds(int dayCount) {
      return 3600000L * 24 * dayCount;
   }

   public static long getTimeAtNWeeksAgo(long now, int weekCount) {

      long sundayStartTime = getStartTimeOfWeek(now);
      return sundayStartTime - getWeeksInMilliseconds(weekCount);
   }

   public static SimpleDateFormat createDateFormat() {
      return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   }

   public static double getTimeBetweenDatesInMonths(long startDate, long endDate) {
      long timeBetweenDates = endDate - startDate;
      double value = timeBetweenDates / getMonthsInMilliseconds(1);
      double timeBetweenDatesInMonths = Math.floor(Double.parseDouble(value+""));

//      System.out.println ("startDate = " + new Date(startDate));
//      System.out.println ("endDate = " + new Date(endDate));
//      System.out.println ("timeBetweenDatesInMonths = " + timeBetweenDatesInMonths);
      return timeBetweenDatesInMonths;
   }
}
