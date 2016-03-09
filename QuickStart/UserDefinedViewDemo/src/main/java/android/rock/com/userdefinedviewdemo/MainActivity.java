package android.rock.com.userdefinedviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnHeaderTitle).setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btnHeaderTitle:
                    openHeaderTitleActivity();
                    break;
                default:
            }
        }
    };

    void openHeaderTitleActivity()
    {
        Intent intent =new Intent(this,UserDefinedTitleAcivity.class);
        startActivity(intent);
    }

}
