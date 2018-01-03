package com.wusir.wuweather;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.annotation.Target;
import java.util.ArrayList;
import android.support.design.widget.TabLayout;
import android.text.SpannableStringBuilder;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wusir.StatusBarCompat;
import com.wusir.adapter.FragmentViewPagerAdapter;
import com.wusir.bean.FirstEvent;
import com.wusir.customView.ArcMenu;
import com.wusir.player.Video2Activity;
import com.wusir.player.VideoActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    //1.当前Activity的实例
//    Context context=MainActivity.this;
//    Context context1=this;
//    //2.项目的Application的实例
//    Context context2=getApplicationContext();
//    //3.
//    Context context3=context2.getApplicationContext();
    private ArrayList<String> aTitleList;
    private ViewPager vp;
    private ItemFragment f1,f2,f3,f4;
    private ImageView iv_player;
    private CoordinatorLayout cl;
    private ArcMenu mArcMenu;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.compat(this,R.color.colorGreen);
        //setTaskDescription();
        EventBus.getDefault().register(this);
        initView();
        aTitleList = new ArrayList<String>();
        aTitleList.add("杭州");
        aTitleList.add("南昌");
        aTitleList.add("九江");
        aTitleList.add("景德镇");
        f1=ItemFragment.newInstance(1);
        f2=ItemFragment.newInstance(2);
        f3=ItemFragment.newInstance(3);
        f4=ItemFragment.newInstance(4);
        ArrayList<Fragment> list=new ArrayList<>();
        list.add(f1);
        list.add(f2);
        list.add(f3);
        list.add(f4);
        FragmentViewPagerAdapter adapter=new FragmentViewPagerAdapter(getSupportFragmentManager());
        adapter.setData(list);
        adapter.setTitleData(aTitleList);
        vp.setAdapter(adapter);
        TabLayout tab = (TabLayout) findViewById(R.id.tabLayout);
        tab.setupWithViewPager(vp);
        initEvent();
        showTapTarget();
    }
    private void showTapTarget() {
        final Display display=getWindowManager().getDefaultDisplay();
        //display.getHeight()=1920,display.getWidth()=1080
        final Rect target=new Rect(0,display.getHeight(),0,display.getHeight());
        target.offset(display.getWidth()/8,-56);

        final Rect target2=new Rect(display.getWidth(),display.getHeight(),display.getWidth(),display.getHeight());
        target2.offset(-display.getWidth()/8,-56);
        // 引导用户使用
        TapTargetSequence sequence=new TapTargetSequence(this)
                .targets(
                        TapTarget.forBounds(target,"点此进入游戏")
                            .dimColor(R.color.colorGreen)
                            .outerCircleColor(R.color.colorAccent)
                            .targetRadius(60)
                            .transparentTarget(true)
                            .drawShadow(true)
                            .id(1),
                        TapTarget.forBounds(target2,"点此展开卫星菜单")
                            .dimColor(R.color.colorGreen)
                            .outerCircleColor(R.color.colorAccent)
                            .targetRadius(60)
                            .transparentTarget(true)
                            .drawShadow(true)
                            .id(2)
                ).listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {

                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }
                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                });
        sequence.start();
    }
    private void initView(){
        fab= (FloatingActionButton) findViewById(R.id.fab);
        cl= (CoordinatorLayout) findViewById(R.id.cl);
        mArcMenu = (ArcMenu) findViewById(R.id.id_menu);
        vp = (ViewPager) findViewById(R.id.FragmentVP);
        iv_player= (ImageView) findViewById(R.id.iv_player);
    }
    private void initEvent(){
        iv_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                String[] options={"节操播放","原生播放"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent in=new Intent(MainActivity.this, VideoActivity.class);
                                startActivity(in);
                                break;
                            case 1:
                                Intent in2=new Intent(MainActivity.this, Video2Activity.class);
                                startActivity(in2);
                                break;
                        }
                    }
                });
                builder.show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this, GameRankActivity.class);
                startActivity(in);
            }
        });
        mArcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener(){
            @Override
            public void onClick(View view, int pos){
                switch (pos){
                    case 5:
                        requestPermissions();
                        break;
                    case 4:
                        Intent intent = new Intent(MainActivity.this,CheckCodeActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Intent intent2 = new Intent(MainActivity.this,QRcodeActivity.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(MainActivity.this,PhotoViewActivity.class);
                        startActivity(intent3);
                        break;
                    case 1:
                        Intent intent4 = new Intent(MainActivity.this,RichEditActivity.class);
                        startActivity(intent4);
                        break;
                }
                Toast.makeText(MainActivity.this, pos+":"+view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FirstEvent event) {
        Snackbar snackbar=Snackbar.make(cl,event.getMsg(),Snackbar.LENGTH_SHORT);
        View view=snackbar.getView();
        if(view!=null){
            view.setBackgroundColor(Color.parseColor("#FF4081"));
        }
        snackbar.show();
    }
    private void requestPermissions() {
        RxPermissions rxPermissions=new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.CAMERA).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(@NonNull Permission permission) throws Exception {
                if(permission.granted){
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivity(intent);
                }else if (permission.shouldShowRequestPermissionRationale) {
                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                    Toast.makeText(MainActivity.this, "权限被拒接，无法打开相机", Toast.LENGTH_SHORT).show();
                } else {
                    // 用户拒绝了该权限，并且选中『不再询问』
                    Toast.makeText(MainActivity.this, "权限被拒接，无法打开相机,请在设置中打开相关权限", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
