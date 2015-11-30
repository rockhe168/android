package com.rock.com.myadapter;

/**
 * Created by Administrator on 2015/11/30 0030.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapter extends BaseAdapter {

    private Context context=null;
    private LayoutInflater mInflater;
    ArrayList<HashMap<String, Object>> mydataList;

    public MyAdapter(Context context,ArrayList<HashMap<String, Object>> dataList){
        super();
        this.mydataList = dataList;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mydataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder =null;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.entry_item,null);
            holder.img = (ImageView)convertView.findViewById(R.id.ItemImage);
            holder.textView =(TextView)convertView.findViewById(R.id.ItemText);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }

        final String showName = mydataList.get(position).get("ItemText").toString();
        final String url = mydataList.get(position).get("url").toString();
        holder.img.setImageBitmap((Bitmap) mydataList.get(position).get("ItemImage"));
        holder.textView.setText(showName);

        //为ImageView 设置 Click 事件,  -->此次也可以是Button 等其他控件
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipText = "你点击了--->"+showName+"-->Url:"+url;
                Toast.makeText(context, tipText, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public final class ViewHolder{
        public ImageView img;
        public TextView textView;
    }
}

