package com.wusir.wuweather;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wusir.StatusBarCompat;
import com.wusir.service.BackgroundService;
import com.wusir.service.ForegroundService;

public class TestServiceActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView start,stop,bind,unbind;
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
//        Intent intent2=new Intent(this,ForegroundService.class);
//        stopService(intent2);
    }

    private void initViews() {
        start= (TextView) findViewById(R.id.start);
        stop= (TextView) findViewById(R.id.stop);
        bind= (TextView) findViewById(R.id.bind);
        unbind= (TextView) findViewById(R.id.unbind);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
    }
    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

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
                bindService(intent3,conn,BIND_AUTO_CREATE);
                break;
            case R.id.unbind:
                unbindService(conn);
                break;
        }
    }
}
