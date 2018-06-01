package br.bosseur.beachvolleytour.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

import br.bosseur.beachvolleytour.R;

public class PreferenceUtils {

  public static String getPreferredYear(Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getString(context.getString(R.string.pref_year_key), String.valueOf(Calendar.getInstance().get(Calendar
        .YEAR)));
  }

  public static void savePreferredYear(Context context, String year) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    sharedPreferences.edit()
        .putString(context.getString(R.string.pref_year_key), year)
        .apply();

  }

}
