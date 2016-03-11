package android.rock.com.fragmentnewsdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.rock.com.fragmentnewsdemo.component.NewsContentFragment;
import android.rock.com.fragmentnewsdemo.model.News;

/**
 * Created by xhhe on 2016/3/11.
 */
public class NewsContentActivity extends Activity {

    public static void actionStart(Context context,String newTitle,String newsContent)
    {
        Intent intent=new Intent(context, NewsContentActivity.class);
        intent.putExtra("news_title",newTitle);
        intent.putExtra("news_content",newsContent);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);

        String newsTitle = getIntent().getStringExtra("news_title");
        String newsContent = getIntent().getStringExtra("news_content");

        NewsContentFragment newsContentFragment = (NewsContentFragment)getFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle,newsContent);
    }


}
