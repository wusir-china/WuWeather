package com.wusir.modules.moive;

import java.util.List;

/**
 * Created by zy on 2017/11/23.
 */

public interface IMovie {
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
        void onSetAdapter(List<Movie> list);

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
        void doRefresh(int start,int count);

        /**
         * 显示网络错误
         */
        void doShowNetError();
        /**
         * 请求数据
         */
        void doLoadData(int start,int count);

        /**
         * 再起请求数据
         */
        void doLoadMoreData(int start,int count);

        /**
         * 设置适配器
         */
        void doSetAdapter(List<Movie> dataBean);

        /**
         * 加载完毕
         */
        void doShowNoMore();
    }
}
