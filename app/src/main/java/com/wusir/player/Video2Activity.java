package com.wusir.player;

import android.graphics.Outline;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.wusir.StatusBarCompat;
import com.wusir.adapter.MomentAdapter;
import com.wusir.bean.Comment;
import com.wusir.bean.FirstEvent;
import com.wusir.bean.Moment;
import com.wusir.bean.User;
import com.wusir.wuweather.CommentFun;
import com.wusir.wuweather.CustomTagHandler;
import com.wusir.wuweather.Path;
import com.wusir.wuweather.R;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;

//原生播放
public class Video2Activity extends AppCompatActivity implements View.OnClickListener{
    private ImageView btnplay,btnstop,btnpause;
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private int position;
    public static User sUser = new User(1, "走向远方"); // 当前登录用户
    private ListView mListView;
    private MomentAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video2);
        StatusBarCompat.compat(this,R.color.colorGreen);
        btnplay=(ImageView)this.findViewById(R.id.btnplay);
        btnstop=(ImageView) this.findViewById(R.id.btnstop);
        btnpause=(ImageView) this.findViewById(R.id.btnpause);
        btnstop.setOnClickListener(this);
        btnplay.setOnClickListener(this);
        btnpause.setOnClickListener(this);

        mediaPlayer=new MediaPlayer();
        surfaceView=(SurfaceView) findViewById(R.id.sv);
        //通过ViewOutlineProvider进行裁剪
        ViewOutlineProvider provider= null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            provider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        outline.setOval(0,0,surfaceView.getWidth(),surfaceView.getHeight());
                    }
                }
            };
            surfaceView.setOutlineProvider(provider);
        }
        //设置SurfaceView自己不管理的缓冲区
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (position>0) {
                    try {
                        //开始播放
                        play();
                        //并直接从指定位置开始播放
                        mediaPlayer.seekTo(position);
                        position=0;
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        //评论
        mListView = (ListView) findViewById(R.id.list_moment);

        // 模拟数据
        ArrayList<Moment> moments = new ArrayList<Moment>();
        for (int i = 0; i < 20; i++) {
            ArrayList<Comment> comments = new ArrayList<Comment>();
            comments.add(new Comment(new User(i + 2, "用户" + i), "评论" + i, null));
            comments.add(new Comment(new User(i + 100, "用户" + (i + 100)), "评论" + i, new User(i + 200, "用户" + (i + 200))));
            comments.add(new Comment(new User(i + 200, "用户" + (i + 200)), "评论" + i, null));
            comments.add(new Comment(new User(i + 300, "用户" + (i + 300)), "评论" + i, null));
            moments.add(new Moment("动态 " + i, comments));
        }

        mAdapter = new MomentAdapter(this, moments, new CustomTagHandler(this, new CustomTagHandler.OnCommentClickListener() {
            @Override
            public void onCommentatorClick(View view, User commentator) {
                Toast.makeText(getApplicationContext(), commentator.mName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceiverClick(View view, User receiver) {
                Toast.makeText(getApplicationContext(), receiver.mName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onContentClick(View view, User commentator, User receiver) {
                if (commentator != null && commentator.mId == sUser.mId) { // 不能回复自己的评论
                    return;
                }
                inputComment(view, commentator);
            }
        }));

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"click "+position,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void inputComment(final View v) {
        inputComment(v, null);
    }

    public void inputComment(final View v, User receiver) {
        CommentFun.inputComment(Video2Activity.this, mListView, v, receiver, new CommentFun.InputCommentListener() {
            @Override
            public void onCommitComment() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    protected void onPause() {
        //先判断是否正在播放
        if (mediaPlayer.isPlaying()) {
            //如果正在播放我们就先保存这个播放位置
            position=mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
        }
        super.onPause();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnplay:
                play();
                break;
            case R.id.btnpause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }else{
                    mediaPlayer.start();
                }
                break;
            case R.id.btnstop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                break;
            default:
                break;
        }
    }
    private void play(){
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(Path.videoPath);
            mediaPlayer.setDisplay(surfaceView.getHolder());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        EventBus.getDefault().post(new FirstEvent("我是原生"));
        super.onBackPressed();
    }

}
