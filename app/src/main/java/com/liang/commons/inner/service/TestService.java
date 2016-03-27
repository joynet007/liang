package com.liang.commons.inner.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by liang on 16/3/1.
 */
public class TestService extends Service {

    String tag = "TestService";

    public TestService() {
        super();
    }

    @Override
    public void onCreate() {
        Log.i(tag,"开始执行==  onCreate");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(tag, "开始执行==  onStartCommand");

        MyThread mt = new MyThread();
        Thread t = new Thread(mt);
        t.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(tag,"开始执行==  onDestroy");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    class MyThread implements  Runnable{
        int i =0 ;
        @Override
        public void run() {
            try {
                jisuan();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         *
         */
        public synchronized void jisuan() throws InterruptedException {
            while(i<10){
                Log.i(tag,"开始数数== "+ i );
                Thread.sleep(2000);
                i++;
            }
        }
    }

}
