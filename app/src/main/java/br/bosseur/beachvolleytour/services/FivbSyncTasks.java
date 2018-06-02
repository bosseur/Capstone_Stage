package br.bosseur.beachvolleytour.services;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.io.IOException;
import java.util.List;

import br.bosseur.beachvolleytour.data.contracts.BeachMatchContract;
import br.bosseur.beachvolleytour.data.contracts.BeachRoundContract;
import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;
import br.bosseur.beachvolleytour.exceptions.NoMatchesAvailableException;
import br.bosseur.beachvolleytour.model.BeachRoundList;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.model.MatchList;
import br.bosseur.beachvolleytour.model.MatchesResponse;
import br.bosseur.beachvolleytour.model.TournamentsResponses;
import br.bosseur.beachvolleytour.remote.FivbNetworkService;
import br.bosseur.beachvolleytour.remote.FivbRequestType;
import br.bosseur.beachvolleytour.utils.ContentValueUtils;
import retrofit2.Response;
import timber.log.Timber;

public class FivbSyncTasks {

  public synchronized static void syncTournaments(Context context, String tournamentsYear) throws Exception {
    Response<TournamentsResponses> response = null;
    try {
      String requestQuery = FivbRequestType.TOURNAMENMTS.getRequestQuery(tournamentsYear);
      Timber.d(requestQuery);
      response = FivbNetworkService.requestTournaments(requestQuery);

      if (response.body() != null) {
        List<BeachTournament> tournaments = response.body().getEventList();
        tournaments = BeachTournament.disintEvent(tournaments);

        saveTournaments(context, tournamentsYear, tournaments);

      }
    } catch (IOException ste) {
      if (response != null && response.raw().body() != null ) {
        response.raw().body().close();
      }
      throw ste;
    } catch (Exception e) {
      Timber.e(e);
      throw e;
    }
  }

  public static void syncMatches(Context context, BeachTournament tournament) throws Exception {
    Response<MatchesResponse> response = null;
    try {
      String requestQuery = FivbRequestType.MATCHES.getRequestQuery(tournament);
      Timber.d(requestQuery);
      response = FivbNetworkService.requestMatches(requestQuery);
      if (response.body() != null) {
        MatchesResponse body = response.body();
        List<BeachRoundList> roundLists = body.getRounds();

        ContentResolver contentResolver = context.getContentResolver();
        saveRounds(roundLists, contentResolver);

        List<MatchList> matchLists = body.getMatches();
        saveMatches(matchLists, contentResolver);
      }
    } catch (IOException ste) {
      if (response != null && response.raw().body() != null ) {
        response.raw().body().close();
      }
      throw ste;
    } catch (Exception e) {
      Timber.e(e);
      throw e;
    }
  }

  private static void saveMatches(List<MatchList> matchLists, ContentResolver contentResolver) {
    ContentValues[] values = ContentValueUtils.getContentValuesMatches(matchLists);

    String args[] = new String[matchLists.size()];
    String selectionArgs[] = new String[matchLists.size()];
    for (int i = 0; i < selectionArgs.length; i++) {
      selectionArgs[i] = matchLists.get(i).getTournament();
      args[i] = "?";
    }

    String selection = BeachMatchContract.BeachMatchEntry.COLUMN_TOURNAMENT + " IN (" + TextUtils.join(",", args) + ")";

    Uri contentUri = BeachMatchContract.BeachMatchEntry.CONTENT_URI;
    contentResolver.delete(contentUri, selection, selectionArgs);
    contentResolver.bulkInsert(contentUri, values);
  }

  private static void saveRounds(List<BeachRoundList> roundLists, ContentResolver contentResolver) throws Exception {
    ContentValues[] values = ContentValueUtils.getContentValues(roundLists);

    if (values.length == 0){
      throw new NoMatchesAvailableException();
    }

    String args[] = new String[roundLists.size()];
    String selectionArgs[] = new String[roundLists.size()];
    for (int i = 0; i < selectionArgs.length; i++) {
      selectionArgs[i] = roundLists.get(i).getTournament();
      args[i] = "?";
    }

    String selection = BeachRoundContract.BeachRoundsEntry.COLUMN_TOURNAMENT + " IN (" + TextUtils.join(",", args) + ")";

    Uri contentUri = BeachRoundContract.BeachRoundsEntry.CONTENT_URI;
    contentResolver.delete(contentUri, selection, selectionArgs);
    contentResolver.bulkInsert(contentUri, values);
  }


  private static void saveTournaments(Context context, String tournamentsYear, List<BeachTournament> tournaments) {
    ContentValues[] values = ContentValueUtils.getContentValues(tournaments, tournamentsYear);
    ContentResolver contentResolver = context.getContentResolver();

    String selection = TournamentsContract.TournamentsEntry.COLUMN_YEAR + " = ? ";
    String selectionArgs[] = new String[]{tournamentsYear};

    Uri contentUri = TournamentsContract.TournamentsEntry.CONTENT_URI;
    contentResolver.delete(contentUri, selection, selectionArgs);
    contentResolver.bulkInsert(contentUri, values);
  }
}
