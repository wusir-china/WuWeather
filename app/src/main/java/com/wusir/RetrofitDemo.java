package com.wusir;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.wusir.wuweather.WeatherApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zy on 2018/1/15.
 */

public class RetrofitDemo {
    //retrofit的一般使用
    public void getData(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(WeatherApi.Host)
                .build();
        WeatherApi wa=retrofit.create(WeatherApi.class);
        Call<ResponseBody> call=wa.getWeather3Json("city");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    //2.配合okhttp使用
    public void getData2(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("host")
                .client(builder.build())
                .build();
        retrofit.create(WeatherApi.class)
                .getWeather3Json("city")
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            System.out.print(call.execute().body().string());
                            System.out.print(response.body().string());
                            //更新UI的操作写在这个方法里面是会报错的
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
    //3.配合Retrofit+Rxjava使用
    public void getData3(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("host")
                .client(builder.build())
                .build();
        retrofit.create(WeatherApi.class)
                .getWeather4Json("city")
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
                        TextView textView=null;
                        try {
                            textView.setText(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        //加载完成
                    }
                });
//                .subscribe(new Consumer<ResponseBody>() {
//                    @Override
//                    public void accept(ResponseBody responseBody) throws Exception {
//                        System.out.print(responseBody.string());
//                    }
//                });
    }
}
