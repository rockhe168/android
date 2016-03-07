package com.rock.com.quickstart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/3/6 0006.
 */
public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age",0);

        if(name !=null) {
           TextView txtMsg = (TextView)findViewById(R.id.txtMsg);
           txtMsg.setText("name:"+name+"--->age:"+age);
        }

        findViewById(R.id.btnClose).setOnClickListener(listener);

        Log.d("ResultActivity", "onCreate");
        Toast.makeText(this, "ResultActivity onCreate", Toast.LENGTH_SHORT).show();
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btnClose:
                    closeWindow();
                    break;
                default:
            }


        }
    };

    void closeWindow()
    {
        Intent closeIntent = new Intent();
        closeIntent.putExtra("returnData","我是来自ResultActiviy 返回数据");
        setResult(RESULT_OK, closeIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ResultActivity", "onStart");
        Toast.makeText(this, "ResultActivity onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ResultActivity", "onRestart");
        Toast.makeText(this,"ResultActivity onRestart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ResultActivity", "onResume");
        Toast.makeText(this,"ResultActivity onResume",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ResultActivity", "onPause");
        Toast.makeText(this,"ResultActivity onPause",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ResultActivity", "onStop");
        Toast.makeText(this,"ResultActivity onStop",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ResultActivity", "onDestroy");
        Toast.makeText(this,"ResultActivity onDestroy",Toast.LENGTH_SHORT).show();
    }
}
