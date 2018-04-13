package com.goldensoft.goldenlibrary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.goldensoft.goldenlibrary.utils.GdActivityTool;

/**
 * Created by golden on 2018/4/2.
 */

public class BaseActivity extends AppCompatActivity{
    public BaseActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = this;
        //GdActivityTool.addActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 初始化 Toolbar
     */
    public void initToolBar(Toolbar toolbar,boolean homeAsUpEnabled,String title){
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }
}
