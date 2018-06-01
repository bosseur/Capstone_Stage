package br.bosseur.beachvolleytour.services;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;

import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.model.TournamentsResponses;
import br.bosseur.beachvolleytour.remote.FivbNetworkService;
import br.bosseur.beachvolleytour.remote.FivbRequestType;
import br.bosseur.beachvolleytour.utils.ContentValueUtils;
import retrofit2.Response;
import timber.log.Timber;

public class FivbSyncTasks {

  public synchronized static void syncTournaments(Context context, String tournamentsYear) {
    try {
      Response<TournamentsResponses> response = FivbNetworkService.requestTournaments(FivbRequestType.TOURNAMENMTS.getRequestQuery(tournamentsYear));

      if (response != null && response.body() != null) {
        ArrayList<BeachTournament> tournaments = new ArrayList<>(response.body().getEventList());
        tournaments = (ArrayList<BeachTournament>) BeachTournament.disintEvent(tournaments);

        ContentValues[] values = ContentValueUtils.getContentValues(tournaments, tournamentsYear);
        ContentResolver contentResolver = context.getContentResolver();

        String selection = TournamentsContract.TournamentsEntry.COLUMN_YEAR + " = ? ";
        String selectionArgs[] = new String[]{tournamentsYear};

        contentResolver.delete(TournamentsContract.TournamentsEntry.CONTENT_URI, selection, selectionArgs);
        contentResolver.bulkInsert(TournamentsContract.TournamentsEntry.CONTENT_URI, values);

      }
    } catch (IOException e) {
      Timber.e(e);
    }
  }

}
