package com.wusir;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.wusir.wuweather.MainActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zy on 2018/1/15.
 */

public class OkHttpDemo {
    private MainActivity context;
    private TextView textView;
    private  String url = "https://www.baidu.com/";
    //1.get请求
    public void getMethod(){
        OkHttpClient okHttpClient=new OkHttpClient();
        //发起请求
        Request request=new Request.Builder()
                .get()//可以不写
                .url(url)
                .header("key","value")//可以有n个
                .build();
        //返回体
        Call call=okHttpClient.newCall(request);
        try {
            //这是个同步的耗时操作,放在android的UI线程直接执行时会出异常
            Response response=call.execute();
            System.out.print(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //2.post请求
    public void postMethod(){
        OkHttpClient okHttpClient=new OkHttpClient();
        //1.比get多了一个RequestBody，相当于提交表单，一般情况是提交键值对
        RequestBody body=new FormBody.Builder()
                .add("key","value")
                .build();
        //2.当表单数据是json时
        MediaType json=MediaType.parse("application/json;charset=utf-8");
        RequestBody jsonBody=RequestBody.create(json,"我的json");
        //3.当表单数据是文件时
        File files=new File("xxxx");
        MediaType file=MediaType.parse("image/png");
        RequestBody fileBody=new MultipartBody.Builder()
                .setType(MultipartBody.FORM)//MediaType FORM = MediaType.parse("multipart/form-data");
                .addFormDataPart("file",files.getName(),RequestBody.create(file,files))
                .build();
        //发起请求
        Request request=new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call=okHttpClient.newCall(request);
        try {
            Response response=call.execute();
            System.out.print(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //3.get方式的异步请求
    public void asyncGetData(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //如果你在Android把更新UI的操作写在这个方法里面是会报错的
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //如果你在Android把更新UI的操作写在这个方法里面是会报错的
                //所以可以用1.runOnUiThread，2.View的post方法，3.Handler,4.AsyncTask.
                //1.
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String str=response.body().string();
                            textView.setText(str);//更新UI
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
    //4.cookie的管理
    public void cookieManager(){
        HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(),cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies=cookieStore.get(url.host());
                        return cookies!=null?cookies:new ArrayList<Cookie>();
                    }
                })
                .build();
    }
}
