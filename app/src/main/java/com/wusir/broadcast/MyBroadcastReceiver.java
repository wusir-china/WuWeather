package com.wusir.broadcast;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.wusir.util.ToastUtil;

/**
 * Created by zy on 2018/2/5.
 */

public class MyBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction()!=null){
            switch (intent.getAction()){
                case "com.wusir.wuweather1":
                    String action=intent.getAction();
                    String extra=intent.getStringExtra("name");
                    int flag=intent.getFlags();
                    ToastUtil.showToast(context,extra);
                    break;
                case "com.wusir.wuweather2":
                    break;
                case "com.wusir.wuweather3":
                    break;
            }
        }
    }
}
