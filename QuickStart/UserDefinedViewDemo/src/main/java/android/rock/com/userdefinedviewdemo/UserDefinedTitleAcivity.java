package android.rock.com.userdefinedviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by xhhe on 2016/3/8.
 */
public class UserDefinedTitleAcivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.userdefinedtitle_activiy);
    }
}
