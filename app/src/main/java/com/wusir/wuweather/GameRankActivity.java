package com.wusir.wuweather;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.wusir.StatusBarCompat;
import com.wusir.adapter.FragmentViewPagerAdapter;
import com.wusir.adapter.ViewPagerAdapter;
import com.wusir.util.BottomNavigationViewHelper;

import java.util.ArrayList;

public class GameRankActivity extends AppCompatActivity implements View.OnClickListener{
    private RankItemFragment allFragment,highFragment,newFragment,inFragment;
    private ViewPager vp;
    private ImageView iv_download;
    private BottomNavigationView bottom_navigation;
    private int position;
    private static final int FRAGMENT_NEWS = 0;
    private static final int FRAGMENT_PHOTO = 1;
    private static final int FRAGMENT_VIDEO = 2;
    private static final int FRAGMENT_MEDIA = 3;
    private static final String NEWS = "news";
    private static final String PHOTO = "photo";
    private static final String VIDEO = "video";
    private static final String MEDIA = "media";
    private static final String POSITION = "position";
    private static final String SELECT_ITEM = "bottomNavigationSelectItem";
    private MenuItem menuItem;
    private ViewPagerAdapter adapter;
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rank);
        StatusBarCompat.compat(this,R.color.colorGreen);
        initViews();
        if (savedInstanceState != null) {
//            allFragment = (RankItemFragment) getSupportFragmentManager().findFragmentByTag(NEWS);
//            highFragment = (RankItemFragment) getSupportFragmentManager().findFragmentByTag(PHOTO);
//            newFragment = (RankItemFragment) getSupportFragmentManager().findFragmentByTag(VIDEO);
//            inFragment = (RankItemFragment) getSupportFragmentManager().findFragmentByTag(MEDIA);
            // 恢复 recreate 前的位置
            //showFragment(savedInstanceState.getInt(POSITION));
            vp.setCurrentItem(savedInstanceState.getInt(POSITION));
            bottom_navigation.setSelectedItemId(savedInstanceState.getInt(SELECT_ITEM));
        } else {
            //showFragment(FRAGMENT_NEWS);
            vp.setCurrentItem(FRAGMENT_NEWS);
        }
    }

    private void initViews() {
        iv_download= (ImageView) findViewById(R.id.iv_download);
        iv_download.setOnClickListener(this);
        vp = (ViewPager)findViewById(R.id.vp);
        bottom_navigation= (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_news:
                        vp.setCurrentItem(FRAGMENT_NEWS);
                        //showFragment(FRAGMENT_NEWS);
                        //doubleClick(FRAGMENT_NEWS);
                        break;
                    case R.id.action_photo:
                        vp.setCurrentItem(FRAGMENT_PHOTO);
                        //showFragment(FRAGMENT_PHOTO);
                        //doubleClick(FRAGMENT_PHOTO);
                        break;
                    case R.id.action_video:
                        vp.setCurrentItem(FRAGMENT_VIDEO);
                        //showFragment(FRAGMENT_VIDEO);
                        //doubleClick(FRAGMENT_VIDEO);
                        break;
                    case R.id.action_media:
                        vp.setCurrentItem(FRAGMENT_MEDIA);
                        //showFragment(FRAGMENT_MEDIA);
                        break;
                }
                return false;
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(menuItem!=null){
                    menuItem.setChecked(false);
                }else{
                    bottom_navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem=bottom_navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUpViewPager();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(POSITION, position);
        outState.putInt(SELECT_ITEM, bottom_navigation.getSelectedItemId());
    }

    private void isAddFragment(String city,RankItemFragment fragment,String tag){
        if(fragment!=null){
            fragment = (RankItemFragment) fm.findFragmentByTag(tag);
        }else{
            fragment=RankItemFragment.newInstance(city);
            FragmentTransaction ft=fm.beginTransaction();
            ft.add(fragment,tag);
            ft.commit();
        }
        adapter.addFragment(fragment);
    }
    private void setUpViewPager(){
        fm=getSupportFragmentManager();
        adapter=new ViewPagerAdapter(fm);
//        isAddFragment("景德镇",allFragment,NEWS);
//        isAddFragment("杭州",highFragment,PHOTO);
//        isAddFragment("南昌",newFragment,VIDEO);
//        isAddFragment("九江",inFragment,MEDIA);
        adapter.addFragment(RankItemFragment.newInstance("景德镇"));
        adapter.addFragment(RankItemFragment.newInstance("杭州"));
        adapter.addFragment(RankItemFragment.newInstance("南昌"));
        adapter.addFragment(RankItemFragment.newInstance("九江"));
        vp.setAdapter(adapter);
    }
    private void hideFragment(FragmentTransaction ft) {
        // 如果不为空，就先隐藏起来
        if (allFragment != null) {
            ft.hide(allFragment);
        }
        if (highFragment != null) {
            ft.hide(highFragment);
        }
        if (newFragment!= null) {
            ft.hide(newFragment);
        }
        if (inFragment != null) {
            ft.hide(inFragment);
        }
    }
    private void showFragment(int index) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideFragment(ft);
        position = index;
        switch (index) {
            case FRAGMENT_NEWS:
                /**
                 * 如果Fragment为空，就新建一个实例
                 * 如果不为空，就将它从栈中显示出来
                 */
                if (allFragment== null) {
                    allFragment = RankItemFragment.newInstance("景德镇");//21
                    //ft.add(R.id.container, allFragment,NEWS);
                } else {
                    ft.show(allFragment);
                }
                break;

            case FRAGMENT_PHOTO:
                if (highFragment == null) {
                    highFragment = RankItemFragment.newInstance("杭州");//23
                    //ft.add(R.id.container, highFragment,PHOTO);
                } else {
                    ft.show(highFragment);
                }
                break;

            case FRAGMENT_VIDEO:
                if (newFragment == null) {
                    newFragment = RankItemFragment.newInstance("南昌");//8
                    //ft.add(R.id.container, newFragment,VIDEO);
                } else {
                    ft.show(newFragment);
                }
                break;

            case FRAGMENT_MEDIA:
                if (inFragment == null) {
                    inFragment= RankItemFragment.newInstance("九江");//20
                    //ft.add(R.id.container, inFragment,MEDIA);
                } else {
                    ft.show(inFragment);
                }
        }
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_download:
                startActivity(new Intent(this,DownloadCenterActivity.class));
                break;
        }
    }
}
