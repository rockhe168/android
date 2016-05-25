package android.rock.com.myhybrid;

import android.content.Intent;
import android.os.Bundle;
import android.rock.com.mylibrary.BaseActivity;
import android.widget.TextView;


/**
 * Created by xhhe on 2016/5/25.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        String password = intent.getStringExtra("password");

        if(userName !=null)
        {
            TextView txtUserName = (TextView) findViewById(R.id.txtUserName);
            txtUserName.setText(userName);
        }

        if(password !=null)
        {
            TextView txtPassword = (TextView) findViewById(R.id.txtPassword);
            txtPassword.setText(password);
        }
    }
}
