package android.rock.com.mylibrary.core;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.webkit.WebView;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xhhe on 2016/5/23.
 *  * 符合注入的方法的格式:
 * public static void ***(WebView webView, JSONObject data, JsCallback callback){
 * //...
 * }
 */

public class NativeMethodInjectHelper {
    private volatile static NativeMethodInjectHelper sInstance;

    //注入的类集合
    private List<Class<? extends BaseHybrid>> mInjectClassList;

    //注入的类对应的方法字段
    private ArrayMap<String,ArrayMap<String,Method>> mClassMethodMap;


    private NativeMethodInjectHelper(){
        mInjectClassList =new ArrayList<>();
        mClassMethodMap = new ArrayMap<>();
    }

    //单列模式，安全线程模式
    public static NativeMethodInjectHelper getInstance() {
        NativeMethodInjectHelper instance = sInstance;
        if (instance == null) {
            synchronized (NativeMethodInjectHelper.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new NativeMethodInjectHelper();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    //类装载器
    public NativeMethodInjectHelper loadingClass(Class<? extends BaseHybrid> classType)
    {
        if(classType ==null)
        {
            throw new NullPointerException("NativeMethodInjectHelper:The classType can not be null!");
        }
        if(!mInjectClassList.contains(classType))
        {
            mInjectClassList.add(classType);
        }
        return this;
    }

    //加载注入class
    public void inject()
    {
        int size = mInjectClassList.size();
        if(size !=0)
        {
            mClassMethodMap.clear();
            for (int i=0;i<size;i++){
                //加载类方法
                putMethod(mInjectClassList.get(i));
            }
        }
    }

    //把类注入到类可用方法中去
    private void putMethod(Class<? extends BaseHybrid> clazz){

        if(clazz==null)
        {
            return;
        }
        ArrayMap<String,Method> currentClassCanLoadingMethodList = new ArrayMap<>();
        Method[]   currentClassMethodList= clazz.getDeclaredMethods();
        int lenght = currentClassMethodList.length;
        for (int i=0;i<lenght;i++)
        {
            Method method = currentClassMethodList[i];
            int methodModifiers = method.getModifiers();
            if ((methodModifiers & Modifier.PUBLIC) != 0 && (methodModifiers & Modifier.STATIC) != 0 && method.getReturnType() == void.class) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes != null && parameterTypes.length == 3) {
                    if (WebView.class == parameterTypes[0] && JSONObject.class == parameterTypes[1] && JsCallBackObject.class == parameterTypes[2]) {
                        currentClassCanLoadingMethodList.put(method.getName(), method);
                    }
                }
            }
        }

        mClassMethodMap.put(clazz.getSimpleName(),currentClassCanLoadingMethodList);
    }

    //容器里面找方法
    public Method findMethod(String className,String methodName)
    {
        if(TextUtils.isEmpty(className) || TextUtils.isEmpty(methodName))
        {
            return  null;
        }

        if(mClassMethodMap.containsKey(className))
        {
            ArrayMap<String,Method> currentClassMethodList = mClassMethodMap.get(className);
            if(currentClassMethodList==null)
            {
                return null;
            }
            if(currentClassMethodList.containsKey(methodName))
            {
                return currentClassMethodList.get(methodName);
            }
        }
        return  null;
    }
}
