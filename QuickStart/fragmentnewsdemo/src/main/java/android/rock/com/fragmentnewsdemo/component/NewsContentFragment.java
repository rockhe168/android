package android.rock.com.fragmentnewsdemo.component;

import android.app.Fragment;
import android.os.Bundle;
import android.rock.com.fragmentnewsdemo.R;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by xhhe on 2016/3/10.
 */
public class NewsContentFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag,container,false);
        return view;
    }

    public void refresh(String newsTitle,String newsContent){
        View visibilityLayout= view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);

        TextView txtTitle = (TextView)view.findViewById(R.id.newsTitle);
        TextView txtContent = (TextView)view.findViewById(R.id.newsContent);

        txtTitle.setText(newsTitle);
        txtContent.setText(newsContent);
    }
}
