package br.bosseur.beachvolleytour.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import br.bosseur.beachvolleytour.data.contracts.BaseContract;
import br.bosseur.beachvolleytour.data.contracts.BeachMatchContract;
import br.bosseur.beachvolleytour.data.contracts.BeachRoundContract;
import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;
import timber.log.Timber;

public class BeachVolleyProvider extends ContentProvider {

  public static final int CODE_TOURNAMENTS = 100;
  public static final int CODE_TOURNAMENTS_WITH_YEAR = 101;

  public static final int CODE_ROUNDS = 200;
  public static final int CODE_ROUNDS_WITH_TOURNAMENT = 201;

  public static final int CODE_MATCHES = 300;

  private static final UriMatcher sURI_MATCHER = buildUriMatcher();

  private DbHelper mDbHelper;

  private static UriMatcher buildUriMatcher() {

    final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = BaseContract.CONTENT_AUTHORITY;

    matcher.addURI(authority, TournamentsContract.PATH, CODE_TOURNAMENTS);
    matcher.addURI(authority, TournamentsContract.PATH + "/#", CODE_TOURNAMENTS_WITH_YEAR);

    matcher.addURI(authority, BeachRoundContract.PATH, CODE_ROUNDS);
    matcher.addURI(authority, BeachRoundContract.PATH + "/#", CODE_ROUNDS_WITH_TOURNAMENT);

    matcher.addURI(authority, BeachMatchContract.PATH, CODE_MATCHES);

    return matcher;
  }

  @Override
  public boolean onCreate() {
    mDbHelper = new DbHelper(getContext());
    return true;
  }

  @Override
  public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
    final SQLiteDatabase db = mDbHelper.getWritableDatabase();

    String tableName;
    switch (sURI_MATCHER.match(uri)) {
      case CODE_TOURNAMENTS:
        tableName = TournamentsContract.TournamentsEntry.TABLE_NAME;
        break;

      case CODE_ROUNDS:
        tableName = BeachRoundContract.BeachRoundsEntry.TABLE_NAME;
        break;

      case CODE_MATCHES:
        tableName = BeachMatchContract.BeachMatchEntry.TABLE_NAME;
        break;
      default:
        return super.bulkInsert(uri, values);
    }

    return insertRows(values, db, tableName, uri);

  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
      @Nullable String sortOrder) {

    int match = sURI_MATCHER.match(uri);

    Timber.d("Loading tournaments.");


    String tableName;
    switch (match) {
      case CODE_TOURNAMENTS:
        tableName = TournamentsContract.TournamentsEntry.TABLE_NAME;
        break;
      case CODE_TOURNAMENTS_WITH_YEAR:
        tableName = TournamentsContract.TournamentsEntry.TABLE_NAME;
        selection = TournamentsContract.TournamentsEntry.COLUMN_YEAR + "=?";
        selectionArgs = new String[]{uri.getLastPathSegment()};
        break;
      case CODE_ROUNDS:
        tableName = BeachRoundContract.BeachRoundsEntry.TABLE_NAME;
        break;
      case CODE_MATCHES:
        tableName = BeachMatchContract.BeachMatchEntry.TABLE_NAME;
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    SQLiteDatabase db = mDbHelper.getReadableDatabase();
    Cursor cursor = db.query(tableName,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder);

    // Set a notification URI on the Cursor and return that Cursor
    cursor.setNotificationUri(getContext().getContentResolver(), uri);

    return cursor;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    throw new UnsupportedOperationException("Not implemented");
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    return null;
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
    int match = sURI_MATCHER.match(uri);

    String tableName;
    switch (match) {
      case CODE_TOURNAMENTS:
        tableName = TournamentsContract.TournamentsEntry.TABLE_NAME;
        break;
      case CODE_ROUNDS:
        tableName = BeachRoundContract.BeachRoundsEntry.TABLE_NAME;
        break;
      case CODE_MATCHES:
        tableName = BeachMatchContract.BeachMatchEntry.TABLE_NAME;
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    SQLiteDatabase db = mDbHelper.getReadableDatabase();

    int rowsDeleted = db.delete(tableName, selection, selectionArgs);

    if (rowsDeleted > 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }

    return rowsDeleted;

  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }

  private int insertRows(@NonNull ContentValues[] values, SQLiteDatabase db, String tableName, Uri uri) {
    int rowsInserted = 0;
    db.beginTransaction();
    try {
      for (ContentValues value : values) {

        long _id = db.insert(tableName, null, value);
        if (_id != -1) {
          rowsInserted++;
        }
      }
      db.setTransactionSuccessful();
    } finally {
      db.endTransaction();
    }

    if (rowsInserted > 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }

    return rowsInserted;
  }
}
