package com.wusir.modules.weather;

import com.wusir.bean.HeWeathers;
import com.wusir.bean.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2017/11/23.
 */

public interface IWeather {
    interface View{
        /**
         * 1.显示加载动画
         */
        void onShowLoading();

        /**
         * 2.隐藏加载
         */
        void onHideLoading();

        /**
         * 3.显示网络错误
         */
        void onShowNetError();

        /**
         * 4.设置 presenter
         */
        void setPresenter(Presenter presenter);
        /**
         * 5.设置适配器
         */
        void onSetAdapter(List<Weather> list);

        /**
         * 6.加载完毕
         */
        void onShowNoMore();
        /**
         * 7.请求数据
         */
        //void onLoadData();
        /**
         * 8.上拉刷新时视图
         */
        //void onRefreshing();
    }
    interface Presenter{
        /**
         * 刷新数据
         */
        void doRefresh(String city);

        /**
         * 显示网络错误
         */
        void doShowNetError();
        /**
         * 请求数据
         */
        void doLoadData(String city);

        /**
         * 再起请求数据
         */
        void doLoadMoreData(String city);

        /**
         * 设置适配器
         */
        void doSetAdapter(List<Weather> dataBeen);

        /**
         * 加载完毕
         */
        void doShowNoMore();
    }
}
