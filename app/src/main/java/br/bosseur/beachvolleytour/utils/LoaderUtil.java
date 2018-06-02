package br.bosseur.beachvolleytour.utils;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

public class LoaderUtil {

  public static void startLoader(final Bundle bundle,
      final LoaderManager supportLoaderManager,
      final int idLoader,
      final LoaderManager.LoaderCallbacks<Cursor> roundsLoaderCallBack) {
    Loader loader = supportLoaderManager.getLoader(idLoader);
    if (loader == null) {
      supportLoaderManager.initLoader(idLoader, bundle, roundsLoaderCallBack);
    } else {
      supportLoaderManager.restartLoader(idLoader, bundle, roundsLoaderCallBack);
    }
  }
}
