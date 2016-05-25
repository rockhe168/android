package android.rock.com.mylibrary.core;

import android.rock.com.mylibrary.async.AsyncTaskExecutor;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Locale;

/**
 * native结果数据返回格式:
 * var resultData = {
 * status: {
 * code: 0,//0成功，1失败
 * msg: '请求超时'//失败时候的提示，成功可为空
 * },
 * data: {}//数据
 * };
 * Created by xhhe on 2016/5/23.
 */

public class JsCallBackObject {

    private static final String CALLBACK_JS_FORMAT = "javascript:RockBridge.onComplete(%s,%s);";
    private WeakReference<WebView> mWebViewWeakReaf;
    private String mPort;


    private JsCallBackObject(WebView webView,String port)
    {
        this.mWebViewWeakReaf = new WeakReference<WebView>(webView);
        this.mPort = port;
    }

    public static JsCallBackObject newInstance(WebView webView,String port)
    {
        return  new JsCallBackObject(webView,port);
    }

    public  void call(boolean isInvokeSuccess, JSONObject resultData,String statusMsg) throws JSCallBackObjectException{
        final WebView webView = mWebViewWeakReaf.get();
        if(webView==null)
        {
            throw  new JSCallBackObjectException("WebView 已回收！");
        }

        //组装响应Json 对象
        JSONObject result =new JSONObject();
        JSONObject status = new JSONObject();
        try {
            status.put("code",isInvokeSuccess?0:1);
            if(!TextUtils.isEmpty(statusMsg))
            {
                status.put("msg",statusMsg);
            }else {
                status.put("msg","");
            }
            result.put("status",status);

            if(resultData !=null)
            {
                result.put("data",resultData);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        //组装回调需要执行的js
        final String callbackJs = String.format(Locale.getDefault(),CALLBACK_JS_FORMAT,mPort,result.toString());

        //放在webView中进行执行(只能执行在主线程中...)
        if(AsyncTaskExecutor.isMainThread())
        {
            Log.d("TAG",callbackJs);
            webView.loadUrl(callbackJs);
        }else {
            AsyncTaskExecutor.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl(callbackJs);
                }
            });
        }
    }

    public static void invokeJsCallBack(JsCallBackObject callBackObject,boolean isInvokeSuccess,JSONObject resultData,String statusMsg)
    {
        if(callBackObject==null)
        {
            return;
        }
        try{
            callBackObject.call(isInvokeSuccess,resultData,statusMsg);
        }catch (JSCallBackObjectException ex)
        {
            ex.printStackTrace();
        }
    }

    public  static class JSCallBackObjectException extends Exception{
        public JSCallBackObjectException(String excetipnMsg){
            super(excetipnMsg);
        }
    }
}
