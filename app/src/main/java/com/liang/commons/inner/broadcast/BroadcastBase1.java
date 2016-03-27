package com.liang.commons.inner.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by liang on 16/2/28.
 */
public class BroadcastBase1 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String msg=intent.getExtras().get("msg").toString();
        Log.i("BroadcastBase", "msg=" + msg + BroadcastBase1.class.toString());

        Toast.makeText(context, "Hello , BroadcastBase : "+msg + BroadcastBase1.class.toString(), Toast.LENGTH_LONG).show();

    }

}