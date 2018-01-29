package com.wusir.modules.moive;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HaohaoChang on 2017/2/11.
 */
public interface DouBanMovieService {
    String BASE_URL = "https://api.douban.com/v2/movie/";
    //https://api.douban.com/v2/movie/top250?start=0&count=2
    @GET("top250")
    Observable<Response<List<Movie>>> getMoviesTop250(@Query("start") int start, @Query("count") int count);
    @GET("coming_soon")
    Observable<Response<List<Movie>>> getMoviesComing_soon(@Query("start") int start, @Query("count") int count);
    @GET("in_theaters")
    Observable<Response<List<Movie>>> getMoviesIn_theaters(@Query("city") String city);
    @GET("us_box")
    Observable<Response<List<Movie>>> getMoviesUs_box();
}
