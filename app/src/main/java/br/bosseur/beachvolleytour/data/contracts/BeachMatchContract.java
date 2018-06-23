package br.bosseur.beachvolleytour.data.contracts;

import static android.provider.BaseColumns._ID;

import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_COURT;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_MATCH;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET1A;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET1B;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET2A;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET2B;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET3A;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_POINT_SET3B;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_POSITION_DRAWA;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_POSITION_DRAWB;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_RESULT_TYPE;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_ROUND;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_TEAMA;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_TEAMA_FEDARATION;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_TEAMA_NAME;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_TEAMB;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_TEAMB_FEDERATION;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_TEAMB_NAME;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.COLUMN_TOURNAMENT;
import static br.bosseur.beachvolleytour.data.contracts.BeachMatchContract.BeachMatchEntry.TABLE_NAME;

import android.net.Uri;

public class BeachMatchContract extends BaseContract {
  public static final String PATH = "matches";

  public static final String CREATE_TABLE_SQL = " CREATE TABLE " + TABLE_NAME + " ( " +
      _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
      COLUMN_MATCH + " INTEGER KEY , " +
      COLUMN_TOURNAMENT  + " TEXT KEY , " +
      COLUMN_ROUND  + " INTEGER KEY , " +
      COLUMN_TEAMA + " TEXT KEY , " +
      COLUMN_TEAMB + " TEXT KEY , " +
      COLUMN_TEAMA_NAME + " TEXT KEY , " +
      COLUMN_TEAMB_NAME + " TEXT KEY , " +
      COLUMN_COURT + " TEXT KEY , " +
      COLUMN_TEAMA_FEDARATION + " TEXT KEY , " +
      COLUMN_TEAMB_FEDERATION + " TEXT KEY , " +
      COLUMN_POINT_SET1A + " TEXT KEY , " +
      COLUMN_POINT_SET1B + " TEXT KEY , " +
      COLUMN_POINT_SET2A + " TEXT KEY , " +
      COLUMN_POINT_SET2B + " TEXT KEY , " +
      COLUMN_POINT_SET3A + " TEXT KEY , " +
      COLUMN_POINT_SET3B + " TEXT KEY , " +
      COLUMN_RESULT_TYPE  + " INTEGER KEY , " +
      COLUMN_POSITION_DRAWA + " TEXT KEY , " +
      COLUMN_POSITION_DRAWB + " TEXT KEY " +
      ");";

  public static final class BeachMatchEntry implements android.provider.BaseColumns {
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
        .appendPath(PATH)
        .build();

    public static final String TABLE_NAME = "matches";

    public static final String COLUMN_TOURNAMENT = "tournament";
    public static final String COLUMN_ROUND = "round";
    public static final String COLUMN_MATCH = "match";
    public static final String COLUMN_TEAMA = "teama";
    public static final String COLUMN_TEAMB = "teamb";
    public static final String COLUMN_TEAMA_NAME = "teama_name";
    public static final String COLUMN_TEAMB_NAME = "teamb_name";
    public static final String COLUMN_COURT = "court";
    public static final String COLUMN_TEAMA_FEDARATION = "teama_fedeartion";
    public static final String COLUMN_TEAMB_FEDERATION = "teamb_fedeartion";
    public static final String COLUMN_POINT_SET1A = "points_set1a";
    public static final String COLUMN_POINT_SET1B = "points_set1b";
    public static final String COLUMN_POINT_SET2A = "points_set2a";
    public static final String COLUMN_POINT_SET2B = "points_set2b";
    public static final String COLUMN_POINT_SET3A = "points_set3a";
    public static final String COLUMN_POINT_SET3B = "points_set3b";
    public static final String COLUMN_RESULT_TYPE = "result_type";
    public static final String COLUMN_POSITION_DRAWA = "postion_drawa";
    public static final String COLUMN_POSITION_DRAWB = "postion_drawb";

  }
}
