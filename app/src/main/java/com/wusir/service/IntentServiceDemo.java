package com.wusir.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.wusir.wuweather.TestServiceActivity;

/**
 * Created by Administrator on 2018/1/17.
 */

public class IntentServiceDemo extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
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
    private static final String ACTION_UPLOAD_IMG ="com.zhy.blogcodes.intentservice.action.UPLOAD_IMAGE";
    public static final String EXTRA_IMG_PATH ="com.zhy.blogcodes.intentservice.extra.IMG_PATH";
    public static void startUploadImg(Context context, String path){
        Intent intent =new Intent(context, IntentServiceDemo.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        intent.putExtra(EXTRA_IMG_PATH, path);
        context.startService(intent);
    }
    private void handleUploadImg(String path) {
        try{//模拟上传耗时
            Thread.sleep(3000);
            Intent intent =new Intent(TestServiceActivity.UPLOAD_RESULT);
            intent.putExtra(EXTRA_IMG_PATH, path);
            sendBroadcast(intent);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
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
