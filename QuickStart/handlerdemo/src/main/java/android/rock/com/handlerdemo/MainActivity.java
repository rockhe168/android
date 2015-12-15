package android.rock.com.handlerdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView txtState;
    private Button btnUpdateUI;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            if(msg.what==8) {
                txtState.setText("complate");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtState =(TextView)findViewById(R.id.txtState);
        btnUpdateUI = (Button)findViewById(R.id.btnUpdateUI);

        btnUpdateUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new WorkThread().start();
            }
        });

    }



    private class WorkThread extends Thread{
        @Override
        public void run() {
            //..............这里可以做很多事情

            //更新UI，会报错，应该UI是线程安全的...其他线程不能随意更新...只能主线程能Update---->下面这行是错误的写法
            //txtState.setText("complate");


            //正确的写法
            Message msg =new Message();
            msg.what = 8;
            handler.sendMessage(msg);
        }
    }
}
