package br.bosseur.beachvolleytour.data.contracts;


import android.net.Uri;

public class BaseContract {
  public final static String CONTENT_AUTHORITY = "br.bosseur.beachvolleytour.data";
  public final static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

}
