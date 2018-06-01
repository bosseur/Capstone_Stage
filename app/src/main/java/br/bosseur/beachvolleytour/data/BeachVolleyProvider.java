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
import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;

public class BeachVolleyProvider extends ContentProvider {

  public static final int CODE_TOURNAMENTS = 100;

  public static final int CODE_TOURNAMENTS_WITH_YEAR = 101;

  private static final UriMatcher sURI_MATCHER = buildUriMatcher();

  private DbHelper mDbHelper;

  private static UriMatcher buildUriMatcher() {

    final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final String authority = BaseContract.CONTENT_AUTHORITY;

    matcher.addURI(authority, TournamentsContract.PATH, CODE_TOURNAMENTS);
    matcher.addURI(authority, TournamentsContract.PATH + "/#", CODE_TOURNAMENTS_WITH_YEAR);

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

    switch (sURI_MATCHER.match(uri)) {
      case CODE_TOURNAMENTS:
        db.beginTransaction();
        int rowsInserted = 0;
        try {
          for (ContentValues value : values) {

            long _id = db.insert(TournamentsContract.TournamentsEntry.TABLE_NAME, null, value);
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
      default:
        return super.bulkInsert(uri, values);
    }

  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
      @Nullable String sortOrder) {

    int match = sURI_MATCHER.match(uri);

    Cursor cursor;
    SQLiteDatabase db;

    switch (match) {
      case CODE_TOURNAMENTS:
        db = mDbHelper.getReadableDatabase();
        cursor = db.query(TournamentsContract.TournamentsEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder);
        break;
      case CODE_TOURNAMENTS_WITH_YEAR:
        db = mDbHelper.getReadableDatabase();
        selection = TournamentsContract.TournamentsEntry.COLUMN_YEAR + "=?";
        selectionArgs = new String[]{uri.getLastPathSegment()};
        cursor = db.query(TournamentsContract.TournamentsEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder);
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

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
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    SQLiteDatabase db = mDbHelper.getReadableDatabase();

    int rowsDeleted = db.delete(tableName, null, null);

    if (rowsDeleted > 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }

    return rowsDeleted;

  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }
}
