package com.wusir.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by zy on 2018/1/11.
 * 后台服务
 */

public class BackgroundService extends Service {
    //1.
    @Override
    public void onCreate() {
        super.onCreate();
    }
    //2.
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //3.
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    //4.
    @Override
    public void onDestroy() {
        super.onDestroy();
    }





}
