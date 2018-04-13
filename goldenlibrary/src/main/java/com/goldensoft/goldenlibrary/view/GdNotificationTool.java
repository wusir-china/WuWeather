package com.goldensoft.goldenlibrary.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.goldensoft.goldenlibrary.golden;


/**
 * Created by golden on 2018/4/4.
 * notification通知
 */

public class GdNotificationTool {
    private int i = 0;
    private static NotificationCompat.Builder mbuilder;
    private static Notification.Builder builder;
    //private static final int NotiID=0x10000;
    private static NotificationManager manager;
    //使用了Notification的默认布局，也可以使用RemoteViews自定义它的布局
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void showSimpleNotification(Context context, int resId,int id, String... params){
//        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        builder=new Notification.Builder(context);
//        Notification notification = builder
//                .setSmallIcon(resId)//设置小图标
//                .setContentTitle(params[0])//标题
//                .setContentText(params[1])//内容
//                .setProgress(100,0,false)
//                .build();
//        manager.notify(id, notification);
        //兼容低版本
        manager= (NotificationManager) golden.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mbuilder=new NotificationCompat.Builder(golden.getContext())//获得全局context
                .setSmallIcon(resId)
                .setContentTitle(params[0])
                .setContentText(params[1])
                .setProgress(100,0,false);;
        manager.notify(id,mbuilder.build());
    }
    //取消notification
    public static void cancel(int id){
        manager.cancel(id);
    }
    //设置当前进度
    public static void setProgress(int progress){
        builder.setProgress(100,progress,false);
    }
    //使用handler更新notification中的progressBar
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            builder.setProgress(20,msg.arg1,false);
            builder.setContentText("当前进度:"+msg.arg1+"/"+20);
            manager.notify(0,mbuilder.build());
            handler.post(updateThread);
        }
    };
    Runnable updateThread=new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i+=1;
            Message msg=handler.obtainMessage();//省内存
            msg.arg1=i;
            if(i>20){
                handler.removeCallbacks(updateThread);//取消回调
                manager.cancel(0);//下载完后自动取消notification视图
            }else{
                handler.sendMessage(msg);
            }
        }
    };
}
