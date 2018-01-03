package com.wusir.wuweather;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zy on 2017/11/21.
 */

public class RetrofitFactory {
    private static final Object Object = new Object();
    private volatile static Retrofit retrofit;
    public static Retrofit getRetrofit() {
        synchronized (Object) {
            if (retrofit == null) {
                //指定缓存路径和缓存大小
                Cache cache=new Cache(new File(MyApplication.AppContext.getCacheDir(),"httpcache"),1024*1024*50);
                // Cookie 持久化
                ClearableCookieJar cookieJar=
                        new PersistentCookieJar(new SetCookieCache(),new SharedPrefsCookiePersistor(MyApplication.AppContext));
                OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .cookieJar(cookieJar)
                        .cache(cache)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true);
                retrofit = new Retrofit.Builder()
                        .baseUrl(WeatherApi.Host)
                        .client(builder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
            }
            return retrofit;
        }
    }
}
