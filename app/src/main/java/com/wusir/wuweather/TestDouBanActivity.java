package com.wusir.wuweather;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.wusir.StatusBarCompat;
import com.wusir.adapter.ViewPagerAdapter;
import com.wusir.modules.moive.MovieItemFragment;
import com.wusir.modules.weather.WeatherItemFragment;
import com.wusir.util.BottomNavigationViewHelper;

public class TestDouBanActivity extends AppCompatActivity {
    private ViewPager vp;
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
    //我是用了viewpager+fragment+bottomNavigationView实现滑动和点击两种方式切换的
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
        vp = (ViewPager)findViewById(R.id.vp);
        //vp.setOffscreenPageLimit(3);
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
        adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MovieItemFragment.newInstance(0,10));
        adapter.addFragment(MovieItemFragment.newInstance(31,10));
        adapter.addFragment(MovieItemFragment.newInstance(61,10));
        adapter.addFragment(MovieItemFragment.newInstance(91,10));
        vp.setAdapter(adapter);
    }
}
