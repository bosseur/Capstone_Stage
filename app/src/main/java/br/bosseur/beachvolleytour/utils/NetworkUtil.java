package br.bosseur.beachvolleytour.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkUtil {

  public static boolean isOnline(Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    return cm.getActiveNetworkInfo() != null &&
        cm.getActiveNetworkInfo().isConnectedOrConnecting();
  }
}
