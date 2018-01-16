package com.wusir.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zy on 2018/1/16.
 */

public class RemoteService extends Service{
    private boolean threadDisable;
    private int count;
    private IRemoteService.Stub remoteBinder=new IRemoteService.Stub(){

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int getCount() throws RemoteException {
            return count;
        }
    };

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        threadDisable=true;
        Log.i("ForegroundService", "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("ForegroundService", "onunbind");
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return remoteBinder;
    }
}
