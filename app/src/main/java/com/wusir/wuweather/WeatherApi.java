package com.wusir.wuweather;

import com.wusir.bean.HeWeathers;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by zy on 2017/11/17.
 */

public interface WeatherApi {
    //必须以‘/’结尾
    String key="1bda33b149584e68b6932b234c12d8fe";
    String Host="https://free-api.heweather.com/v5/forecast?key="+key+"/";
    //1.使用了Retrofit自己的返回类型Call和自定义泛型参数
    @GET("city/{city}")
    @FormUrlEncoded//默认形式请求数据类型mime
    Call<HeWeathers> getWeatherJson(@Query("city") String city);//@Query("city") String city

    //2.使用了Retrofit自己的返回类型Call和okhttp3.ResponseBody
    @GET("https://free-api.heweather.com/v5/forecast?city=杭州&key=1bda33b149584e68b6932b234c12d8fe")
    Call<ResponseBody> getWeather3Json(@Query("city") String city);

    //3.使用了RxJava返回类型Observable和自定义泛型参数
    @GET("https://free-api.heweather.com/v5/forecast?city=杭州&key=1bda33b149584e68b6932b234c12d8fe")
    Observable<HeWeathers> getWeather2Json(@Query("city") String city);

    //4.使用了RxJava返回类型Observable和okhttp3.ResponseBody
    @GET("https://free-api.heweather.com/v5/forecast?city=杭州&key=1bda33b149584e68b6932b234c12d8fe")
    Observable<ResponseBody> getWeather4Json(@Query("city") String city);

    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload1(@Part("name") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);
}
