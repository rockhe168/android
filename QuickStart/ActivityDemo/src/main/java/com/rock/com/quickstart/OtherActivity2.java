package com.rock.com.quickstart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/11/21 0021.
 */
public class OtherActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.otheractivity2);

        Intent intent=getIntent();
        String name =intent.getStringExtra("name");
        int age = intent.getIntExtra("age", 1);

        TextView txtMsg = (TextView)findViewById(R.id.txtMsg);
        txtMsg.setText("name:"+name+"--->age:"+age);

        Log.d("OtherActivity2", "onStart");
        Toast.makeText(this, "OtherActivity2 onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("OtherActivity2", "onStart");
        Toast.makeText(this, "OtherActivity2 onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("OtherActivity2", "onRestart");
        Toast.makeText(this,"OtherActivity2 onRestart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("OtherActivity2", "onResume");
        Toast.makeText(this,"OtherActivity2 onResume",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("OtherActivity2", "onPause");
        Toast.makeText(this,"OtherActivity2 onPause",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("OtherActivity2", "onStop");
        Toast.makeText(this,"OtherActivity2 onStop",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("OtherActivity2", "onDestroy");
        Toast.makeText(this,"OtherActivity2 onDestroy",Toast.LENGTH_SHORT).show();
    }
}
