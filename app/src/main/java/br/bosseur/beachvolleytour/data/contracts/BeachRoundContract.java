package br.bosseur.beachvolleytour.data.contracts;

import static android.provider.BaseColumns._ID;

import static br.bosseur.beachvolleytour.data.contracts.BeachRoundContract.BeachRoundsEntry.COLUMN_NAME;
import static br.bosseur.beachvolleytour.data.contracts.BeachRoundContract.BeachRoundsEntry.COLUMN_NUMBER;
import static br.bosseur.beachvolleytour.data.contracts.BeachRoundContract.BeachRoundsEntry.COLUMN_PHASE;
import static br.bosseur.beachvolleytour.data.contracts.BeachRoundContract.BeachRoundsEntry.COLUMN_START_DATE;
import static br.bosseur.beachvolleytour.data.contracts.BeachRoundContract.BeachRoundsEntry.COLUMN_TOURNAMENT;
import static br.bosseur.beachvolleytour.data.contracts.BeachRoundContract.BeachRoundsEntry.TABLE_NAME;

import android.net.Uri;

public class BeachRoundContract extends BaseContract {
  public static final String PATH = "rounds";

  public static final String CREATE_TABLE_SQL = " CREATE TABLE " + TABLE_NAME + " ( " +
      _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
      COLUMN_NUMBER + " INTEGER KEY, " +
      COLUMN_START_DATE + " INTEGER NOT NULL, " +
      COLUMN_TOURNAMENT + " TEXT NOT NULL, " +
      COLUMN_NAME + " TEXT NOT NULL, " +
      COLUMN_PHASE + " INTEGER NOT NULL " +
      ");";

  public static final class BeachRoundsEntry implements android.provider.BaseColumns {

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
        .appendPath(PATH)
        .build();

    public static final String TABLE_NAME = "rounds";

    public static String COLUMN_TOURNAMENT = "tournament";
    public static String COLUMN_START_DATE = "start_date";
    public static String COLUMN_NAME = "name";
    public static String COLUMN_PHASE = "phase";
    public static String COLUMN_NUMBER = "number";


  }
}
