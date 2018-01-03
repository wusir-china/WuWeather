package com.wusir.player;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wusir.StatusBarCompat;
import com.wusir.bean.FirstEvent;
import com.wusir.customView.DanmuVideoPlayer;
import com.wusir.util.DanMuControl;
import com.wusir.wuweather.Path;
import com.wusir.wuweather.R;

import org.greenrobot.eventbus.EventBus;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import master.flame.danmaku.ui.widget.DanmakuView;

public class VideoActivity extends AppCompatActivity {
    private DanmakuView danmakuView;
    private DanMuControl danMuControl;
    //private TextView startDanmu,closeDanmu;
    //private JCVideoPlayerStandard jcVideoPlayerStandard;
    private DanmuVideoPlayer danmuVideoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        StatusBarCompat.compat(this,R.color.colorGreen);
//        startDanmu= (TextView) findViewById(R.id.startDanmu);
//        closeDanmu= (TextView) findViewById(R.id.closeDanmu);
        //initEvents();
        danmakuView= (DanmakuView) findViewById(R.id.danmaview);
        danMuControl = new DanMuControl(danmakuView);
//        jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
//        jcVideoPlayerStandard.setUp(Path.videoPath,JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "嫂子闭眼睛");
        //danmuVideoPlayer=new DanmuVideoPlayer(this,danmakuView);
        danmuVideoPlayer = (DanmuVideoPlayer) findViewById(R.id.custom_videoplayer_standard);
        danmuVideoPlayer.onStatePlaying(danMuControl);
        danmuVideoPlayer.onStatePause(danMuControl);
        danmuVideoPlayer.setUp(Path.videoPath,JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "嫂子闭眼睛");
        Glide.with(this).load(Path.ivUrl).placeholder(R.mipmap.loading).error(R.mipmap.lose).into(danmuVideoPlayer.thumbImageView);
    }

    private void initEvents() {
//        startDanmu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //danMuControl.show(0);
//                danmakuView.start();
//                danMuControl.generateSomeDanmaku();
//            }
//        });
//        closeDanmu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                danMuControl.hide();
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        danMuControl.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        danMuControl.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        danMuControl.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        EventBus.getDefault().post(new FirstEvent("我是节操"));
        super.onBackPressed();
    }
}
