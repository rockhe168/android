package com.rock.com.quickstart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2015/11/21 0021.
 */
public class OtherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置用那个布局来呈现这个view
        setContentView(R.layout.otheractivity);
    }
}
