package com.wusir.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/17.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> aFragment;
    private ArrayList<String> aTitle;
    public void setData(ArrayList<Fragment> aFragment){
        this.aFragment = aFragment;
    }
   public void setTitleData(ArrayList<String> aTitle){
       this.aTitle = aTitle;
   }
    public FragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        return aFragment.get(position);
    }
    @Override
    public int getCount() {
        return aFragment.size();
    }
    //重写方法，以便于和tablayout联动从而使tablayout获取标题
    @Override
    public CharSequence getPageTitle(int position) {
        return aTitle.get(position);
    }
}
