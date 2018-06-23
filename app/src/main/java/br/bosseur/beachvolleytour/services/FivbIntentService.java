package br.bosseur.beachvolleytour.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.io.IOException;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.exceptions.NoMatchesAvailableException;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.remote.FivbRequestType;
import timber.log.Timber;

public class FivbIntentService extends IntentService {

  private static final String YEAR_PARAM = "YEAR_PARAM";
  private static final String MATCH_PARAM = "MATCH_PARAM";

  public FivbIntentService() {
    super(FivbIntentService.class.getSimpleName());
  }

  public static void startFivbService(Context context, String parameter) {
    Intent intent = new Intent(context, FivbIntentService.class);
    intent.setAction(FivbRequestType.TOURNAMENMTS.name());
    intent.putExtra(YEAR_PARAM, parameter);
    context.startService(intent);
  }

  public static void startFivbMatchService(Context context, BeachTournament tournament) {
    Intent intent = new Intent(context, FivbIntentService.class);
    intent.setAction(FivbRequestType.MATCHES.name());
    intent.putExtra(MATCH_PARAM, tournament);
    context.startService(intent);
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {

    if (intent != null && intent.getAction() != null) {
      FivbRequestType requestType = FivbRequestType.valueOf(intent.getAction());
      try {
        switch (requestType) {
          case TOURNAMENMTS:
            String yearTournaments = intent.getStringExtra(YEAR_PARAM);
            FivbSyncTasks.syncTournaments(this, yearTournaments);
            break;
          case MATCHES:
            BeachTournament tournament = intent.getParcelableExtra(MATCH_PARAM);
            FivbSyncTasks.syncMatches(this, tournament);
            break;
          default:
            Timber.d(getString(R.string.undefined_request));
        }
      } catch (Exception ex) {
        Timber.e(ex);
        // Send a broadcast so that appropriate action can ben taken and user can be informed.
        Intent broadCastIntent = new Intent(getString(R.string.broadcast_request_error));
        String message = extractMessage(ex);
        broadCastIntent.putExtra(getString(R.string.broadcast_exception_message_key), message);
        sendBroadcast(broadCastIntent);
      }

    }

  }

  private String extractMessage(Exception ex) {
    if (ex != null) {
      if (ex instanceof IOException) {
        return getString(R.string.network_error);
      }
      if(NoMatchesAvailableException.class.equals(ex.getClass())) {
        return getString(R.string.no_match_data_available);
      }
      // An error occurred parsing the data received from FIVB.
      return getString(R.string.error_parsing_data);
    }
    return getString(R.string.unknown_error);
  }


}
