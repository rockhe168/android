package com.rock.com.quickstart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //控件事件绑定二
        Button button2= (Button)findViewById(R.id.button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button)v;
                String text = (String)button.getText();
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openTipWindow1(View view)
    {
        //获取当前组件
        Button button1 = (Button)view;

        //获取文本
        String text = (String)button1.getText();

        // Toast 在当前窗口显示一个提示框
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    public void openWindow1(View view)
    {
        Intent intent = new Intent(this,OtherActivity.class);

        startActivity(intent);
    }

    public void openWindow2(View view)
    {
        Intent intent = new Intent(this,OtherActivity2.class);

        intent.putExtra("name","rock");
        intent.putExtra("age",30);

        startActivity(intent);
    }

    public void openWindow3(View view)
    {
        Intent intent =new Intent(this,OtherActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putString("name","alice");
        bundle.putInt("age",29);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
