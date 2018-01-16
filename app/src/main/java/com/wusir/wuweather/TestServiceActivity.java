package com.wusir.wuweather;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wusir.StatusBarCompat;
import com.wusir.service.BackgroundService;
import com.wusir.service.ForegroundService;
import com.wusir.service.IBackgroundService;
import com.wusir.service.IRemoteService;
import com.wusir.service.RemoteService;

public class TestServiceActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView start,stop,bind,unbind,tv_count,bindRemote,unbindRemote;
    private int i = 0;
    private NotificationManager manager = null;
    private NotificationCompat.Builder builder;
    private final int NotificationID = 0x10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);
        StatusBarCompat.compat(this,R.color.colorGreen);
        initViews();
        Log.i("TestServiceActivity", "onCreate");
        initNotification();
        handler.post(updateThread);//开启更新progressBar线程
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateThread);
        manager.cancel(NotificationID);
        this.stopService(new Intent(this, ForegroundService.class));
    }

    private void initViews() {
        tv_count= (TextView) findViewById(R.id.tv_count);
        start= (TextView) findViewById(R.id.start);
        stop= (TextView) findViewById(R.id.stop);
        bind= (TextView) findViewById(R.id.bind);
        unbind= (TextView) findViewById(R.id.unbind);
        bindRemote= (TextView) findViewById(R.id.bindRemote);
        unbindRemote= (TextView) findViewById(R.id.unbindRemote);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        bindRemote.setOnClickListener(this);
        unbindRemote.setOnClickListener(this);
    }
    //local service
    private IBackgroundService backgroundService;
    private ServiceConnection localConn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            backgroundService= (IBackgroundService) service;
            TestServiceActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_count.setText("count is "+backgroundService.getCount());
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            backgroundService=null;
        }
    };
    //remote service
    private IRemoteService remoteService;
    private ServiceConnection remoteConn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            remoteService= (IRemoteService) service;
            TestServiceActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        tv_count.setText("count is "+remoteService.getCount());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            remoteService=null;
        }
    };
    //使用了Notification的默认布局，也可以使用RemoteViews自定义它的布局
    private void initNotification() {
        manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder=new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle("wusir天气")
                .setContentText("开始下载")
                .setProgress(20,0,false);;
        manager.notify(NotificationID,builder.build());
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            builder.setProgress(20,msg.arg1,false);
            builder.setContentText("当前进度:"+msg.arg1+"/"+20);
            manager.notify(NotificationID,builder.build());
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
                handler.removeCallbacks(updateThread);
                manager.cancel(NotificationID);//下载完后自动取消notification视图
            }else{
                handler.sendMessage(msg);
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                Intent intent=new Intent(this,ForegroundService.class);
                startService(intent);//开始服务
                break;
            case R.id.stop:
                Intent intent2=new Intent(this,ForegroundService.class);
                stopService(intent2);
                break;
            case R.id.bind:
                Intent intent3=new Intent(this,BackgroundService.class);
                bindService(intent3,localConn,BIND_AUTO_CREATE);
                break;
            case R.id.unbind:
                unbindService(localConn);
                break;
            case R.id.bindRemote:
                Intent intent4=new Intent(this,RemoteService.class);
                bindService(intent4,remoteConn,BIND_AUTO_CREATE);
                break;
            case R.id.unbindRemote:
                unbindService(remoteConn);
                break;
        }
    }
}
