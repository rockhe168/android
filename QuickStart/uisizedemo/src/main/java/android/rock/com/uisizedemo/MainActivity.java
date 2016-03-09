package android.rock.com.uisizedemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by xhhe on 2016/3/8.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        float xdpi = getResources().getDisplayMetrics().xdpi;
        float ydpi = getResources().getDisplayMetrics().ydpi;
        Log.d("DP","xdpi is "+xdpi);
        Log.d("DP", "ydpi is " + ydpi);

        TextView txtMsg = (TextView)findViewById(R.id.txtMsg);
        txtMsg.setText("xdpi is "+xdpi+"-->ydpi is "+ydpi);
    }
}
