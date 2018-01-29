package com.wusir.modules.moive;

import android.util.Log;

import com.wusir.util.ToastUtil;
import com.wusir.wuweather.RetrofitFactory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zy on 2017/11/23.
 */

public class MoviePresenter implements IMovie.Presenter{
    private IMovie.View view;
    private List<Movie> dataList= new ArrayList<>();

    public MoviePresenter(IMovie.View view) {
        this.view = view;
    }

    @Override
    public void doRefresh(int start,int count) {
        if (dataList.size() != 0) {
            dataList.clear();
        }
        view.onShowLoading();
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doLoadData(int start,int count) {
        getDataByRxjava(start,count);
    }
    private void getDataByRxjava(int start,int count){
        RetrofitHelper.getRetrofit().create(DouBanMovieService.class)
                .getMoviesTop250(start,count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<List<Movie>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        //d.dispose();
                        d.isDisposed();
                    }

                    @Override
                    public void onNext(@NonNull Response<List<Movie>> listResponse) {
                        Log.e("aaa",listResponse.getSubjects().get(1).getTitle());
                        dataList=listResponse.getSubjects();
                        if (null != dataList && dataList.size() > 0) {
                            doSetAdapter(dataList);
                        } else {
                            doShowNoMore();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        doShowNetError();
                    }

                    @Override
                    public void onComplete() {
                        view.onHideLoading();
                    }
                });
    }
    @Override
    public void doLoadMoreData(int start,int count) {
        //++page;
        doLoadData(start,count);
    }

    @Override
    public void doSetAdapter(List<Movie> dataBean) {
        view.onSetAdapter(dataBean);
    }

    @Override
    public void doShowNoMore() {
        view.onShowNoMore();
    }
}
