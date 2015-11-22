package com.rock.com.quickstart;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import android.util.Log;

/**
 * Created by Administrator on 2015/11/21 0021.
 */
public class MyWebAppInterface {
    Context myContext;

    MyWebAppInterface(Context c)
    {
        myContext = c;
    }

    @JavascriptInterface
    public void showTip()
    {
        String toast ="JS你弹我Android提示框搞啥子2...";
        Log.i("TAG","JS你弹我Android提示框搞啥子2...");
        Toast.makeText(myContext,toast,Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void ShowTip(String msg)
    {
        Log.i("TAG","JS你弹我Android提示框搞啥子3...");
        Toast.makeText(myContext,msg,Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public String androidHelloWorld(){
        Log.i("TAG", "我已经来过了2...");
        return  "我是一个Android应用里的一个Java方法返回值，很高兴见到你...";
    }
}
