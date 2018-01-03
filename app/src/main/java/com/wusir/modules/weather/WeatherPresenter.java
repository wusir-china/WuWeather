package com.wusir.modules.weather;

import com.wusir.bean.HeWeathers;
import com.wusir.wuweather.RetrofitFactory;
import com.wusir.wuweather.WeatherApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zy on 2017/11/23.
 */

public class WeatherPresenter implements IWeather.Presenter{
    private IWeather.View view;
    private List<HeWeathers.HeWeather5Bean.DailyForecastBean> dataList;

    public WeatherPresenter(IWeather.View view) {
        this.view = view;
    }

    @Override
    public void doRefresh(String city) {
        if (dataList.size() != 0) {
            dataList.clear();
        }
        view.onShowLoading();
        doLoadData(city);
    }

    @Override
    public void doShowNetError() {
        view.onHideLoading();
        view.onShowNetError();
    }

    @Override
    public void doLoadData(String category) {
        Observable<HeWeathers> ob = RetrofitFactory.getRetrofit()
                .create(WeatherApi.class)
                .getWeather2Json(category);

        ob.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<HeWeathers>() {
                    @Override
                    public void accept(@NonNull HeWeathers heWeathers) throws Exception {
                        List<HeWeathers.HeWeather5Bean.DailyForecastBean> list = new ArrayList<HeWeathers.HeWeather5Bean.DailyForecastBean>();
                        if (null != list && list.size() > 0) {
                            doSetAdapter(list);
                        } else {
                            doShowNoMore();
                        }
                        for (HeWeathers.HeWeather5Bean bean : heWeathers.getHeWeather5()) {
                            for (HeWeathers.HeWeather5Bean.DailyForecastBean bean2 : bean.getDaily_forecast()) {
                                list.add(bean2);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        doShowNetError();
                    }
                });
    }

    @Override
    public void doLoadMoreData() {
        doLoadData("");
    }

    @Override
    public void doSetAdapter(List<HeWeathers.HeWeather5Bean.DailyForecastBean> dataBeen) {
        dataList.addAll(dataBeen);
        view.onSetAdapter(dataList);
        view.onHideLoading();
    }

    @Override
    public void doShowNoMore() {
        view.onHideLoading();
        view.onShowNoMore();
    }
}
