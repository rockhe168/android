package com.rock.com.myadapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2015/11/30 0030.
 */
public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void openArrayAdapter(View view)
    {
        Intent intent = new Intent(this,ArrayAdapterActivity.class);
        startActivity(intent);
    }

    public void openBaseAdapter(View view)
    {
        Intent intent = new Intent(this,BaseAdapterActivity.class);
        startActivity(intent);
    }

    public void openBaseAdapter2(View view)
    {
        Intent intent = new Intent(this,ShowActivity.class);
        startActivity(intent);
    }
}
