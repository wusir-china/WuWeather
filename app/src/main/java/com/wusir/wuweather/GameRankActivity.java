package com.wusir.wuweather;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rank);
        StatusBarCompat.compat(this,R.color.colorGreen);
        initViews();
        initDatas();
        initEvents();
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

        vp.setOffscreenPageLimit(4);
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
        vp = (ViewPager)findViewById(R.id.vp);
        tabLayout= (TabLayout) findViewById(R.id.tab);
        rank1 = getResources().getDrawable(R.mipmap.icon_rank01);
        rank1_on = getResources().getDrawable(R.mipmap.icon_rank01_on);
        rank2 =getResources().getDrawable(R.mipmap.icon_rank02);
        rank2_on = getResources().getDrawable(R.mipmap.icon_rank02_on);
        rank3 = getResources().getDrawable(R.mipmap.icon_rank03);
        rank3_on = getResources().getDrawable(R.mipmap.icon_rank03_on);
        rank4 = getResources().getDrawable(R.mipmap.icon_rank04);
        rank4_on = getResources().getDrawable(R.mipmap.icon_rank04_on);
    }
}
