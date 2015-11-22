package com.rock.com.quickstart;

import android.content.DialogInterface;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private EditText txtUserName,txtPwd;
    private Button btnGet,btnPost;
    private TextView txtResult;

    private String requestUrl = "http://192.168.1.102:8080/User/Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                .detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        txtUserName = (EditText) findViewById(R.id.name);
        txtPwd =(EditText) findViewById(R.id.age);
        txtResult = (TextView)findViewById(R.id.result);

        btnGet = (Button)findViewById(R.id.submit_get);
        btnPost=(Button)findViewById(R.id.submit_post);

        //Post 请求
        btnPost.setOnClickListener(postClickListener);

        //Get请求
        btnGet.setOnClickListener(getClickListener);

    }

    protected  View.OnClickListener getClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("TAG", "GET request");
            // 先获取用户名和年龄
            String username = txtUserName.getText().toString();
            String age =txtPwd.getText().toString();

            // 使用GET方法发送请求,需要把参数加在URL后面，用？连接，参数之间用&分隔
            String url = requestUrl + "?username=" + username + "&age=" + age;

            // 生成请求对象
            HttpGet httpGet = new HttpGet(url);
            HttpClient httpClient = new DefaultHttpClient();

            // 发送请求
            try
            {
                HttpResponse response = httpClient.execute(httpGet);
                // 显示响应
                showResponseResult(response);// 一个私有方法，将响应结果显示出来
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    };

    protected View.OnClickListener postClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i("TAG", "POST request");

            //先获取输入内容
            String username = txtUserName.getText().toString();
            String age =txtPwd.getText().toString();

            //构建请求参数
            NameValuePair pairUsername = new BasicNameValuePair("username",username);
            NameValuePair pairAge =new BasicNameValuePair("age",age);

            List<NameValuePair> pairList=new ArrayList<NameValuePair>();
            pairList.add(pairUsername);
            pairList.add(pairAge);

            //发送请求
            try {

                HttpEntity requestHttpEntity=new UrlEncodedFormEntity(pairList);
                HttpPost httpPost =new HttpPost(requestUrl);
                //设置请求体
                httpPost.setEntity(requestHttpEntity);

                //设置客户端来发送请求
                HttpClient httpClient = new DefaultHttpClient();

                Log.i("TAG", "POST request 开始...");
                //发送请求
                HttpResponse response = httpClient.execute(httpPost);

                Log.i("TAG", "POST request 开始等待结果");
                //显示响应
                showResponseResult(response);
            }catch (Exception e)
            {
                e.printStackTrace();
                Log.i("TAG", "POST request 有异常");
            }
        }
    };

    private void showResponseResult(HttpResponse response)
    {
        if(null==response)
        {
            return;
        }

        HttpEntity httpEntity=response.getEntity();
        try{

            InputStream inputStream=httpEntity.getContent();
            BufferedReader reader= new BufferedReader(new InputStreamReader(inputStream));
            String result = "";
            String line = "";
            while (null != (line = reader.readLine()))
            {
                result += line;
            }
            System.out.println(result);
            txtResult.setText("Response Context from server:"+result);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
