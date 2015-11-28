package com.rock.com.quickstart.sender.home;

import com.rock.com.quickstart.sender.http.CtripHTTPCallback;
import com.rock.com.quickstart.sender.http.CtripHTTPClient;
import com.rock.com.quickstart.viewmodel.home.RecommendProductModel;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by xhhe on 2015/11/24.
 */
public class RecommendProductSender {


    //超时时间
    private static final int DEFAULT_TIMEOUT = 20000;
    private static RecommendProductSender mRecommendProductSender = null;

    //接口响应回调对象
    public abstract interface RecommendProductCallBack{
        void CallbackFunction(boolean success, ArrayList<RecommendProductModel> recommendProductList);
    }

    private RecommendProductSender(){

    }

    //返回当前对象实例
    public static RecommendProductSender getInstance(){
        if(mRecommendProductSender == null){
            synchronized(RecommendProductSender.class){
                if (mRecommendProductSender == null){
                    mRecommendProductSender = new RecommendProductSender();
                }
            }
        }
        return mRecommendProductSender;
    }

    //发送http 请求，并执行callBack
    public void Send(final RecommendProductCallBack callBack){
        String baseUrl="http://m.ctrip.com/restapi/soa2/10294/APIGetBarginProductListForBeiJingSite.json";

        JSONObject request = new JSONObject();

        try {
            request.put("head", CtripHTTPClient.buildRequestHead(null));
            request.put("Platform","H5");
            request.put("SaleCityId","2");
            request.put("TakeNum",8);
            request.put("contentType","json");
            //request.put("Reserved", "");
        } catch (Exception e){
            e.printStackTrace();
        }
        CtripHTTPClient httpClient = CtripHTTPClient.getNewClient();
        httpClient.asyncPostWithTimeout(baseUrl, request.toString(), new CtripHTTPCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    if(callBack !=null)
                    {
                        callBack.CallbackFunction(false, null);
                    }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                ResponseBody body = response.body();
                String respStr = new String(body.bytes(), "utf-8");
                try {
                    JSONObject resJson = new JSONObject(respStr);
                    if(resJson == null){
                        if(callBack != null){
                            callBack.CallbackFunction(false, null);
                        }
                    }
                    JSONArray originList = null;
                    if(resJson.has("BarginProductList")){
                        originList =resJson.optJSONArray("BarginProductList");
                    }
                    if(originList !=null && originList.length() !=0){
                            ArrayList<RecommendProductModel> recommendProductList = new ArrayList<RecommendProductModel>();
                            int lenght = originList.length();
                            for (int i=0;i<lenght;i++)
                            {
                                JSONObject jo = (JSONObject) originList.get(i);
                                if(jo !=null){
                                    RecommendProductModel model =new RecommendProductModel();
                                    model.ProductId = jo.optInt("ProductId");
                                    model.ProductName = jo.getString("ProductName");
                                    model.ImageUrl = jo.getString("CoverImageUrl");
                                    model.MinPrice = jo.getString("SalesPrice");
                                    recommendProductList.add(model);
                                }
                            }
                            if(callBack != null && recommendProductList != null){
                                callBack.CallbackFunction(true, recommendProductList);
                            }
                    }
                    if(callBack != null){
                        callBack.CallbackFunction(false, null);
                    }
                } catch (Exception e){
                    if(callBack != null){
                        callBack.CallbackFunction(false, null);
                    }
                    e.printStackTrace();
                }
            }
        },DEFAULT_TIMEOUT);

    }

    public void Send(){
        String baseUrl="http://m.ctrip.com/restapi/soa2/10294/APIGetBarginProductListForBeiJingSite.json";

        JSONObject request = new JSONObject();

        try {
            request.put("head", CtripHTTPClient.buildRequestHead(null));
            request.put("Platform","H5");
            request.put("SaleCityId","2");
            request.put("TakeNum","8");
            request.put("contentType","json");
            //request.put("Reserved", "");
        } catch (Exception e){
            e.printStackTrace();
        }
        CtripHTTPClient httpClient = CtripHTTPClient.getNewClient();
        Response response =httpClient.syncPost(baseUrl, request.toString());

        if(response !=null)
        {
            ResponseBody body = response.body();

            try {
                String respStr = new String(body.bytes(),"utf-8");
                JSONObject resJson = new JSONObject(respStr);

                JSONArray originList = null;
                if(resJson.has("BarginProductList")){
                    originList =resJson.optJSONArray("BarginProductList");
                }
                if(originList !=null && originList.length() !=0){
                    ArrayList<RecommendProductModel> recommendProductList = new ArrayList<RecommendProductModel>();
                    int lenght = originList.length();
                    for (int i=0;i<lenght;i++)
                    {
                        JSONObject jo = (JSONObject) originList.get(i);
                        if(jo !=null){
                            RecommendProductModel model =new RecommendProductModel();
                            model.ProductId = jo.optInt("ProductId");
                            model.ProductName = jo.getString("ProductName");
                            model.ImageUrl = jo.getString("CoverImageUrl");
                            model.MinPrice = jo.getString("SalesPrice");
                            recommendProductList.add(model);
                            System.out.print(model.ProductName+"------------->");
                        }
                    }
                }

            } catch (Exception e){

                e.printStackTrace();
            }
        }
    }

}
