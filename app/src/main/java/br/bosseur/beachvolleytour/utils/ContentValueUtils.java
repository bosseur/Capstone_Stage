package br.bosseur.beachvolleytour.utils;

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

import br.bosseur.beachvolleytour.data.contracts.BeachMatchContract;
import br.bosseur.beachvolleytour.data.contracts.BeachRoundContract;
import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;
import br.bosseur.beachvolleytour.model.BeachRound;
import br.bosseur.beachvolleytour.model.BeachRoundList;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.model.MatchList;
import br.bosseur.beachvolleytour.model.TournamentMatch;

public class ContentValueUtils {


  public static ContentValues[] getContentValues(final List<BeachTournament> tournaments, final String tournamentsYear) {
    ContentValues[] values = new ContentValues[tournaments.size()];
    for (int i = 0; i < tournaments.size(); i++) {
      values[i] = toContentValues(tournaments.get(i), tournamentsYear);
    }

    return values;
  }

  public static ContentValues[] getContentValues(List<BeachRoundList> roundLists) {
    ArrayList<ContentValues> contentValues = new ArrayList<>();
    for (BeachRoundList roundList : roundLists) {
      if( roundList != null && roundList.getRounds() != null ) {
        for (BeachRound round : roundList.getRounds()) {
          // Only Main draw matches
          if (round.getPhase() == 4) {
            contentValues.add(toContentValues(round));
          }
        }
      }
    }
    return contentValues.toArray(new ContentValues[contentValues.size()]);
  }

  public static ContentValues[] getContentValuesMatches(List<MatchList> matchLists) {
    ArrayList<ContentValues> contentValues = new ArrayList<>();
    for (MatchList matchList : matchLists) {
      if( matchList != null && matchList.getMatches() != null) {
        for (TournamentMatch match : matchList.getMatches()) {
          contentValues.add(toContentValues(match));
        }
      }
    }
    return contentValues.toArray(new ContentValues[contentValues.size()]);
  }

  private static ContentValues toContentValues(TournamentMatch match) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_COURT, match.getCourt());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_MATCH, match.getMatchNumber());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET1A, match.getPointsTeamASet1());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET1B, match.getPointsTeamBSet1());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET2A, match.getPointsTeamASet2());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET2B, match.getPointsTeamBSet2());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET3A, match.getPointsTeamASet3());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET3B, match.getPointsTeamBSet3());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_POSITION_DRAWA, match.getTeamAPositionInMainDraw());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_POSITION_DRAWB, match.getTeamBPositionInMainDraw());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_RESULT_TYPE, match.getResultType());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_ROUND, match.getRound());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMA, match.getTeamA());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMA_FEDARATION, match.getTeamAFederationCode());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMA_NAME, match.getTeamAName());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMB, match.getTeamB());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMB_FEDERATION, match.getTeamBFederationCode());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMB_NAME, match.getTeamBName());
    contentValues.put(BeachMatchContract.BeachMatchEntry.COLUMN_TOURNAMENT, match.getNumberTournament());
    return contentValues;
  }

  private static ContentValues toContentValues(BeachTournament beachTournament, String tournamentsYear) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_NAME, beachTournament.getName());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_TITLE, beachTournament.getTile());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_COUNTRY, beachTournament.getCountry());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_START_DATE, beachTournament.getStartDate().getTime());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_END_DATE, beachTournament.getEndDate().getTime());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_GENDER, beachTournament.getGender());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_TYPE, beachTournament.getType());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_TOURNAMENT_CODE, beachTournament.getTournamentCode());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_OTHER_GENDER_TOURNAMENT_CODE,
        beachTournament.getOtherGenderTournamentCode());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_NUMBER, beachTournament.getNumber());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_EVENT_NUMBER, beachTournament.getEventNumber());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_YEAR, Integer.valueOf(tournamentsYear));
    return contentValues;
  }


  private static ContentValues toContentValues(BeachRound round) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(BeachRoundContract.BeachRoundsEntry.COLUMN_TOURNAMENT, round.getNumberTournament());
    contentValues.put(BeachRoundContract.BeachRoundsEntry.COLUMN_START_DATE, round.getStartDate().getTime());
    contentValues.put(BeachRoundContract.BeachRoundsEntry.COLUMN_NAME, round.getName());
    contentValues.put(BeachRoundContract.BeachRoundsEntry.COLUMN_NUMBER, round.getNumber());
    contentValues.put(BeachRoundContract.BeachRoundsEntry.COLUMN_PHASE, round.getPhase());
    return contentValues;
  }

}
