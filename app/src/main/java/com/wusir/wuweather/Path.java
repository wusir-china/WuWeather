package com.wusir.wuweather;

import android.util.Log;

/**
 * Created by zy on 2017/5/25.
 */

public  class Path {
    private static String root= "http://d.yxdd.com/gm/";
    public static String rankItemApi(String menuID,int pageNum){
        return root+"ctAndroidActionDataCharts?menuID="+menuID+"&pageNum="+pageNum;
    }
    public static String ivUrl="http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640";
    public static String videoPath="http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4";
    public static String key="1bda33b149584e68b6932b234c12d8fe";
    public static String heWeather="https://free-api.heweather.com/v5/forecast?key="+key;
    //https://free-api.heweather.com/v5/forecast?city=杭州&key=1bda33b149584e68b6932b234c12d8fe
    public static String weApi(String city){
        Log.d("aaa","https://free-api.heweather.com/v5/forecast?city="+city+"&key=1bda33b149584e68b6932b234c12d8fe");
        return "https://free-api.heweather.com/v5/forecast?city="+city+"&key=1bda33b149584e68b6932b234c12d8fe";
    }
}
