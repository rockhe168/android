package com.rock.com.quickstart.sender.http;

import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.rock.com.quickstart.business.controller.BusinessCommonParameter;
import com.rock.com.quickstart.common.StringUtil;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by xhhe on 2015/11/24.
 */
public class CtripHTTPClient {

    private static final int kMinTimeout = 2*1000;
    private static final int kMaxTimeout =  200*1000;
    private static final int kDefaultTimeout = 10*1000;
    private static final IOException timeoutException =  new IOException("(-110)网络请求超时,超过设定timeout");
    private static final HandlerThread shandlerThread = new HandlerThread("CtripHTTPClient");
    private static final MediaType MediaType_JSON = MediaType.parse("application/json;charset=utf-8");

    /**
     * 真正干活的OKHTTP
     */
    private OkHttpClient okClient;
    private CtripHTTPClient(){
        okClient = new OkHttpClient();
        if (shandlerThread.getState() == Thread.State.NEW) {//Thread.State.NEW====The thread has been created, but has never been started.
            try {
                shandlerThread.start();
            } catch (IllegalThreadStateException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 获取一个自定义的新实例。可根据需要自定义调整配置。
     * @return  新client实例
     */
    public static CtripHTTPClient getNewClient(){
        return new CtripHTTPClient();
    }

    /**
     * 通过tag取消请求
     * @param tag tag是发送请求之后的返回值
     */
    public void cancelRequest(String tag) {
        if (!StringUtil.emptyOrNull(tag) && okClient != null) {
            okClient.cancel(tag);
        }
    }


    /**
     * 异步调用HTTP的post方法，会把所有键值对按照标准form的 application/x-www-form-urlencoded 编码方式post到服务器
     * @param url   目标url
     * @param json
     * @param responseCallback  回调
     * @param timeoutMillis 超时，单位毫秒, 如果超时时间>kMaxTimeout(200*1000ms)或者小于kMinTimeout(2*1000ms),都会使用默认超时时间kDefaultTimeout(10*1000ms)
     * @return 返回request的Tag，取消请求的时候需要使用
     */
    public String asyncPostWithTimeout(String url, String json, final CtripHTTPCallback responseCallback, final int timeoutMillis) {

        RequestBody jsonBody = RequestBody.create(MediaType_JSON, json);
        String retTag = getRequestTagByURL(url);

        final Request request = new Request.Builder()
                .url(url)
                .tag(retTag)
                .post(jsonBody)
                .build();

        final Call call = okClient.newCall(request);
        CallbackWithTimeout cbWithTimeout = wrapCallbackWithTimeout(call, request, responseCallback, timeoutMillis);
        call.enqueue(cbWithTimeout);
        return  retTag;
    }

    public static JSONObject buildRequestHead(HashMap<String, Object> extension){
        String sysCode = BusinessCommonParameter.SYSTEMCODE;
        if(StringUtil.emptyOrNull(sysCode)){
            sysCode = "32";
        }

        String cver = BusinessCommonParameter.VERSION;

//        SharedPreferences sharedPreferences = PreferenceManager
//                .getDefaultSharedPreferences(BusinessController
//                        .getApplication());
//        String sauth = sharedPreferences.getString("sauth", "");
//        if(StringUtil.emptyOrNull(sauth)){
//            sauth = "";
//        }

        // 添加head
        JSONObject headObject = new JSONObject();
        try {
            headObject.put("syscode", BusinessCommonParameter.SYSTEMCODE);
            headObject.put("lang", BusinessCommonParameter.LANGUAGE);
//            headObject.put("auth", ApplicationCache.getInstance().getLoginTikcet());
//            headObject.put("cid", BusinessController.getAttribute(CacheKeyEnum.client_id));
//            headObject.put("ctok", BusinessController.getAttribute(CacheKeyEnum.token));
            headObject.put("cver", BusinessCommonParameter.VERSION);
            headObject.put("sid", BusinessCommonParameter.SOURCEID);
           // headObject.put("sauth", sauth);

            if (extension != null && extension.keySet().size() > 0) {
                JSONArray extensionArray = new JSONArray();
                for (String key : extension.keySet()) {
                    JSONObject ext = new JSONObject();
                    ext.put("name", key);
                    ext.put("value", extension.get(key));
                    extensionArray.put(ext);
                }
                headObject.put("extension", extensionArray);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return headObject;
    }

    private String getRequestTagByURL(String url) {
        String path = "--";
        if (!StringUtil.emptyOrNull(url)) {
            Uri.Builder urlBuilder = Uri.parse(url).buildUpon();
            path = urlBuilder.build().getPath();
        }
        String retTag = "RequestTag:" + path + ":" + System.currentTimeMillis();
        return  retTag;
    }

    private int formatTimeout(int inTimeout) {
        if (inTimeout < kMinTimeout || inTimeout > kMaxTimeout) {
            inTimeout = kDefaultTimeout;
        }
        return  inTimeout;
    }

    /*
    private void logHTTPRequestMetrics(Request request, Response response, String error, long startTimestamp) {
        String networkInfo = NetworkStateUtil.getNetworkTypeForHybrid();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("requestUrl", request.urlString());
        params.put("networkInfo", networkInfo);
        params.put("method", request.method());
        if (response != null) {
            if (response.code() > 0) {
                params.put("statusCode", ""+response.code());
            } else {
                params.put("statusCode", "unknown_status_code");
            }
        }
        if (!StringUtil.emptyOrNull(error)) {
            params.put("error_reason", error);
        }
        double useTm = (System.currentTimeMillis()-startTimestamp)/1000.0f;
        if (!StringUtil.emptyOrNull(error)) {
            LogUtil.logMonitor("o_http_failed",useTm, params);
            if(!CtripConfig.isProductEnv()) {
                LogUtil.d("HTTPRequest->error:" + error + "url:" + request.urlString());
            }
        } else {
            LogUtil.logMonitor("o_http_success",useTm, params);
            if(!CtripConfig.isProductEnv()) {
                LogUtil.d("HTTPRequest->success:" + error + "url:" + request.urlString());
            }
        }
    }
    */

    public CallbackWithTimeout wrapCallbackWithTimeout(final Call call,
                                                       final Request request,
                                                       final CtripHTTPCallback responseCallback,
                                                       int timeoutMillis) {

        if (call == null || request == null) {
            return null;
        }

        final long startTimestamp = System.currentTimeMillis();

        final OkHandler handler = new OkHandler(shandlerThread.getLooper());
        handler.request = request;
        Message message = Message.obtain();
        message.what = 0;

        handler.sendMessageDelayed(message, formatTimeout(timeoutMillis));

        CallbackWithTimeout callbackWithTimeout = new CallbackWithTimeout() {

            @Override
            public void onFailure(Request request, IOException e) {
                handler.removeMessages(0, this);
                if (call.isCanceled()) {
                    return;
                }

                if (!mTimeout) {
                    if (timeoutException.equals(e)) {
                        mTimeout = true;
                    }
                    //logHTTPRequestMetrics(request, null, "HTTP Request失败:"+e.getMessage(), startTimestamp);
                    Log.e("TAG","HTTP Request失败:"+e.getMessage());
                    if(responseCallback != null) {
                        responseCallback.onFailure(request, e);
                    }
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                handler.removeMessages(0, this);
                if (call.isCanceled()) {
                    return;
                }

                if(!mTimeout) {
                    //logHTTPRequestMetrics(request, response,null,startTimestamp);
                    Log.e("TAG","HTTP Request超时...:");
                    if(responseCallback != null) {
                        responseCallback.onResponse(response);
                    }
                }
            }
        };
        message.obj = callbackWithTimeout;
        return  callbackWithTimeout;
    }

    /**
     * 获取实际的OkHttpClient
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return okClient;
    }

    private static class OkHandler extends Handler {
        public Request request;

        OkHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            CallbackWithTimeout callbackWithTimeout = (CallbackWithTimeout)msg.obj;
            callbackWithTimeout.onFailure(request, timeoutException);
        }
    }

    private static class CallbackWithTimeout implements CtripHTTPCallback {
        protected boolean mTimeout = false;

        @Override
        public void onFailure(Request request, IOException e) {

        }

        @Override
        public void onResponse(Response response) throws IOException {

        }
    }
}
