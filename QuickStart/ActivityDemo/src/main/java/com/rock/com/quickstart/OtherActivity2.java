package com.rock.com.quickstart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/11/21 0021.
 */
public class OtherActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.otheractivity2);

        Intent intent=getIntent();
        String name =intent.getStringExtra("name");
        int age = intent.getIntExtra("age", 1);

        TextView txtMsg = (TextView)findViewById(R.id.txtMsg);
        txtMsg.setText("name:"+name+"--->age:"+age);
    }
}
