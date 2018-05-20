package br.bosseur.beachvolleytour.utils;

import java.util.Calendar;

public class DateUtil {
  public static String getStartYear(int year) {
    Calendar cal = Calendar.getInstance();
    cal.set(year, Calendar.JANUARY, 1);
    //TODO format date
    return null;
  }

  public static String getEndYear(int year) {
    Calendar cal = Calendar.getInstance();
    cal.set(year, Calendar.DECEMBER, 31);
    //TODO format date
    return null;
  }
}
