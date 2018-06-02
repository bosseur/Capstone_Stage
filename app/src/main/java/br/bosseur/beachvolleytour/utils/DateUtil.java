package br.bosseur.beachvolleytour.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

  private static String format(Calendar cal) {
    return df.format(cal.getTime());
  }

  public static String format(Date date, String pattern) {
    DateFormat df = new SimpleDateFormat(pattern);
    return df.format(date);
  }

  public static String getStartYear(int year) {
    Calendar cal = Calendar.getInstance();
    cal.set(year, Calendar.JANUARY, 1);
    return format(cal);
  }

  public static String getEndYear(int year) {
    Calendar cal = Calendar.getInstance();
    cal.set(year, Calendar.DECEMBER, 31);
    return format(cal);
  }


}
