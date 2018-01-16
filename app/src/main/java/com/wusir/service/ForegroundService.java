package com.wusir.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zy on 2018/1/11.
 * 前台服务
 */

public class ForegroundService extends Service {
    private boolean threadDisable;
    private int count;
    //1.
    @Override
    public void onCreate() {
        Log.i("ForegroundService", "onCreate");
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!threadDisable){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    Log.i("ForegroundService", "Count is " + count);
                }
            }
        }).start();
    }
    //2.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("ForegroundService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
    //3.
    @Override
    public boolean stopService(Intent name) {
        Log.i("ForegroundService", "stopService");
        return super.stopService(name);
    }
    //4.
    @Override
    public void onDestroy() {
        super.onDestroy();
        threadDisable=true;
        Log.i("ForegroundService", "onDestroy");
    }
    //必须实现的父类抽象方法,BackgroundService时使用。
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
