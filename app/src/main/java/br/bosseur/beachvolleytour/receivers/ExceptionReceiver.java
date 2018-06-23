package br.bosseur.beachvolleytour.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.bosseur.beachvolleytour.R;
import br.bosseur.beachvolleytour.listeners.ErrorCallBack;

public class ExceptionReceiver extends BroadcastReceiver {
  private final ErrorCallBack mErrorCallBack;
  private final Context mContext;

  public ExceptionReceiver(ErrorCallBack errorCallBack, Context context) {
    mErrorCallBack = errorCallBack;
    mContext = context;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    if(context.getString(R.string.broadcast_request_error).equals(intent.getAction())) {
      String message = intent.getStringExtra(mContext.getString(R.string.broadcast_exception_message_key));
      mErrorCallBack.error(message);
    }
  }

}
