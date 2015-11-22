package com.rock.com.quickstart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1,加载一个网页，使用loadUrl--->并且在当前webView中打开

        WebView myWebView =(WebView)findViewById(R.id.webView);
        myWebView.loadUrl("http://www.baidu.com");

        //使加载的Web页面支持JavaScript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //在当前app 打开webview，还不是开启其他浏览器应用程序
        myWebView.setWebViewClient(new WebViewClient());
    }
}
