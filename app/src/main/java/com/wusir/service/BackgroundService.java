package com.wusir.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zy on 2018/1/11.
 * 后台服务
 */

public class BackgroundService extends Service {//implements IBackgroundService
    private boolean threadDisable;
    private int count;
    public class ServiceBinder extends Binder implements IBackgroundService{
        @Override
        public int getCount() {
            return count;
        }
    }
    private ServiceBinder serviceBinder=new ServiceBinder();
    //1.
    @Override
    public void onCreate() {
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
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }
    //3.
    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("ForegroundService", "onUnbind");
        return super.onUnbind(intent);
    }
    //4.
    @Override
    public void onDestroy() {

        super.onDestroy();
        threadDisable=true;
        Log.i("ForegroundService", "onDestroy");
        //保证 Service 在后台不被 kill
        //1.发送一个自定义的广播
        //2.当收到广播的时候，重新启动service；
    }

//    @Override
//    public int getCount() {
//        return count;
//    }
}
