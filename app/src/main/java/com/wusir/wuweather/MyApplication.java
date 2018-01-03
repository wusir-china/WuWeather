package com.wusir.wuweather;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;

/**
 * Created by zy on 2017/5/26.
 */

public class MyApplication extends Application {
    public static Context AppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext=getApplicationContext();
        OkGo.getInstance().init(this);
    }
}
