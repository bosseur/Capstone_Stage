package br.bosseur.beachvolleytour.services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;

import java.util.Date;

import br.bosseur.beachvolleytour.data.contracts.TournamentsContract;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.utils.CursorParserUtil;
import br.bosseur.beachvolleytour.widget.VolleyTourWidget;

public class TournamentService extends IntentService {

  public TournamentService() {
    super(TournamentService.class.getSimpleName());
  }

  public static void startWidgetUpdate(Context context) {
    Intent intent = new Intent(context, TournamentService.class);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      context.startForegroundService(intent);
    } else {
      context.startService(intent);
    }
  }

  @Override
  public void onStart(@Nullable Intent intent, int startId) {
    super.onStart(intent, startId);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      startForeground(1, new Notification());
    }
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    BeachTournament latestTournament = loadLatestTournament();
    VolleyTourWidget.updateWidget(this, latestTournament);
  }

  private BeachTournament loadLatestTournament() {
    Uri tournamentURI = TournamentsContract.TournamentsEntry.CONTENT_URI;
    String selection = TournamentsContract.TournamentsEntry.COLUMN_END_DATE + " > ?";
    String[] selectionArgs = new String[]{String.valueOf(new Date().getTime())};
    String sortOrder = TournamentsContract.TournamentsEntry.COLUMN_START_DATE + " ASC";
    Cursor cursor = getContentResolver().query(tournamentURI, null, selection, selectionArgs, sortOrder);

    if (cursor != null) {
      if (cursor.getCount() > 0) {
        cursor.moveToFirst();
        return CursorParserUtil.parseTournament(cursor);
      }
      cursor.close();
    }
    return null;
  }
}
