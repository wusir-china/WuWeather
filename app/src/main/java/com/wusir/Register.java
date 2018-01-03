package com.wusir;

import com.wusir.bean.HeWeathers;
import com.wusir.modules.weather.WeatherViewBinder;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by zy on 2017/11/23.
 */

public class Register {
    public static void registerWeatherItem(MultiTypeAdapter adapter){
        adapter.register(HeWeathers.HeWeather5Bean.DailyForecastBean.class,new WeatherViewBinder());
        adapter.register(LoadingBean.class, new LoadingViewBinder());
        adapter.register(LoadingEndBean.class, new LoadingEndViewBinder());
    }
}
