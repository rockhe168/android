package com.rock.com.quickstart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        final WebView webView = (WebView)findViewById(R.id.webView);
        webView.requestFocus();

        //设置webView 进度处理...
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //super.onProgressChanged(view, newProgress);
                MainActivity.this.setTitle("Loading...");
                MainActivity.this.setProgress(newProgress);

                if (newProgress >= 80) {
                    MainActivity.this.setTitle("JSInVokeAndroid Test...");
                }
            }
        });

        //设置webView Can goBack
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    return true;
                }
                return false;
            }
        });

        //设置WebSetting支持执行js与字符编码
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultTextEncodingName("utf-8");


        //webView.addJavascriptInterface(ExposedToJsObject(),"androidObject");
        webView.addJavascriptInterface(new MyWebAppInterface(this),"androidObject");
        webView.loadUrl("http://192.168.1.102:8080/index.html");


        //Android 调用JS方法,--->不带参数
        Button buttonAndroid = (Button)findViewById(R.id.buttonAndroid);
        buttonAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("javascript:JSHelloWorld()");
            }
        });

        Button buttonAndroid2 = (Button) findViewById(R.id.buttonAndroid2);
        buttonAndroid2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "我的值来自APP";
                webView.loadUrl("javascript:JSHelloWorld2('"+msg+"')");
            }
        });

    }

    //---->API>17 必须加此标识

    @android.webkit.JavascriptInterface
    public Object ExposedToJsObject()
    {
        Object jsObject = new Object() {

            @JavascriptInterface
            public String androidHelloWorld(){
                Log.i("TAG","我已经来过了...");
                return  "我是一个Android应用里的一个Java方法返回值，很高兴见到你...";
            }

            @JavascriptInterface
            public void showTip()
            {
                Log.i("TAG","JS你弹我Android提示框搞啥子...");
                Toast.makeText(MainActivity.this,"JS你弹我Android提示框搞啥子...",Toast.LENGTH_SHORT);
            }
        };
        return  jsObject;
    }

}

