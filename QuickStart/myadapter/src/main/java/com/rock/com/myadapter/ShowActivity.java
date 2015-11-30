package com.rock.com.myadapter;

/**
 * Created by Administrator on 2015/11/30 0030.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class ShowActivity extends Activity {
    private GridView gridView=null;
    private MyAdapter2 adapter=null;
    private SimpleAdapter adapter1=null;
    private List<HashMap<String,Object>> list=null;
    private HashMap<String,Object> map=null;
    private String data[]={"图片1","图片2","图片3","图片4","图片5","图6","图片7","图片8","图片9",
            "图片10","图片11","图片12","图片13","图片14","图片15","图片16"};
    private int   imgId[]={R.drawable.golden,R.drawable.golden,R.drawable.golden,R.drawable.golden,R.drawable.golden,
            R.drawable.golden,R.drawable.golden,R.drawable.golden,R.drawable.golden,R.drawable.golden,R.drawable.golden,
            R.drawable.golden,R.drawable.golden,R.drawable.golden,R.drawable.golden,R.drawable.golden};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        init();
        setData();
    }
    private void init(){

        gridView=(GridView) findViewById(R.id.grid_view);
        list=new ArrayList<HashMap<String,Object>>();
    }
    private void setData(){
//    使用BaseAdapter添加数据
        adapter=new MyAdapter2(ShowActivity.this, data, imgId);

        gridView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_show, menu);
        return true;
    }
}