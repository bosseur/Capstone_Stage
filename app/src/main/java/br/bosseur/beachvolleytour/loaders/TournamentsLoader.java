package br.bosseur.beachvolleytour.loaders;

import static br.bosseur.beachvolleytour.ui.fragments.BeachTournamentsFragment.YEAR_PARAM;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.List;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;
import br.bosseur.beachvolleytour.listeners.FivbCallBack;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.utils.CursorParser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TournamentsLoader implements LoaderManager.LoaderCallbacks<Cursor> {
  private final FivbCallBack mCallBack;
  private final Context mContext;

  @NonNull
  @Override
  public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {

    String selectionYear = bundle.getString(YEAR_PARAM);
    Uri tournamentsUri = TournamentsContract.TournamentsEntry.CONTENT_URI
        .buildUpon()
        .appendPath(selectionYear)
        .build();
    /* Sort order: Ascending by start date */
    String sortOrder = TournamentsContract.TournamentsEntry.COLUMN_START_DATE + " ASC";

    return new CursorLoader(mContext,
        tournamentsUri,
        null,
        null,
        null,
        sortOrder);

  }

  @Override
  public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
    if (data != null && data.getCount() > 0) {
      List<BeachTournament> tournaments = CursorParser.parseTournaments(data);
      mCallBack.success(tournaments);
    } else {
      mCallBack.error(mContext.getString(R.string.message_no_tournaments_found));
    }
  }

  @Override
  public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    mCallBack.success(null);
  }
}
