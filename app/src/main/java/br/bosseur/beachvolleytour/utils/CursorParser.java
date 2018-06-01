package br.bosseur.beachvolleytour.utils;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;
import br.bosseur.beachvolleytour.model.BeachTournament;

public class CursorParser {
  public static List<BeachTournament> parseTournaments(Cursor data) {
    List<BeachTournament> tournaments = new ArrayList<>();
    data.moveToFirst();

    do {
      Date startDate = new Date(data.getLong(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_START_DATE)));
      Date endDate = new Date(data.getLong(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_END_DATE)));
      BeachTournament tournament = BeachTournament.builder()
          .name(data.getString(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_NAME)))
          .tile(data.getString(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_TITLE)))
          .country(data.getString(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_COUNTRY)))
          .startDate(startDate)
          .endDate(endDate)
          .type(data.getInt(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_TYPE)))
          .gender(data.getInt(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_GENDER)))
          .tournamentCode(data.getString(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_TOURNAMENT_CODE)))
          .otherGenderTournamentCode(data.getString(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_OTHER_GENDER_TOURNAMENT_CODE)))
          .eventNumber(data.getInt(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_EVENT_NUMBER)))
          .number(data.getInt(data.getColumnIndex(TournamentsContract.TournamentsEntry.COLUMN_NUMBER)))
          .build();
      tournaments.add(tournament);
    }while (data.moveToNext());

    return tournaments;

  }
}
