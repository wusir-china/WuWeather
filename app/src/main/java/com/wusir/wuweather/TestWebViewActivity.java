package com.wusir.wuweather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wusir.StatusBarCompat;


public class TestWebViewActivity extends AppCompatActivity {
    private WebView webView;
    private EditText et_content;
    private TextView submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_webview);
        StatusBarCompat.compat(this,R.color.colorGreen);
        webView= (WebView) findViewById(R.id.webView);
        et_content= (EditText) findViewById(R.id.content);
        submit= (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=et_content.getText().toString();
                //2.java调用js方法
                webView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
            }
        });
        WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //wusir作为注入接口别名。
        webView.addJavascriptInterface(new JsInterface(),"wusir");
        webView.loadUrl("file:///android_asset/js_webview.html");
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
