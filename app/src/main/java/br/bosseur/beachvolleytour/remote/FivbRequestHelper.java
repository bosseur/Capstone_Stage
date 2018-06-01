package br.bosseur.beachvolleytour.remote;

import br.bosseur.beachvolleytour.utils.DateUtil;

public final class FivbRequestHelper {

  protected static String createFivbTourRequest(int year) {
    String startYear = DateUtil.getStartYear(year);
    String endYear = DateUtil.getEndYear(year);
    return "<Requests><Request Type=\"GetBeachTournamentList\" " +
        "Fields=\"Type CountryCode Code Name Title Gender Type StartDateMainDraw EndDateMainDraw No NoEvent\">"+
        "<Filter FirstDate=\"" + startYear + "\" LastDate=\"" + endYear + "\"/>"+
        "</Request></Requests>";
  }
}
