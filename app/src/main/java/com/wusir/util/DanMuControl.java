package com.wusir.util;

import android.graphics.Color;

import com.wusir.bean.DanMuBean;

import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by Administrator on 2017/9/1 0001.
 */

public class DanMuControl {

    private DanmakuView mDanmakuView;
    boolean isCanShowDanMu = false;
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    public DanMuControl(DanmakuView mDanmakuView) {
        this.mDanmakuView = mDanmakuView;
        initDanMu();
    }

    private void initDanMu() {
        hide();
        mDanmakuView.enableDanmakuDrawingCache(true);
        mDanmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                isCanShowDanMu = true;
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuContext.setScrollSpeedFactor(3.0f);
        mDanmakuView.showFPS(false);
        mDanmakuView.prepare(parser, danmakuContext);
    }
    public void startDanMu(long time){
        if(mDanmakuView.isShown()){
            generateSomeDanmaku();//
            mDanmakuView.start(time);
        }
    }
    public void addDanMu(DanMuBean danMuBean){
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = danMuBean.getText();
        danmaku.padding = 5;
        danmaku.textSize = 60;
        danmaku.textColor = Color.WHITE;
        danmaku.isLive = false;
        danmaku.setTime(danMuBean.getTime()*1000);
        mDanmakuView.addDanmaku(danmaku);
    }
    public void sendDanMu(String conetnt,long time){
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = conetnt;
        danmaku.padding = 5;
        danmaku.textSize =60;
        danmaku.textColor = Color.RED;
        danmaku.isLive = false;
        danmaku.priority = 1;
        danmaku.setTime(time);
        mDanmakuView.addDanmaku(danmaku);
    }
    public void onPause(){
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }
    public void onResume(){
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }
    public void onDestroy(){
        if (mDanmakuView != null) {
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }
    public void clearScreen(){
        mDanmakuView.clearDanmakusOnScreen();
    }
    public void hide(){
        mDanmakuView.hideAndPauseDrawTask();
    }
    //展示弹幕
    public void show(long position){
        mDanmakuView.showAndResumeDrawTask(position);
    }
    public void seekTo(long time){
        if(mDanmakuView.isShown())
        mDanmakuView.seekTo(time);
    }
    public void pause(){
        mDanmakuView.pause();
    }
    //随机生成弹幕数据
    public void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //while(showDanmaku) {
                    long time = new Random().nextInt(300);//介于[0,300)
                    String content = "我是" + time + time;
                    String news_id="" + time;
                    DanMuBean danMuBean=new DanMuBean();
                    danMuBean.setNews_id(news_id);
                    danMuBean.setText(content);
                    danMuBean.setTime(time);
                    addDanMu(danMuBean);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                //}
            }
        }).start();
    }
}
