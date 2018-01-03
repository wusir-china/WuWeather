package com.wusir.customView;

import android.content.Context;
import android.util.AttributeSet;

import com.wusir.util.DanMuControl;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by zy on 2017/9/13.
 */

public class DanmuVideoPlayer extends JCVideoPlayerStandard {
    private DanmakuView danmakuView;
    //private DanMuControl danMuControl;
    public DanmuVideoPlayer(Context context) {
        super(context);
    }
//    public DanmuVideoPlayer(Context context,DanmakuView danmakuView) {
//        super(context);
//        danMuControl = new DanMuControl(danmakuView);
//        this.danmakuView=danmakuView;
//    }
    public DanmuVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    public void onStatePlaying(DanMuControl danMuControl) {
        super.onStatePlaying();
        //danmakuView.start();
        //danMuControl.generateSomeDanmaku();
        danMuControl.startDanMu(this.getCurrentPositionWhenPlaying());
        danMuControl.show(this.getCurrentPositionWhenPlaying());

    }
    @Override
    public void onStatePause() {
        super.onStatePause();
    }
    public void onStatePause(DanMuControl danMuControl) {
        super.onStatePause();
        danMuControl.hide();
    }
}
