package android.rock.com.userdefinedviewdemo.views;

import android.app.Activity;
import android.content.Context;
import android.rock.com.userdefinedviewdemo.R;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by xhhe on 2016/3/8.
 */
public class Title extends LinearLayout {

    public Title(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        findViewById(R.id.btnBack).setOnClickListener(listener);
        findViewById(R.id.btnEdit).setOnClickListener(listener);
    }

    View.OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId())
            {
                case R.id.btnBack:
                    back();
                    break;
                case R.id.btnEdit:
                    edit();
                    break;
                default:
            }
        }
    };

    void back(){
        ((Activity)getContext()).finish();
    }

    void edit(){
        Toast.makeText(getContext(),"You click edit!",Toast.LENGTH_SHORT).show();
    }
}
