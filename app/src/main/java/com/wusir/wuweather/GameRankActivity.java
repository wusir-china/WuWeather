package com.wusir.wuweather;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.wusir.StatusBarCompat;
import com.wusir.adapter.ViewPagerAdapter;
import com.wusir.modules.weather.WeatherItemFragment;
import com.wusir.util.BottomNavigationViewHelper;

public class GameRankActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager vp;
    private ImageView iv_download;
    private BottomNavigationView bottom_navigation;
    private int position;
    private static final int FRAGMENT_NEWS = 0;
    private static final int FRAGMENT_PHOTO = 1;
    private static final int FRAGMENT_VIDEO = 2;
    private static final int FRAGMENT_MEDIA = 3;
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
            // 恢复 recreate 前的位置
            vp.setCurrentItem(savedInstanceState.getInt(POSITION));
            bottom_navigation.setSelectedItemId(savedInstanceState.getInt(SELECT_ITEM));
        } else {
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
                        break;
                    case R.id.action_photo:
                        vp.setCurrentItem(FRAGMENT_PHOTO);
                        break;
                    case R.id.action_video:
                        vp.setCurrentItem(FRAGMENT_VIDEO);
                        break;
                    case R.id.action_media:
                        vp.setCurrentItem(FRAGMENT_MEDIA);
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

    private void setUpViewPager(){
        fm=getSupportFragmentManager();
        adapter=new ViewPagerAdapter(fm);
//        adapter.addFragment(RankItemFragment.newInstance("景德镇"));
//        adapter.addFragment(RankItemFragment.newInstance("杭州"));
//        adapter.addFragment(RankItemFragment.newInstance("南昌"));
//        adapter.addFragment(RankItemFragment.newInstance("九江"));
        adapter.addFragment(WeatherItemFragment.newInstance("景德镇"));
        adapter.addFragment(WeatherItemFragment.newInstance("杭州"));
        adapter.addFragment(WeatherItemFragment.newInstance("南昌"));
        adapter.addFragment(WeatherItemFragment.newInstance("九江"));
        vp.setAdapter(adapter);
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
