package com.liang.commons.inner.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import liang.com.liang.R;

/**
 * Created by liang on 16/2/28.
 */
public class BroadcastBase extends BroadcastReceiver {

    private NotificationManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {

        String msg=intent.getExtras().get("msg").toString();
        Log.i("BroadcastBase", "msg=" + msg + BroadcastBase.class.toString());

        Toast.makeText(context, "Hello , BroadcastBase : "+msg + BroadcastBase.class.toString(), Toast.LENGTH_LONG).show();

        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker("广播来了！");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("没有网络了。。。。。");
        builder.setContentText("wifi 掉了！！！！！！");

        manager.notify(1001, builder.build());


    }

}
