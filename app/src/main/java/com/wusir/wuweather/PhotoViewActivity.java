package com.wusir.wuweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import uk.co.senab.photoview.PhotoView;
//import uk.co.senab.photoview.PhotoViewAttacher;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.wusir.StatusBarCompat;
import com.wusir.util.ToastUtil;

public class PhotoViewActivity extends AppCompatActivity {
    private PhotoView mPhotoView;
    private PhotoViewAttacher mAttacher;
    private LocalBroadcastReceiver mReceiver;
    private LocalBroadcastManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        StatusBarCompat.compat(this,R.color.colorGreen);
        mPhotoView= (PhotoView) findViewById(R.id.photoview);
        mAttacher=new PhotoViewAttacher(mPhotoView);
        mPhotoView.setImageResource(R.mipmap.a);
        mAttacher.update();
        //测试App应用内广播
        mReceiver=new LocalBroadcastReceiver();
        manager=LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(mReceiver,new IntentFilter("zzz"));
        //发送广播
        Intent intent=new Intent();
        intent.setAction("zzz");
        manager.sendBroadcast(intent);
    }
    private class LocalBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ToastUtil.showToast(context,"xxx");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.unregisterReceiver(mReceiver);
    }
}
