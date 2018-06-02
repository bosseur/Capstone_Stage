package br.bosseur.beachvolleytour.remote;

import android.support.annotation.NonNull;

import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.utils.DateUtil;

public final class FivbRequestHelper {

  protected static String createFivbTourRequest(int year) {
    String startYear = DateUtil.getStartYear(year);
    String endYear = DateUtil.getEndYear(year);
    return "<Requests><Request Type=\"GetBeachTournamentList\" " +
        "Fields=\"Type CountryCode Code Name Title Gender Type StartDateMainDraw EndDateMainDraw No NoEvent\">" +
        "<Filter FirstDate=\"" + startYear + "\" LastDate=\"" + endYear + "\"/>" +
        "</Request></Requests>";
  }


  public static String createMatches(BeachTournament tournament) {
    String requestRounds;
    String requestMatches;

    if (tournament.getFemaleTournamentCode() != null && tournament.getMaleTournamentCode() != null) {
      requestRounds = createRequestRound(tournament.getMaleTournamentCode());
      requestRounds += createRequestRound(tournament.getFemaleTournamentCode());
      requestMatches = createRequestMatches(tournament.getMaleTournamentCode());
      requestMatches += createRequestMatches(tournament.getFemaleTournamentCode());
    } else {
      requestRounds = createRequestRound(tournament.getNumber().toString());
      requestMatches = createRequestMatches(tournament.getNumber().toString());
    }


    return "<Requests>" + requestRounds + requestMatches + "</Requests>";
  }

  private static String createRequestMatches(String numberTournament) {
    return "<Request Type=\"GetBeachMatchList\" "
        + "Fields=\"NoTournament NoInTournament NoTeamA NoTeamB TeamAFederationCode TeamBFederationCode NoRound "
        + "TeamAName TeamBName Court PointsTeamASet1 PointsTeamBSet1 PointsTeamASet2 PointsTeamBSet2 PointsTeamASet3 PointsTeamBSet3 "
        + "ResultType TeamAPositionInMainDraw TeamBPositionInMainDraw \">"
        + createFilterTournament(numberTournament)
        + "</Request>";
  }

  private static String createRequestRound(String numberTournament) {
    return "<Request Type=\"GetBeachRoundList\" "
        + "Fields=\"NoTournament StartDate Name Phase No \">"
        + createFilterTournament(numberTournament)
        + "</Request>";
  }

  @NonNull
  private static String createFilterTournament(String numberTournament) {
    return "<Filter NoTournament=\"" + numberTournament + "\"/>";
  }

}
