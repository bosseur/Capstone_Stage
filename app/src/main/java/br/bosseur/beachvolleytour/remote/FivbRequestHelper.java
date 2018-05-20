package br.bosseur.beachvolleytour.remote;

import java.util.Calendar;

import br.bosseur.beachvolleytour.utils.DateUtil;

public class FivbRequestHelper {

  public static String createFivbTourRequest(int year) {
    String startYear = DateUtil.getStartYear(year);
    String endYear = DateUtil.getEndYear(year);
    return "<Requests><Request Type=\"GetBeachTournamentList\" " +
        "Fields=\"Type CountryCode Code Name Title Gender Type Earnings StartDateMainDraw EndDateMainDraw No NoEvent Status\">"+
        "<Filter FirstDate=\"" + startYear + "\" LastDate=\"" + endYear + "\"/>"+
        "</Request></Requests>";
  }
}
