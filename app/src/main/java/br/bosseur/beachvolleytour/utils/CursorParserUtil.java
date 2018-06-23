package br.bosseur.beachvolleytour.utils;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.bosseur.beachvolleytour.data.contracts.BeachMatchContract;
import br.bosseur.beachvolleytour.data.contracts.BeachRoundContract;
import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;
import br.bosseur.beachvolleytour.model.BeachRound;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.model.TournamentMatch;

public class CursorParserUtil {
  public static List<BeachTournament> parseTournaments(Cursor data) {
    List<BeachTournament> tournaments = new ArrayList<>(data.getCount());
    data.moveToFirst();

    do {
      BeachTournament tournament = parseTournament(data);
      tournaments.add(tournament);
    } while (data.moveToNext());

    return tournaments;

  }

  public static BeachTournament parseTournament(Cursor data) {
    Date startDate = new Date(data.getLong(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_START_DATE)));
    Date endDate = new Date(data.getLong(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_END_DATE)));
    return BeachTournament.builder()
        .name(data.getString(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_NAME)))
        .tile(data.getString(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_TITLE)))
        .country(data.getString(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_COUNTRY)))
        .startDate(startDate)
        .endDate(endDate)
        .type(data.getInt(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_TYPE)))
        .gender(data.getInt(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_GENDER)))
        .tournamentCode(data.getString(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_TOURNAMENT_CODE)))
        .otherGenderTournamentCode(
            data.getString(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_OTHER_GENDER_TOURNAMENT_CODE)))
        .eventNumber(data.getInt(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_EVENT_NUMBER)))
        .number(data.getInt(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_NUMBER)))
        .build();
  }

  public static List<BeachRound> parseRounds(Cursor data) {
    List<BeachRound> rounds = new ArrayList<>(data.getCount());
    data.moveToFirst();

    do {
      BeachRound beachRound = BeachRound.builder()
          .name(data.getString(data.getColumnIndex(BeachRoundContract.BeachRoundsEntry.COLUMN_NAME)))
          .startDate(new Date(data.getLong(data.getColumnIndex(BeachRoundContract.BeachRoundsEntry.COLUMN_START_DATE))))
          .numberTournament(data.getString(data.getColumnIndex(BeachRoundContract.BeachRoundsEntry.COLUMN_TOURNAMENT)))
          .phase(data.getInt(data.getColumnIndex(BeachRoundContract.BeachRoundsEntry.COLUMN_PHASE)))
          .number(data.getInt(data.getColumnIndex(BeachRoundContract.BeachRoundsEntry.COLUMN_NUMBER)))
          .build();
      rounds.add(beachRound);
    } while (data.moveToNext());

    return rounds;
  }

  public static List<TournamentMatch> parseMatches(Cursor data) {
    List<TournamentMatch> matches = new ArrayList<>(data.getCount());
    data.moveToFirst();

    do {
      TournamentMatch match = TournamentMatch.builder()
          .court(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_COURT)))
          .matchNumber(data.getInt(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_MATCH)))
          .pointsTeamASet1(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET1A)))
          .pointsTeamASet2(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET2A)))
          .pointsTeamASet3(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET3A)))
          .pointsTeamBSet1(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET1B)))
          .pointsTeamBSet2(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET2B)))
          .pointsTeamBSet3(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET3B)))
          .teamAPositionInMainDraw(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_POSITION_DRAWA)))
          .teamBPositionInMainDraw(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_POSITION_DRAWB)))
          .resultType(data.getInt(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_RESULT_TYPE)))
          .round(data.getInt(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_ROUND)))
          .teamA(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMA)))
          .teamAFederationCode(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMA_FEDARATION)))
          .teamAName(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMA_NAME)))
          .teamB(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMB)))
          .teamBFederationCode(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMB_FEDERATION)))
          .teamBName(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_TEAMB_NAME)))
          .numberTournament(data.getString(data.getColumnIndex(BeachMatchContract.BeachMatchEntry.COLUMN_TOURNAMENT)))
          .build();
      matches.add(match);
    } while (data.moveToNext());

    return matches;
  }

}
