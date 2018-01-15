package com.wusir.wuweather;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zy on 2017/11/21.
 */

public class RetrofitFactory {
    private static final Object Object = new Object();
    //设置无网络时使用缓存数据的拦截器
    private static final Interceptor cacheControlInterceptor=new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            if(true){//没有网络
                request=request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response=chain.proceed(request);
            if(true){//有网络
                String cacheControl=request.cacheControl().toString();
                return response.newBuilder()
                        .header("Cache-Control",cacheControl)
                        .removeHeader("param")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            }else{
                // 无网络时 设置超时为1周
                int maxState=60*60*24*7;
                return response.newBuilder()
                        .header("Cache-Control","public, only-if-cached, max-stale="+maxState)
                        .removeHeader("param")
                        .build();
            }
        }
    };
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
                        .addInterceptor(cacheControlInterceptor)
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
