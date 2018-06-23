package br.bosseur.beachvolleytour.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Date;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.model.BeachTournament;
import br.bosseur.beachvolleytour.services.TournamentService;
import br.bosseur.beachvolleytour.ui.BeachTournamentsActivity;
import br.bosseur.beachvolleytour.utils.DateUtil;
import timber.log.Timber;

/**
 * Implementation of App Widget functionality.
 */
public class VolleyTourWidget extends AppWidgetProvider {

  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, BeachTournament tournament) {

    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.volley_tour_widget);

    if (tournament == null) {
      views.setViewVisibility(R.id.name_tournament, View.GONE);
      views.setViewVisibility(R.id.tournament_info, View.GONE);
      views.setViewVisibility(R.id.tournament_dates, View.GONE);
      views.setViewVisibility(R.id.no_data, View.VISIBLE);
    } else {
      views.setViewVisibility(R.id.name_tournament, View.VISIBLE);
      views.setViewVisibility(R.id.tournament_info, View.VISIBLE);
      views.setViewVisibility(R.id.tournament_dates, View.VISIBLE);
      views.setViewVisibility(R.id.no_data, View.GONE);
      views.setTextViewText(R.id.name_tournament, tournament.getTile());
      views.setTextViewText(R.id.title_tournament, tournament.getName());

      String startDate = DateUtil.format(tournament.getStartDate(), context.getString(R.string.date_format));
      String endDate = DateUtil.format(tournament.getEndDate(), context.getString(R.string.date_format));
      views.setTextViewText(R.id.start_tournament, startDate);
      views.setTextViewText(R.id.end_tournament, endDate);
      views.setTextViewText(R.id.title, context.getString(R.string.next_tournament));

      // Only allow to click on the tournament when it´s ongoing.
      Date currentDate = new Date();
      if (currentDate.after(tournament.getStartDate()) && currentDate.before(tournament.getEndDate())) {
        views.setTextViewText(R.id.title, context.getString(R.string.current_tournament));
        Intent intent = new Intent(context, BeachTournamentsActivity.class);
        intent.putExtra(BeachTournamentsActivity.BEACH_TOURNAMENT_KEY, tournament);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
      }

      try {
        Bitmap bmpFlag = Picasso.with(context)
            .load(context.getString(R.string.url_country_flag, tournament.getCountry()))
            .get();
        views.setImageViewBitmap(R.id.country, bmpFlag);
      } catch (IOException e) {
        Timber.e(e);
      }

    }
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  static void updateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, BeachTournament tournament) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId, tournament);
    }
  }

  public static void updateWidget(Context context, BeachTournament latestTournament) {
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, VolleyTourWidget.class));

    //Now update all widgets
    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.layout.volley_tour_widget);
    updateWidget(context, appWidgetManager, appWidgetIds, latestTournament);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    TournamentService.startWidgetUpdate(context);
  }

}

