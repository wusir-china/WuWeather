package com.wusir.modules.weather;

import com.wusir.bean.HeWeathers;
import com.wusir.bean.Weather;
import com.wusir.wuweather.RetrofitFactory;
import com.wusir.wuweather.WeatherApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zy on 2017/11/23.
 */

public class WeatherPresenter implements IWeather.Presenter{
    private IWeather.View view;
    private ArrayList<Weather> dataList= new ArrayList<>();

    public WeatherPresenter(IWeather.View view) {
        this.view = view;
    }

    @Override
    public void doRefresh(String city) {
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
    public void doLoadData(String city) {
        getDataByRxjava(city);
    }
    private void getDataByRxjava(String city){
        RetrofitFactory.getRetrofit().create(WeatherApi.class)
                .getWeather4Json(city,WeatherApi.key)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        d.isDisposed();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //更新ui线程
                        try {
                            dataList.clear();
                            JSONObject jo = new JSONObject(responseBody.string());
                            JSONArray data = jo.getJSONArray("HeWeather5");
                            for(int i=0;i<data.length();i++){
                                JSONObject item= data.getJSONObject(i);
                                JSONArray daily_forecast = item.getJSONArray("daily_forecast");
                                for(int j=0;j<daily_forecast.length();j++){
                                    JSONObject subitem= daily_forecast.getJSONObject(j);
                                    JSONObject cond=subitem.getJSONObject("cond");
                                    String txt_d = cond.optString("txt_d");
                                    String date = subitem.optString("date");
                                    JSONObject tmp=subitem.getJSONObject("tmp");
                                    String min= tmp.optString("min");
                                    String max= tmp.optString("max");
                                    String stmp=min+"度/"+max+"度";
                                    dataList.add(new Weather(date,txt_d,stmp));
                                }
                            }
                            if (null != dataList && dataList.size() > 0) {
                                doSetAdapter(dataList);
                            } else {
                                doShowNoMore();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //网络错误时执行
                        doShowNetError();
                    }

                    @Override
                    public void onComplete() {
                        //数据成功加载完成时执行
                        view.onHideLoading();
                    }
                });
    }
    @Override
    public void doLoadMoreData(String city) {
        //++page;
        doLoadData(city);
    }

    @Override
    public void doSetAdapter(List<Weather> dataBeen) {
        view.onSetAdapter(dataBeen);
    }

    @Override
    public void doShowNoMore() {
        view.onShowNoMore();
    }
}
