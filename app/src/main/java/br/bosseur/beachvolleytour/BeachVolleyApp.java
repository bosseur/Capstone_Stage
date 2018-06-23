package br.bosseur.beachvolleytour;

import android.app.Application;

import timber.log.Timber;

public class BeachVolleyApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Timber.plant(new Timber.DebugTree());
  }
}
