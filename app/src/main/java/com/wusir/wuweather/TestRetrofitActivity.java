package com.wusir.wuweather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.wusir.StatusBarCompat;
import com.wusir.modules.moive.DouBanMovieService;
import com.wusir.modules.moive.Movie;
import com.wusir.util.ToastUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class TestRetrofitActivity extends RxAppCompatActivity {
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_retrofit);
        StatusBarCompat.compat(this,R.color.colorGreen);
        //测试MyBroadcastReceiver
        Intent globalIntent=new Intent();
        globalIntent.setAction("com.wusir.wuweather1");
        globalIntent.addFlags(1);
        globalIntent.putExtra("name","aaa");
        sendBroadcast(globalIntent);
        //以下动态注册时才会用,静态注册在xml中
        //1.注册广播
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.wusir.wuweather");
        //指定当前动作（Action）被执行的环境
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiver,intentFilter);
        //2.发送广播
        Intent localIntent=new Intent();
        localIntent.putExtra("name","bbb");
        localIntent.setAction("com.wusir.wuweather");
        sendBroadcast(localIntent);
        result= (TextView) findViewById(R.id.result);
        getDataByRxjava();
    }
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction()!=null){
                switch (intent.getAction()){
                    case "com.wusir.wuweather":
                        String extra=intent.getStringExtra("name");
                        ToastUtil.showToast(context,extra);
                        break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    //3.get方式的异步请求
    private void getDataByOkhttp(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(WeatherApi.ss)
                .build();
        okhttp3.Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                //需要把ui更新操作需要自己手动放在ui线程中
                TestRetrofitActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String str=response.body().string();
                            result.setText(str);//更新UI
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    private void getDataByRetrofit(){
        OkHttpClient.Builder builder=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .writeTimeout(15,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(WeatherApi.Host)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofit.create(WeatherApi.class)
                .getWeather3Json("杭州",WeatherApi.key)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            //Retrofit方式无需自己切换到ui线程
                            result.setText(response.body().string());
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
    private void getDataByRxjava(){
        RetrofitFactory.getRetrofit().create(WeatherApi.class)
                .getWeather4Json("杭州",WeatherApi.key)
                .compose(this.<ResponseBody>bindToLifecycle())//防止RxJava的内存泄漏
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
//                .subscribe(new Consumer<ResponseBody>() {
//                    @Override
//                    public void accept(ResponseBody responseBody) throws Exception {
//                        result.setText(responseBody.string());
//                    }
//                });
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        d.isDisposed();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        //更新ui线程
                        try {
                            result.setText(responseBody.string());
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
    }
}
