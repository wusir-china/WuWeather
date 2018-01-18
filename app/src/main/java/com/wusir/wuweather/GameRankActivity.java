package com.wusir.wuweather;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.wusir.StatusBarCompat;
import com.wusir.adapter.FragmentViewPagerAdapter;

import java.util.ArrayList;

public class GameRankActivity extends AppCompatActivity{
    private RankItemFragment allFragment,highFragment,newFragment,inFragment;
    private Drawable rank1,rank1_on,rank2,rank2_on,rank3,rank3_on,rank4,rank4_on;
    private ViewPager vp;
    private FragmentViewPagerAdapter fpa;
    private TabLayout.Tab tab1,tab2,tab3,tab4;
    private TabLayout tabLayout;
    private ArrayList<String> mTitles=new ArrayList<>();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rank);
        StatusBarCompat.compat(this,R.color.colorGreen);
        initViews();
        if (savedInstanceState != null) {
            allFragment = (RankItemFragment) getSupportFragmentManager().findFragmentByTag(NEWS);
            highFragment = (RankItemFragment) getSupportFragmentManager().findFragmentByTag(PHOTO);
            newFragment = (RankItemFragment) getSupportFragmentManager().findFragmentByTag(VIDEO);
            inFragment = (RankItemFragment) getSupportFragmentManager().findFragmentByTag(MEDIA);
            // 恢复 recreate 前的位置
            showFragment(savedInstanceState.getInt(POSITION));
            bottom_navigation.setSelectedItemId(savedInstanceState.getInt(SELECT_ITEM));
        } else {
            showFragment(FRAGMENT_NEWS);
        }
        //initDatas();
        //initEvents();
    }
    private void initDatas() {
        allFragment=RankItemFragment.newInstance("21");
        highFragment=RankItemFragment.newInstance("23");
        newFragment=RankItemFragment.newInstance("8");
        inFragment=RankItemFragment.newInstance("20");
        ArrayList<Fragment> list=new ArrayList<>();
        list.add(allFragment);
        list.add(highFragment);
        list.add(newFragment);
        list.add(inFragment);
        fpa=new FragmentViewPagerAdapter(getSupportFragmentManager());
        fpa.setData(list);
        mTitles.add("艾欧尼亚");
        mTitles.add("战争学院");
        mTitles.add("黑色玫瑰");
        mTitles.add("皮尔特");
        fpa.setTitleData(mTitles);
        vp.setAdapter(fpa);
        tabLayout.setupWithViewPager(vp);
        //在setupWithViewPager之前tablayout没有tab,所以获取tab在setupWithViewPager之后
        tab1=tabLayout.getTabAt(0);
        tab1.setIcon(rank1_on);

        tab2=tabLayout.getTabAt(1);
        tab2.setIcon(rank2);
        tab3=tabLayout.getTabAt(2);
        tab3.setIcon(rank3);
        tab4=tabLayout.getTabAt(3);
        tab4.setIcon(rank4);
        //vp.setCurrentItem(0);
        //vp.setOffscreenPageLimit(4);
    }
    private void initEvents() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == tab1) {
                    tab1.setIcon(rank1_on);
                    vp.setCurrentItem(0);
                } else if (tab == tab2) {
                    tab2.setIcon(rank2_on);
                    vp.setCurrentItem(1);
                } else if (tab == tab3) {
                    tab3.setIcon(rank3_on);
                    vp.setCurrentItem(2);
                }else if (tab == tab4){
                    tab4.setIcon(rank4_on);
                    vp.setCurrentItem(3);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab == tab1) {
                    tab1.setIcon(rank1);
                } else if (tab == tab2) {
                    tab2.setIcon(rank2);
                } else if (tab == tab3) {
                    tab3.setIcon(rank3);
                }else if (tab == tab4){
                    tab4.setIcon(rank4);
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        iv_download= (ImageView) findViewById(R.id.iv_download);
        iv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(GameRankActivity.this,DownloadCenterActivity.class);
                startActivity(in);
            }
        });
    }

    private void initViews() {
        bottom_navigation= (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_news:
                        showFragment(FRAGMENT_NEWS);
                        //doubleClick(FRAGMENT_NEWS);
                        break;
                    case R.id.action_photo:
                        showFragment(FRAGMENT_PHOTO);
                        //doubleClick(FRAGMENT_PHOTO);
                        break;
                    case R.id.action_video:
                        showFragment(FRAGMENT_VIDEO);
                        //doubleClick(FRAGMENT_VIDEO);
                        break;
                    case R.id.action_media:
                        showFragment(FRAGMENT_MEDIA);
                        break;
                }
                return false;
            }
        });
        //vp = (ViewPager)findViewById(R.id.vp);
        //tabLayout= (TabLayout) findViewById(R.id.tab);
//        rank1 = getResources().getDrawable(R.mipmap.icon_rank01);
//        rank1_on = getResources().getDrawable(R.mipmap.icon_rank01_on);
//        rank2 =getResources().getDrawable(R.mipmap.icon_rank02);
//        rank2_on = getResources().getDrawable(R.mipmap.icon_rank02_on);
//        rank3 = getResources().getDrawable(R.mipmap.icon_rank03);
//        rank3_on = getResources().getDrawable(R.mipmap.icon_rank03_on);
//        rank4 = getResources().getDrawable(R.mipmap.icon_rank04);
//        rank4_on = getResources().getDrawable(R.mipmap.icon_rank04_on);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(POSITION, position);
        outState.putInt(SELECT_ITEM, bottom_navigation.getSelectedItemId());
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
                    allFragment = RankItemFragment.newInstance("21");
                    ft.add(R.id.container, allFragment,NEWS);
                } else {
                    ft.show(allFragment);
                }
                break;

            case FRAGMENT_PHOTO:
                if (highFragment == null) {
                    highFragment = RankItemFragment.newInstance("23");
                    ft.add(R.id.container, highFragment,PHOTO);
                } else {
                    ft.show(highFragment);
                }
                break;

            case FRAGMENT_VIDEO:
                if (newFragment == null) {
                    newFragment = RankItemFragment.newInstance("8");
                    ft.add(R.id.container, newFragment,VIDEO);
                } else {
                    ft.show(newFragment);
                }
                break;

            case FRAGMENT_MEDIA:
                if (inFragment == null) {
                    inFragment= RankItemFragment.newInstance("20");
                    ft.add(R.id.container, inFragment,MEDIA);
                } else {
                    ft.show(inFragment);
                }
        }
        ft.commit();
    }
}
