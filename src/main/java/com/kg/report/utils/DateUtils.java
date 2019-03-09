package com.kg.report.utils;

import com.kg.report.model.enums.ReportTypeEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
  private int month;
  private int day;
  private int week;
  private int hour;
  private int mints;

  private Date[] queryRange = {new Date(), new Date()};

  private boolean overDeadLine;

  public DateUtils() {
  }

  private boolean isHourDead(int deadHour, int deadMints) {
    return hour > deadHour  || (deadHour == hour && mints > deadMints);
  }

  public DateUtils(ReportTypeEnum type, String deadLine) {
    this(type, deadLine, new Date());
  }

  public DateUtils(ReportTypeEnum type, String deadLine, Date date) {
    Calendar calendar=Calendar.getInstance();
    calendar.setTime(date);
    month = calendar.get(Calendar.MONTH)+1;
    day = calendar.get(Calendar.DAY_OF_MONTH);
    week = calendar.get(Calendar.DAY_OF_WEEK)-1;
    week = week == 0 ? 7 : week;
    hour = calendar.get(Calendar.HOUR_OF_DAY);
    mints = calendar.get(Calendar.MINUTE);

    String[] deadLines = deadLine.split("-");
    int deadHour = Integer.valueOf(deadLines[3]);
    int deadMints = Integer.valueOf(deadLines[4]);
    boolean isHourDead = isHourDead(deadHour, deadMints);

    switch (type) {
      case Daily:
        if (isHourDead) {
          overDeadLine = true;
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        queryRange[0] = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        queryRange[1] = calendar.getTime();
        break;
      case Weekly:
        int deadWeek = Integer.valueOf(deadLines[1]);
        if (deadWeek < week || (deadWeek == week && isHourDead)) {
          overDeadLine = true;
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.add(Calendar.DATE, -(week - 1));
        queryRange[0] = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.add(Calendar.DATE, 6);
        queryRange[1] = calendar.getTime();
        break;
      case Monthly:
        int deadDay = Integer.valueOf(deadLines[2]);
        if (deadDay < day || ( deadDay == day && isHourDead)) {
          overDeadLine = true;
        }
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        queryRange[0] = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        queryRange[1] = calendar.getTime();
      case Annually:
        // TODO
        break;
    }
  }

  public static Date formatDate(String date){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      return sdf.parse(date);
    } catch (ParseException e) {
      return null;
    }
  }
  public static String formatDate(Date date){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(date);
  }

  public static String formatDateHuman(Date date){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
    return sdf.format(date);
  }

  public int getMonth() {
    return month;
  }

  public int getDay() {
    return day;
  }

  public int getWeek() {
    return week;
  }

  public int getHour() {
    return hour;
  }

  public int getMints() {
    return mints;
  }

  public Date[] getQueryRange() {
    return queryRange;
  }

  public boolean isOverDeadLine() {
    return overDeadLine;
  }
}
