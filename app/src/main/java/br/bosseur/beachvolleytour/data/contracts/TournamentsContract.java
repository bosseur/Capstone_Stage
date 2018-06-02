package br.bosseur.beachvolleytour.data.contracts;

import static android.provider.BaseColumns._ID;

import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_COUNTRY;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_END_DATE;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_EVENT_NUMBER;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_GENDER;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_NAME;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_NUMBER;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_OTHER_GENDER_TOURNAMENT_CODE;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_START_DATE;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_TITLE;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_TOURNAMENT_CODE;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_TYPE;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.COLUMN_YEAR;
import static br.bosseur.beachvolleytour.data.contracts.TournamentsContract.TournamentsEntry.TABLE_NAME;

import android.net.Uri;

public class TournamentsContract extends BaseContract {

  public static final String PATH = "tournaments";

  public static final String CREATE_TABLE_SQL = " CREATE TABLE " + TABLE_NAME +
      " ( " +
      _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
      COLUMN_NAME + " TEXT NOT NULL, " +
      COLUMN_TITLE + " TEXT NOT NULL, " +
      COLUMN_COUNTRY + " TEXT NOT NULL, " +
      COLUMN_START_DATE + " INTEGER NOT NULL, " +
      COLUMN_END_DATE + " INTEGER NOT NULL, " +
      COLUMN_GENDER + " INTEGER NOT NULL, " +
      COLUMN_TYPE + " INTEGER NOT NULL, " +
      COLUMN_TOURNAMENT_CODE + " TEXT NOT NULL, " +
      COLUMN_OTHER_GENDER_TOURNAMENT_CODE + " TEXT NULL, " +
      COLUMN_NUMBER + " TEXT NOT NULL, " +
      COLUMN_EVENT_NUMBER + " TEXT NOT NULL, " +
      COLUMN_YEAR + " INTEGER NOT NULL " +
      ");";

  public static final class TournamentsEntry implements android.provider.BaseColumns {

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
        .appendPath(PATH)
        .build();

    public static final String TABLE_NAME = "tournaments";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_START_DATE = "startdate";
    public static final String COLUMN_END_DATE = "enddate";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TOURNAMENT_CODE = "tournament_code";
    public static final String COLUMN_OTHER_GENDER_TOURNAMENT_CODE = "other_gender_tournament_code";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_EVENT_NUMBER = "event_number";
    public static final String COLUMN_YEAR = "event_year";

  }
}
