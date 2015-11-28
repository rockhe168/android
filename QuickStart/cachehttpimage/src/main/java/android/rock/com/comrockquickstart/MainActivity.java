package android.rock.com.comrockquickstart;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.os.StrictMode;
import android.rock.com.comrockquickstart.util.AsyncBitmapLoader;
import android.rock.com.comrockquickstart.util.FileUtils;
import android.rock.com.comrockquickstart.util.HttpUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


//有缓存本地地方文件，线都本地缓存的文件，不然实时去服务器上去
//2,每当页面销毁时，开一个异步线程获取服务器最新文件下载到本地缓存中去
public class MainActivity extends Activity {

    private static final MediaType MediaType_JSON = MediaType.parse("application/json;charset=utf-8");
    //private AsyncBitmapLoader asyncBitmapLoader;
   private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourindexactivity);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        path = MainActivity.this.getFilesDir().getPath();
        initData(path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //开启线程从新下载数据
        //如果不在内存缓存中，也不在本地（被jvm回收掉），则开启线程下载图片
        new Thread()
        {
            /* (non-Javadoc)
             * @see java.lang.Thread#run()
             */
            @Override
            public void run()
            {
                String filePath = path + "/" + "VacationHomePage.json";
                navigationSend(filePath,true);
            }
        }.start();

    }

    private void initData(String path)
    {
        //先检查本地是否存在
        String filePath = path + "/" + "VacationHomePage.json";

        File file = new File(filePath);

        if(!file.exists())
        {
            navigationSend(filePath,true);
        }else{

            String content =  FileUtils.readFileContent(file);
            initNavigation(content);
        }
    }


    private synchronized void navigationSend(final String filePath, final boolean isShowUI)
    {
        String url = "http://m.ctrip.com/restapi/soa2/10124/VacationHomePage.json";

        //create OkHttpClient
        OkHttpClient okHttpClient=new OkHttpClient();

        JSONObject requestParams = new JSONObject();
        try {
            requestParams.put("RequestTypeList", 1);
            requestParams.put("Version", "61100");
            requestParams.put("PlatformId", 1);
        } catch (JSONException e) {
            //Log.e(TAG, e.getLocalizedMessage());
        }
        RequestBody requestBody=RequestBody.create(MediaType_JSON,requestParams.toString());
        //构建一个Request（url+requestBody）
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        //发起一个请求动作
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("Tag", "okHttp 执行失败了...");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.e("des", "---------->okHttp 成功了...");
                ResponseBody body = response.body();
                byte[] bytes = body.bytes();
                final String responseStr = new String(bytes, "utf-8");
                FileUtils.saveFileToDisk(bytes, filePath);
                //来第一次进入
                if(isShowUI)
                {
                    //主线程中处理UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initNavigation(responseStr);
                        }
                    });
                }else {
                    //更新数据-->把图片也更新了。。。
                    parseResponseData(responseStr,path);
                }
            }
        });
    }

    private void initNavigation(String jsonStr)
    {
        String path = MainActivity.this.getFilesDir().getPath();
        ArrayList<HashMap<String, Object>> dataList = parseResponseData(jsonStr,path);
        initNavigationGridView(dataList,path);
    }

    //解析响应数据
    private ArrayList<HashMap<String, Object>> parseResponseData(String responseStr,final String path)
    {
        final ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        //ResponseBody body = response.body();
        try {
            //String respStr = new String(body.bytes(), "utf-8");
            JSONObject entrylist=new JSONObject(responseStr);
            JSONObject dataModel = entrylist.getJSONObject("Data");
            String appsettingString = dataModel.getString("AppSettingJSON");
            JSONObject appsettingModel = new JSONObject(appsettingString);
            JSONObject vactionModel = appsettingModel.getJSONObject("Vacation");
            String des = vactionModel.getString("Description");
            JSONArray navigationArray = vactionModel.getJSONArray("Navigation");
            int length = navigationArray.length();
            for (int i=0;i<length;i++)
            {
                JSONObject navigationModel = navigationArray.getJSONObject(i);
                String showText = navigationModel.getString("Text");
                String url =navigationModel.getString("AppLink");
                String imageUrl = navigationModel.getString("AppUrl");
                HashMap<String, Object> navigation = new HashMap<String, Object>();
                navigation.put("ItemImage",getImage(imageUrl,path)); //R.drawable.search);//添加图像资源的ID
                navigation.put("ItemText", showText);//按序号做ItemText
                navigation.put("Url",url);
                dataList.add(navigation);
            }
            Log.e("des", "---------->" + des);
            Log.e("ajax", responseStr);

        }catch (Exception e)
        {
            Log.e("des", "简析失败---------->" + e.toString());
        }
        return  dataList;
    }

    private void initNavigationGridView(final ArrayList<HashMap<String, Object>> dataList,final String path)
    {
        Log.e("TAG","--------------------------------------------------->");
        //显示入口
        GridView gridview = (GridView) findViewById(R.id.gridview_entry);

        //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, //没什么解释
                dataList,//数据来源
                R.layout.entry_item,//entry_item的XML实现
                //动态数组与ImageItem对应的子项
                new String[] {"ItemImage","ItemText"},
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] {R.id.ItemImage,R.id.ItemText});
                //显示bitmap图片
                adapter.setViewBinder(new SimpleAdapter.ViewBinder(){
                    public boolean setViewValue(View view, Object data,
                                                String textRepresentation) {
                        //判断是否为我们要处理的对象
                        if(view instanceof ImageView && data instanceof Bitmap){
                            ImageView imageView = (ImageView) view;
                            Bitmap bitmap =(Bitmap)data;//getImage(data.toString(),path);
                            imageView.setImageBitmap(bitmap);
                            return true;
                        }else
                            return false;
                    }
                });
                //添加并且显示
                gridview.setAdapter(adapter);

        //图片点击事件
        //思考题 在Gridview中 SimpleAdpater  图片添加事件
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,Object> currentMap = dataList.get(position);
                String name = currentMap.get("ItemText").toString();
                String url = currentMap.get("Url").toString();

                String tipText = "你点击了--->"+name+"-->Url:"+url;

                Toast.makeText(MainActivity.this, tipText, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public Bitmap getImage(String imageHttpUrl,String cachePath)
    {
        Bitmap bitmap =null;
        String imageName = imageHttpUrl.substring(imageHttpUrl.lastIndexOf("/") + 1);
        String imagePath = cachePath +"/"+imageName;
        File file =new File(imagePath);
        if(!file.exists())
        {
            //去下载
            bitmap = getUrlImage(imageHttpUrl);
            //落地
            boolean result = FileUtils.saveFileToDisk(bitmap,imagePath);
            if(result){
                Log.e("img","图片命中下载并落地成功..."+imagePath);
            }else
            {
                Log.e("img","图片命中下载并落地失败..."+imagePath);
            }
        }
        else
        {
            Log.e("img","图片命中..."+imagePath);
            bitmap =FileUtils.getImageBitmap(imagePath);//GetLocalBitmap(imagePath);
        }

        return  bitmap;
    }

    //加载图片
    public Bitmap getUrlImage(String url) {
        Bitmap img = null;
        try {
            URL picurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection)picurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(true);//不缓存
            conn.setDefaultUseCaches(true);
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            img = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

}
