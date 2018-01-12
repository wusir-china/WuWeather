package com.wusir.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zy on 2018/1/11.
 * 进程保活服务
 */

public class KeepProcessAliveService extends Service {
    private final static int GRAY_SERVICE_ID = 1001;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Build.VERSION.SDK_INT<18){
            startForeground(GRAY_SERVICE_ID,new Notification());
        }else{
            Intent in=new Intent(this,GrayInnerService.class);
            startService(in);
            startForeground(GRAY_SERVICE_ID,new Notification());
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /**     * 给 API >= 18 的平台上用的灰色保活手段     */
    public static class GrayInnerService extends Service{
        @Override
        public int onStartCommand(Intent intent,int flags, int startId) {
            startForeground(GRAY_SERVICE_ID,new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
