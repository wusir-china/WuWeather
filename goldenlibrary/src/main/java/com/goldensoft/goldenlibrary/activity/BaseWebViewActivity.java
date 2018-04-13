package com.goldensoft.goldenlibrary.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.goldensoft.goldenlibrary.R;

/**
 * Created by golden on 2018/4/2.
 */

public class BaseWebViewActivity extends BaseActivity{
    public WebView webView;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_webview);
        initViews();
    }

    private void initViews() {
        webView= (WebView) findViewById(R.id.webView);
        pb = (ProgressBar) findViewById(R.id.pb_web_base);
        final WebSettings settings=webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //webView.setWebViewClient(new WebViewClient());
        //设置此方法可在WebView中打开链接，反之用浏览器打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!settings.getLoadsImagesAutomatically()) {
                    settings.setLoadsImagesAutomatically(true);
                }
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                pb.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                }
                // Otherwise allow the OS to handle things like tel, mailto, etc.
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
    }
}
