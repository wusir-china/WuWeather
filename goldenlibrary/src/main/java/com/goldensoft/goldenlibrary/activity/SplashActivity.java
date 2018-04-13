package com.goldensoft.goldenlibrary.activity;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.goldensoft.goldenlibrary.R;
import com.goldensoft.goldenlibrary.thread.JumpThread;

public class SplashActivity extends AppCompatActivity {
    ImageView mSpBgImage;
    TextView mSpJumpTv;
    String imgUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSpBgImage=findViewById(R.id.sp_bg);
        mSpJumpTv=findViewById(R.id.sp_jump_btn);
        //以下部分最好最实际开发者中定义
        Glide.with(this).load(imgUrl).into(mSpBgImage);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //mSpJumpTv.setText("跳转　3");
                Thread t=new Thread(new JumpThread(mSpJumpTv,3,SplashActivity.this,BaseActivity.class));
                t.start();
            }
        },2000);
    }
}
