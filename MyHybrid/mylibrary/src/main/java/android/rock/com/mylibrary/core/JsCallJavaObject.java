package android.rock.com.mylibrary.core;

import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xhhe on 2016/5/23.
 */

public class JsCallJavaObject {

    private static  final String JS_BRIDGE_PROTOCOL_SCHEMA="rock";
    private String mClassName;
    private String mMethodName;
    private String mPort;
    private JSONObject mParams;

    private JsCallJavaObject(){

    }

    public static JsCallJavaObject newInstance(){
        return new JsCallJavaObject();
    }

    public void call(WebView webView,String message)
    {
        if(webView==null || TextUtils.isEmpty(message))
        {
            return;
        }

        //1.解析 message 报文
        parseMessage(message);
        //调用native方法
        invokeNativeMethod(webView);
        //执行Js 回调函数(放到native方法中去了)
    }

    private void parseMessage(String message)
    {
        if(!message.startsWith(JS_BRIDGE_PROTOCOL_SCHEMA))
        {
            return;
        }
        //------->   rock://LoginHandler:8888/Add?{msg:'我是小三',data:{userName:'admin',passWord:'123456'}}
        Uri uri = Uri.parse(message);
        mClassName = uri.getHost();
        mPort = String.valueOf(uri.getPort());

        String path =  uri.getPath();
        if(!TextUtils.isEmpty(path))
        {
            mMethodName = path.replace("/","");
        }else
        {
            mMethodName = "";
        }

        try{
            mParams =new JSONObject(uri.getQuery());
        }catch (JSONException e){
            e.printStackTrace();
            mParams =new JSONObject();
        }
    }

    private void invokeNativeMethod(WebView webView){
        Method method =  NativeMethodInjectHelper.getInstance().findMethod(mClassName,mMethodName);

        JsCallBackObject jsCallBackObject = JsCallBackObject.newInstance(webView,mPort);
        if(method==null)
        {
            String msg =  "Method (" + mMethodName + ") in this class (" + mClassName + ") not found!";
            JsCallBackObject.invokeJsCallBack(jsCallBackObject,false,null,msg);
            return;
        }
        Object[] objects = new Object[3];
        objects[0] = webView;
        objects[1] = mParams;
        objects[2] = jsCallBackObject;

        try{
            method.invoke(null,objects);
        }catch (InvocationTargetException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
