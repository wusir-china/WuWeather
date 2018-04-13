package com.goldensoft.goldenlibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.view.Window;

import com.goldensoft.goldenlibrary.GdApplication;

/**
 * App辅助类
 *
 * @author wusir
 * @version 1.0.0
 */
public class GdAppUtil {
    /**
     * 获取App包名
     *
     * @param context 上下文
     * @return App包名
     */
    public static String getAppPackageName(Context context) {
        return context.getPackageName();
    }
    //    获取APP版本名称
    public static String getAppVersionName(Context context) {
        String packageName=context.getPackageName();
        if(GdDataTool.isNullString(packageName)) {
            return null;
        } else {
            try {
                PackageManager pm = context.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(packageName, 0);
                return pi == null?null:pi.versionName;
            } catch (PackageManager.NameNotFoundException var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }
    /**
     * 全局获取Context
     * @return
     */
    public static Context getContext(){
        return GdApplication.getInstance();
    }

    /**
     * 获取App名称
     * @return 获取App名称
     */
    public static String getAppName(){
        Context context = getContext();
        return context.getString(context.getApplicationInfo().labelRes);
    }

    /**
     * 获取Android系统版本
     */
    public static String getAndroidVersion(){
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取Android SDK系统版本
     */
    public static String getAndroidSDKVersion(){
        return Build.VERSION.SDK_INT + "";
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel(){
        return Build.MODEL;
    }


    /**
     * 获取网络状态信息
     * @return NetworkInfo
     */
    public static NetworkInfo getNetworkInfo(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }

    /**
     * 判断是否离线
     * @return true则有网络,否则离线
     */
    public static boolean isNetworkConnected(){
        NetworkInfo activeNetworkInfo = getNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * 判断是否Wifi连线
     * @return true则wifi,否则不是
     */
    public static boolean isWifi(){
        NetworkInfo activeNetworkInfo = getNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 隐藏虚拟导航栏
     * @param window Android Window
     */
    public static void hideNavgationBar(Window window){
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
