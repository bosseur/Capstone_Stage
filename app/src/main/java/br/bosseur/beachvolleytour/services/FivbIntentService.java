package br.bosseur.beachvolleytour.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import br.bosseur.beachvolleytour.remote.FivbRequestType;
import timber.log.Timber;

public class FivbIntentService extends IntentService {

  private static final String YEAR_PARAM = "YEAR_PARAM";
  private static final String RECEIVER_PARAM = "RECEIVER_PARAM";

  public FivbIntentService() {
    super(FivbIntentService.class.getSimpleName());
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {

    if (intent != null && intent.getAction() != null) {
      FivbRequestType requestType = FivbRequestType.valueOf(intent.getAction());

      switch (requestType) {
        case TOURNAMENMTS:
          String requestParameter = intent.getStringExtra(YEAR_PARAM);
          FivbSyncTasks.syncTournaments(this, requestParameter);
          break;
        default:
          Timber.d("Undefined request type.");
      }

    }

  }

  public static void startFivbService(Context context, String parameter) {
    Intent intent = new Intent(context, FivbIntentService.class);
    intent.setAction(FivbRequestType.TOURNAMENMTS.name());
    intent.putExtra(YEAR_PARAM, parameter);
    context.startService(intent);
  }
}
