package br.bosseur.beachvolleytour.utils;

import android.content.ContentValues;

import java.util.ArrayList;

import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;
import br.bosseur.beachvolleytour.model.BeachTournament;

public class ContentValueUtils {


  public static ContentValues[] getContentValues(final ArrayList<BeachTournament> tournaments, final String tournamentsYear) {
    ContentValues[] values = new ContentValues[tournaments.size()];
    for (int i = 0; i < tournaments.size(); i++) {
      values[i] = toContentValues(tournaments.get(i), tournamentsYear);
    }

    return values;
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
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_OTHER_GENDER_TOURNAMENT_CODE, beachTournament.getOtherGenderTournamentCode());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_NUMBER, beachTournament.getNumber());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_EVENT_NUMBER, beachTournament.getEventNumber());
    contentValues.put(TournamentsContract.TournamentsEntry.COLUMN_YEAR, Integer.valueOf(tournamentsYear));
    return contentValues;
  }
}
