package com.wusir.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wusir.wuweather.TestServiceActivity;

/**
 * Created by Administrator on 2018/1/17.
 * Service/IntentService不会专门启动一条单独的进程，Service与它所在应用位于同一个进程中
 */

public class IntentServiceDemo extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public IntentServiceDemo() {
        super("IntentServiceDemo");
    }
    public IntentServiceDemo(String name) {
        super(name);
    }
    //创建workerThread线程
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent !=null){
            final String action = intent.getAction();
            if(ACTION_UPLOAD_IMG.equals(action)) {
                final String path = intent.getStringExtra(EXTRA_IMG_PATH);
                handleUploadImg(path);
            }
        }
    }
    private static final String ACTION_UPLOAD_IMG ="com.wusir.service.intentservice.action.UPLOAD_IMAGE";
    public static final String EXTRA_IMG_PATH ="com.wusir.service.intentservice.extra.IMG_PATH";
    public static void startUploadImg(Context context, String path){
        Intent intent =new Intent(context, IntentServiceDemo.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        intent.putExtra(EXTRA_IMG_PATH, path);
        context.startService(intent);
    }
    private void handleUploadImg(String path) {
        /*
        *Service也不是专门一条新线程，因此不适合在Service中直接处理耗时的任务.
        *而IntentService会创建独立的worker线程处理耗时操作.
        * 且所有请求处理完成后，IntentService会自动停止，无需调用stopSelf()方法停止(onDestroy())Service
        * */
        try{//模拟上传耗时
            Thread.sleep(3000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        Intent intent =new Intent(TestServiceActivity.UPLOAD_RESULT);
        intent.putExtra(EXTRA_IMG_PATH, path);
        sendBroadcast(intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("IntentServiceDemo","onCreate");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("IntentServiceDemo","onDestroy");
    }
}
