package com.wusir.modules.weather;

import com.wusir.bean.HeWeathers;

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
        void onSetAdapter(List<?> list);

        /**
         * 6.加载完毕
         */
        void onShowNoMore();
        /**
         * 7.请求数据
         */
        void onLoadData();

        /**
         * 8.刷新
         */
        void onRefresh();
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
        void doLoadData(String category);

        /**
         * 再起请求数据
         */
        void doLoadMoreData();

        /**
         * 设置适配器
         */
        void doSetAdapter(List<HeWeathers.HeWeather5Bean.DailyForecastBean> dataBeen);

        /**
         * 加载完毕
         */
        void doShowNoMore();
    }
}
