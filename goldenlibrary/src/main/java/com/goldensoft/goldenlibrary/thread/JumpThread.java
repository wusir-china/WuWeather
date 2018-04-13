package com.goldensoft.goldenlibrary.thread;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.goldensoft.goldenlibrary.R;
import com.goldensoft.goldenlibrary.activity.BaseActivity;

/**
 * Created by golden on 2018/4/4.
 */

public class JumpThread implements Runnable{
    private TextView textView;
    private int numberTime ;//跳转时间
    private AppCompatActivity fromActivity;
    private Class clazz;//<BaseActivity>
    public JumpThread(TextView textView, int numberTime, AppCompatActivity fromActivity,Class clazz) {
        this.textView = textView;
        this.numberTime = numberTime;
        this.fromActivity = fromActivity;
        this.clazz=clazz;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(JumpThread.this);
                jumpActivity();
            }
        });
    }

    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView.setText("跳转   "+String.valueOf(numberTime));
            if (numberTime==0) {
                jumpActivity();
            }
        }
    };
    private void jumpActivity(){
        Intent intent = new Intent(fromActivity, clazz);
        //解决从倒计时跳过的按钮而造成重复clazz的问题，或在主配置文件中配置
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        fromActivity.startActivity(intent);
        //activity切换动画
        fromActivity.overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        fromActivity.finish();
    }
    @Override
    public void run() {
        try {
            while (numberTime>0) {
                Thread.sleep(1000);//不打广告时注释掉
                numberTime--;
                handler.sendMessage(new Message());//handler.obtainMessage()
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
