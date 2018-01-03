package com.wusir.util;

import android.content.Context;
import android.widget.Toast;

import com.wusir.wuweather.MyApplication;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class ToastUtil {
    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
