package android.rock.com.uilayoutdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnMsg).setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btnMsg:
                    invokeMessage();
                    break;
                default:
            }
        }
    };

    void invokeMessage(){
        Intent intent =new Intent(this,MessageActivity.class);
        startActivity(intent);
    }
}
