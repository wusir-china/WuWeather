package com.goldensoft.goldenlibrary;

import android.app.Application;

/**
 * Created by Administrator on 2018/3/13.
 */

public class GdApplication extends Application{
    private static GdApplication gApplication;
    public static GdApplication getInstance(){
        return gApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gApplication=this;
        //CrashTool.init(gApplication);异常的捕获放在golden.init里面
        golden.init(gApplication);
        golden.Builder.setDebug(true);
    }

}
