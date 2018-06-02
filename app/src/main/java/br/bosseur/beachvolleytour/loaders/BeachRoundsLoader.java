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

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.data.contracts.BeachRoundContract;
import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;
import br.bosseur.beachvolleytour.listeners.FivbCallBack;
import br.bosseur.beachvolleytour.model.BeachRound;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.services.FivbIntentService;
import br.bosseur.beachvolleytour.utils.CursorParserUtil;
import br.bosseur.beachvolleytour.utils.NetworkUtil;
import lombok.RequiredArgsConstructor;


public class BeachRoundsLoader implements LoaderManager.LoaderCallbacks<Cursor> {
  private FivbCallBack<List<BeachRound>> mCallBack;
  private Context mContext;
  private BeachTournament mTournament;
  private int tries = 0;


  public BeachRoundsLoader(
      FivbCallBack<List<BeachRound>> callBack, Context context) {
    mCallBack = callBack;
    mContext = context;
  }

  @NonNull
  @Override
  public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
    tries++;
    mTournament = bundle.getParcelable(TOURNAMENT_PARAM);
    String selection = createSelection();
    String[] selectionArgs =LoaderHelper.createSelectionArgs(mTournament);
    String orderBy = BeachRoundContract.BeachRoundsEntry.COLUMN_START_DATE + " DESC, "
        + BeachRoundContract.BeachRoundsEntry._ID + " DESC";
    Uri roundsUri = BeachRoundContract.BeachRoundsEntry.CONTENT_URI;

    return new CursorLoader(mContext,
        roundsUri,
        null,
        selection,
        selectionArgs,
        orderBy);

  }

  @Override
  public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
    tries ++;
    if (data != null && data.getCount() > 0) {
      List<BeachRound> rounds = CursorParserUtil.parseRounds(data);
      mCallBack.success(rounds);
      tries = 0;
    } else {
      if (NetworkUtil.isOnline(mContext) && tries < mContext.getResources().getInteger(R.integer.min_tries) ) {
        FivbIntentService.startFivbMatchService(mContext, mTournament);
      } else {
        tries = 0;
        mCallBack.error(mContext.getString(R.string.network_unavailable));
      }
    }
  }

  @Override
  public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    mCallBack.success(null);
  }

  private String createSelection() {

    if (mTournament.getTournamentCode() != null && mTournament.getOtherGenderTournamentCode() != null) {
      return BeachRoundContract.BeachRoundsEntry.COLUMN_TOURNAMENT + " IN (?,?)";
    } else {
      return BeachRoundContract.BeachRoundsEntry.COLUMN_TOURNAMENT + "=?";
    }
  }

}
