package android.rock.com.fragmentnewsdemo.model;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.rock.com.fragmentnewsdemo.NewsContentActivity;
import android.rock.com.fragmentnewsdemo.R;
import android.rock.com.fragmentnewsdemo.component.NewsContentFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by xhhe on 2016/3/10.
 */
public class NewsAdapter  extends BaseAdapter {


    private Context context =null;
    private LayoutInflater mInflater;
    private ArrayList<News> mydataList;
    private boolean isTwoPhone;

    public NewsAdapter(Context context,ArrayList<News> dataList,boolean isTwoPhone)
    {
        super();
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mydataList = dataList;
        this.isTwoPhone = isTwoPhone;
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

        ViewHolder holder=null;
        if(convertView ==null)
        {
            holder =new ViewHolder();
            convertView = mInflater.inflate(R.layout.news_item,null);
            holder.newsTitle =(TextView)convertView.findViewById(R.id.news_title);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        //要显示的消息项
        final News news = mydataList.get(position);
        holder.newsTitle.setText(news.getTitle());

        //点击事件
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NewsClick",news.getTitle());
                //大屏幕--->直接 refresh Fragment内容
                if(isTwoPhone)
                {
                    NewsContentFragment newsContentFragment = (NewsContentFragment)((Activity)context).getFragmentManager().findFragmentById(R.id.news_content_fragment);
                    newsContentFragment.refresh(news.getTitle(),news.getContent());
                }else{
                    //小屏幕--> open new activity
                    NewsContentActivity.actionStart(context, news.getTitle(), news.getContent());
                }

            }
        });

        return convertView;
    }

    public final class ViewHolder{
        public TextView newsTitle;
    }
}
