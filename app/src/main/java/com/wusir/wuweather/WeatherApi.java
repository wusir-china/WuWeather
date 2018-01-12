package com.wusir.wuweather;

import com.wusir.bean.HeWeathers;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zy on 2017/11/17.
 */

public interface WeatherApi {
    //必须以‘/’结尾
    String Host="https://free-api.heweather.com/v5/";
    //1.使用了Retrofit自己的返回类型Call和自定义泛型参数
    @GET("https://free-api.heweather.com/v5/forecast?city=杭州&key=1bda33b149584e68b6932b234c12d8fe")
    Call<HeWeathers> getWeatherJson(@Query("city") String city);//@Query("city") String city

    @GET("https://free-api.heweather.com/v5/forecast?city=杭州&key=1bda33b149584e68b6932b234c12d8fe")
    Observable<HeWeathers> getWeather2Json(@Query("city") String city);
}
