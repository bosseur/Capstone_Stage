package br.bosseur.beachvolleytour.loaders;

import static br.bosseur.beachvolleytour.ui.fragments.MatchesFragment.TOURNAMENT_PARAM;

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

import br.bosseur.beachvolleytour.data.contracts.BeachMatchContract;
import br.bosseur.beachvolleytour.listeners.MatchesLoaderCallBack;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.model.Tournament;
import br.bosseur.beachvolleytour.model.TournamentMatch;
import br.bosseur.beachvolleytour.utils.CursorParserUtil;

public class MatchesLoader implements LoaderManager.LoaderCallbacks<Cursor> {
  private final MatchesLoaderCallBack mCallBack;
  private final Context mContext;

  private BeachTournament mTournament;

  public MatchesLoader(MatchesLoaderCallBack callBack, Context context) {
    mCallBack = callBack;
    mContext = context;
  }

  @NonNull
  @Override
  public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
    if( bundle == null || !bundle.containsKey(TOURNAMENT_PARAM)){
      return null;
    }
    mTournament = bundle.getParcelable(TOURNAMENT_PARAM);
    String selection = createSelection();
    String[] selectionArgs = LoaderHelper.createSelectionArgs(mTournament);
    String orderBy = BeachMatchContract.BeachMatchEntry._ID + " DESC";
    Uri roundsUri = BeachMatchContract.BeachMatchEntry.CONTENT_URI;

    return new CursorLoader(mContext,
        roundsUri,
        null,
        selection,
        selectionArgs,
        orderBy);
  }

  @Override
  public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
    if (data != null && data.getCount() > 0) {
      List<TournamentMatch> matches = CursorParserUtil.parseMatches(data);
      mCallBack.matchesLoaded(matches);
    }
  }

  @Override
  public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    mCallBack.matchesLoaded(null);
  }

  private String createSelection() {

    if (mTournament.getTournamentCode() != null && mTournament.getOtherGenderTournamentCode() != null) {
      return BeachMatchContract.BeachMatchEntry.COLUMN_TOURNAMENT + " IN (?,?)";
    } else {
      return BeachMatchContract.BeachMatchEntry.COLUMN_TOURNAMENT + "=?";
    }
  }

}
