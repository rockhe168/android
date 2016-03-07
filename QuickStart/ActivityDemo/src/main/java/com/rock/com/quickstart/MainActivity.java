package com.rock.com.quickstart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnOpenTip).setOnClickListener(listener);
        findViewById(R.id.btnOpenTip2).setOnClickListener(listener);
        findViewById(R.id.btnOpenWindow).setOnClickListener(listener);
        findViewById(R.id.btnOpenWindowParams).setOnClickListener(listener);
        findViewById(R.id.btnOpenWindowParams2).setOnClickListener(listener);
        findViewById(R.id.btnOpenWindowParamsResult).setOnClickListener(listener);

        Log.d("MainActivity", "onCreate");
        Toast.makeText(this,"MainActivity onCreate",Toast.LENGTH_SHORT).show();
    }

    private View.OnClickListener listener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btnOpenTip:
                    openTip();
                    break;
                case R.id.btnOpenTip2:
                    break;
                case R.id.btnOpenWindow:
                    openWindow();
                    break;
                case R.id.btnOpenWindowParams:
                    openWindowParams();
                    break;
                case R.id.btnOpenWindowParams2:
                    openWindowParams2();
                    break;
                case R.id.btnOpenWindowParamsResult:
                    openWindowParamsResult();
                    break;

            }
        }
    };

    //加入菜单栏


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);//当前activity 加入menu
        return true;//允许菜单栏显示
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
            case R.id.add_item:
                Toast.makeText(this,"You Click Add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(this,"You Click Remove",Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return  true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart");
        Toast.makeText(this,"MainActivity onStart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity", "onRestart");
        Toast.makeText(this,"MainActivity onRestart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
        Toast.makeText(this,"MainActivity onResume",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
        Toast.makeText(this,"MainActivity onPause",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop");
        Toast.makeText(this, "MainActivity onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
        Toast.makeText(this,"MainActivity onDestroy",Toast.LENGTH_SHORT).show();
    }


    void openTip()
    {
        Toast.makeText(this,"MainActivity onStart",Toast.LENGTH_SHORT).show();
    }

    void openWindow()
    {
        Intent intent = new Intent(this,OtherActivity.class);
        startActivity(intent);
    }

    void openWindowParams()
    {
        Intent intent = new Intent(this,OtherActivity2.class);

        intent.putExtra("name","rock");
        intent.putExtra("age",30);

        startActivity(intent);
    }

    void openWindowParams2()
    {
        Intent intent =new Intent(this,OtherActivity2.class);
        Bundle bundle = new Bundle();
        bundle.putString("name","alice");
        bundle.putInt("age",29);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    void openWindowParamsResult()
    {
        Intent intent = new Intent(this,ResultActivity.class);
        intent.putExtra("name","rock");
        intent.putExtra("age",30);

        startActivityForResult(intent,1);
    }

    //当从另外一个窗口返回时回调的方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1://代表从制定的Ativity callback
                if(resultCode==RESULT_OK)
                {
                    String resultData = data.getStringExtra("returnData");
                    Log.d("ResultActivityReturn", resultData);
                    TextView txtResultMsg = (TextView)findViewById(R.id.txtResultMsg);
                    txtResultMsg.setText(resultData);
                }
                break;
            default:
        }
    }
}
