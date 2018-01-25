package com.wusir.wuweather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wusir.StatusBarCompat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TestWebViewActivity extends AppCompatActivity implements View.OnClickListener{
    private WebView webView;
    private EditText et_content;
    private TextView submit,fetch,htmlData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_webview);
        StatusBarCompat.compat(this,R.color.colorGreen);
        webView= (WebView) findViewById(R.id.webView);
        et_content= (EditText) findViewById(R.id.content);
        submit= (TextView) findViewById(R.id.submit);
        fetch= (TextView) findViewById(R.id.fetch);
        htmlData= (TextView) findViewById(R.id.htmlData);
        submit.setOnClickListener(this);
        fetch.setOnClickListener(this);
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //wusir作为注入接口别名。
        webView.addJavascriptInterface(new JsInterface(),"wusir");
        webView.loadUrl("file:///android_asset/js_webview.html");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit:
                String msg=et_content.getText().toString();
                //2.java调用js方法
                webView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
                break;
            case R.id.fetch:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Document doc= Jsoup.connect("http://home.meishichina.com/show-top-type-recipe.html").get();
                            TestWebViewActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Elements elements=doc.select("div.top-bar");
                                    htmlData.setText(elements.select("a").attr("title"));
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }
    //1.js调用java方法，定义注入接口JsInterface
    private class JsInterface{
        @JavascriptInterface
        public void onSumResult(int result){
            Toast.makeText(getApplicationContext(), result+"", Toast.LENGTH_LONG).show();
        }
        @JavascriptInterface
        public void callPhone(String phone){
            Uri uri = Uri.parse("tel:" + phone);
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(dialIntent);
        }
    }
    // 当点击系统“Back”键时希望网页回退而不退出浏览器,需要在当前Activity中处理并消费掉该 Back 事件。
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(webView.canGoBack()){
            webView.goBack();
            return;
        }else{
            finish();
        }
    }
}
